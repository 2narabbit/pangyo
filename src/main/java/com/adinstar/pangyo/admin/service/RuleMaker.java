package com.adinstar.pangyo.admin.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.model.ExecutionRule;
import com.adinstar.pangyo.service.ExecutionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RuleMaker {
    private static final String POLICY_TURN_SUBFIX = "_TURN";
    private static final String POLICY_DONE_SUBFIX = "_DONE";

    @Autowired
    private ExecutionRuleService executionRuleService;

    @Autowired
    private PolicyService policyService;

    // 신규 타입이 추가됬을 때 유효하지 않습니다. default 룰 처리에 대한 고민을 해야하기 때문에 필요시 고민하는 걸로+_+
    @Transactional
    public void addExecutionRule() {
        ExecutionRule executionRule = executionRuleService.getProgressExecuteRuleByType(PangyoEnum.ExecutionRuleType.CANDIDATE);
        if (executionRule == null) {
            throw InvalidConditionException.EXECUTION_RULE;
        }

        addExecutionRule( executionRule.getTurnNum() + 1);
    }

    public void addExecutionRule(long nextTurnNum) {
        List<ExecutionRule> beforeExecutionRuleList = executionRuleService.getExecutionRuleListByTurnNum(nextTurnNum - 1);
        if (beforeExecutionRuleList.size() != PangyoEnum.ExecutionRuleType.values().length) {
            throw InvalidConditionException.TRUN_NUM;
        }

        executionRuleService.removeExecutionRuleByTurnNum(nextTurnNum);

        Map<PangyoEnum.ExecutionRuleType, Long> daysMap = beforeExecutionRuleList.stream().map(r -> r.getType()).distinct()
                .collect(Collectors.toMap(r -> r, r -> policyService.getPolicyValueByKey(r.name() + POLICY_TURN_SUBFIX)));

        for (ExecutionRule rule : beforeExecutionRuleList) {
            long days = daysMap.get(rule.getType());
            ExecutionRule nextRule = new ExecutionRule();
            nextRule.setTurnNum(nextTurnNum);
            nextRule.setType(rule.getType());
            nextRule.setStartDttm(rule.getStartDttm().plusDays(days));
            nextRule.setEndDttm(rule.getEndDttm().plusDays(days));

            executionRuleService.insert(nextRule);
        }
    }

    @Transactional
    public void proceedFromDoneToEnd() {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> doneRuleList = executionRuleService.getExecutionRuleListByStatus(PangyoEnum.ExecutionRuleStatus.DONE);
        doneRuleList.stream()
                .filter(r -> now.isBefore(r.getEndDttm()))
                .forEach(r -> executionRuleService.updateExecutionRuleStatusById(r.getId(), PangyoEnum.ExecutionRuleStatus.END));
    }

    @Transactional
    public void proceedFromRunningToDone() {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> runningRuleList = executionRuleService.getExecutionRuleListByStatus(PangyoEnum.ExecutionRuleStatus.RUNNING);

        Map<PangyoEnum.ExecutionRuleType, Long> daysMap = runningRuleList.stream().map(r -> r.getType()).distinct()
                .collect(Collectors.toMap(r -> r, r -> policyService.getPolicyValueByKey(r.name() + POLICY_DONE_SUBFIX)));

        runningRuleList.stream()
                .filter(r -> now.isBefore(r.getEndDttm().minusDays(daysMap.getOrDefault(r.getType(), 0L))))
                .forEach(r -> executionRuleService.updateExecutionRuleStatusById(r.getId(), PangyoEnum.ExecutionRuleStatus.DONE));
    }

    @Transactional
    public void proceedFromReadyToRunning() {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> readyRuleList = executionRuleService.getExecutionRuleListByStatus(PangyoEnum.ExecutionRuleStatus.READY);
        readyRuleList.stream()
                .filter(r -> now.isBefore(r.getStartDttm()))
                .forEach(r -> executionRuleService.updateExecutionRuleStatusById(r.getId(), PangyoEnum.ExecutionRuleStatus.RUNNING));
    }

    public boolean haveNextTurnRules() {
        ExecutionRule executionRule = executionRuleService.getProgressExecuteRuleByType(PangyoEnum.ExecutionRuleType.CANDIDATE);
        if (executionRule == null) {
            return false;
        }

        long nextTurnNum = executionRule.getTurnNum() + 1;
        List<ExecutionRule> nextExecutionRuleList = executionRuleService.getExecutionRuleListByTurnNum(nextTurnNum);
        if (nextExecutionRuleList.size() != PangyoEnum.ExecutionRuleType.values().length) {
            return false;
        }

        return true;
    }
}