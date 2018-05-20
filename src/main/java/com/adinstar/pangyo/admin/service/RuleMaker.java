package com.adinstar.pangyo.admin.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.model.ExecutionRule;
import com.adinstar.pangyo.service.ExecutionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RuleMaker {
    private static final String POLICY_TURN_SUBFIX = "_TURN";
    private static final String POLICY_DONE_SUBFIX = "_DONE";

    @Autowired
    private ExecutionRuleService executionRuleService;

    @Autowired
    private PolicyService policyService;

    public long getRunningTurnNum() {
        return executionRuleService.getRunningTurnNum(PangyoEnum.ExecutionRuleType.CANDIDATE);
    }

    // 신규 타입이 추가됬을 때 유효하지 않습니다. default 룰 처리에 대한 고민을 해야하기 때문에 필요시 고민하는 걸로+_+
    @Transactional
    public void registeredExecutionRule(final long turnNum) {
        long beforeTurnNum = turnNum - 1;
        if (beforeTurnNum < 1) {
            throw InvalidConditionException.TRUN_NUM;
        }

        List<ExecutionRule> beforeExecutionRuleList = executionRuleService.getExecutionRuleListByTurnNum(beforeTurnNum);
        if (CollectionUtils.isEmpty(beforeExecutionRuleList)) {
            throw InvalidConditionException.TRUN_NUM;
        }

        for (ExecutionRule rule : beforeExecutionRuleList) {
            long days = policyService.getPolicyValueByKey(rule.getType().name() + POLICY_TURN_SUBFIX);

            ExecutionRule nextRule = new ExecutionRule();
            nextRule.setTurnNum(turnNum);
            nextRule.setType(rule.getType());
            nextRule.setStartDttm(rule.getStartDttm().plusDays(days));
            nextRule.setEndDttm(rule.getEndDttm().plusDays(days));

            executionRuleService.insert(nextRule);
        }
    }

    @Transactional
    public void processedFromDoneToEnd() {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> doneRuleList = executionRuleService.getExecutionRuleListByStatus(PangyoEnum.ExecutionRuleStatus.DONE);
        for (ExecutionRule rule : doneRuleList) {
            if (now.isBefore(rule.getEndDttm())) {
                executionRuleService.updateExecutionRuleStatusById(rule.getId(), PangyoEnum.ExecutionRuleStatus.END);
            }
        }
    }

    @Transactional
    public void processedFromRunningToDone() {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> runningRuleList = executionRuleService.getExecutionRuleListByStatus(PangyoEnum.ExecutionRuleStatus.RUNNING);
        for (ExecutionRule rule : runningRuleList) {
            String key = rule.getType().name() + POLICY_DONE_SUBFIX;
            long days = policyService.getPolicyValueByKey(key);

            LocalDateTime doneDate = rule.getEndDttm().minusDays(days);
            if (now.isBefore(doneDate)) {
                executionRuleService.updateExecutionRuleStatusById(rule.getId(), PangyoEnum.ExecutionRuleStatus.DONE);
            }
        }
    }

    @Transactional
    public void processedFromReadyToRunning(long turnNum) {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> runningRuleList = executionRuleService.getExecutionRuleListByTurnNum(turnNum);
        for (ExecutionRule rule : runningRuleList) {
            if (now.isBefore(rule.getStartDttm())) {
                executionRuleService.updateExecutionRuleStatusById(rule.getId(), PangyoEnum.ExecutionRuleStatus.RUNNING);
            }
        }
    }
}