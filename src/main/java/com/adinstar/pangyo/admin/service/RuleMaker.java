package com.adinstar.pangyo.admin.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.exception.InvalidSituation;
import com.adinstar.pangyo.mapper.ExecutionRuleMapper;
import com.adinstar.pangyo.mapper.PolicyMapper;
import com.adinstar.pangyo.model.ExecutionRule;
import com.adinstar.pangyo.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RuleMaker {

    public static final String POLICY_TURN_SUBFIX = "_TURN";
    public static final String POLICY_DONE_SUBFIX = "_DONE";

    @Autowired
    private ExecutionRuleMapper executionRuleMapper;

    @Autowired
    private PolicyMapper policyMapper;



    public long getRunningTurnNum() {
        Long runningTurmNum = executionRuleMapper.selectRunningTurnNum();
        if (runningTurmNum == null) {
            throw InvalidSituation.NOT_FOUND_EXECUTION_RULE;
        }

        return runningTurmNum;
    }

    public List<ExecutionRule> getExecutionRuleByTurnNum(long turnNum) {
        return executionRuleMapper.selectExecutionRuleListByTurnNum(turnNum);
    }

    // 신규 타입이 추가됬을 때 유효하지 않습니다. default 룰 처리에 대한 고민을 해야하기 때문에 필요시 고민하는 걸로+_+
    @Transactional
    public void registeredExecutionRule(final long turnNum) {
        List<ExecutionRule> beforeExecutionRuleList = getExecutionRuleByTurnNum(turnNum - 1);
        if (CollectionUtils.isEmpty(beforeExecutionRuleList)) {
            throw InvalidSituation.NOT_FOUND_EXECUTION_RULE;
        }

        for (ExecutionRule rule : beforeExecutionRuleList) {
            long days = getPolicyValueByKey(rule.getType().name() + POLICY_TURN_SUBFIX);

            ExecutionRule nextRule = new ExecutionRule();
            nextRule.setTurnNum(turnNum);
            nextRule.setType(rule.getType());
            nextRule.setStartDttm(rule.getStartDttm().plusDays(days));
            nextRule.setEndDttm(rule.getEndDttm().plusDays(days));

            executionRuleMapper.insertExecutionRule(nextRule);
        }
    }

    private long getPolicyValueByKey(String key) {
        Policy policy = policyMapper.selectPolicyByKey(key);
        if (policy == null) {
            throw InvalidSituation.NOT_FOUND_POLICY;
        }

        return Long.valueOf(policy.getValue());
    }

    public List<ExecutionRule> getExecutionRuleByStatus(PangyoEnum.ExecutionRuleStatus status) {
        return executionRuleMapper.selectExecutionRuleListByStatus(status);
    }

    @Transactional
    public void processedFromDoneToEnd() {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> doneRuleList = getExecutionRuleByStatus(PangyoEnum.ExecutionRuleStatus.DONE);
        for (ExecutionRule rule : doneRuleList) {
            if (now.isBefore(rule.getEndDttm())) {
                executionRuleMapper.updateExecutionRuleStatusById(rule.getId(), PangyoEnum.ExecutionRuleStatus.END);
            }
        }
    }

    @Transactional
    public void processedFromRunningToDone() {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> runningRuleList = getExecutionRuleByStatus(PangyoEnum.ExecutionRuleStatus.RUNNING);
        for (ExecutionRule rule : runningRuleList) {
            String key = rule.getType().name() + POLICY_DONE_SUBFIX;
            long days = getPolicyValueByKey(key);

            LocalDateTime doneDate = rule.getEndDttm().minusDays(days);
            if (now.isBefore(doneDate)) {
                executionRuleMapper.updateExecutionRuleStatusById(rule.getId(), PangyoEnum.ExecutionRuleStatus.DONE);
            }
        }
    }

    @Transactional
    public void processedFromReadyToRunning(long turnNum) {
        LocalDateTime now = LocalDateTime.now();

        List<ExecutionRule> runningRuleList = getExecutionRuleByTurnNum(turnNum);
        for (ExecutionRule rule : runningRuleList) {
            if (now.isBefore(rule.getStartDttm())) {
                executionRuleMapper.updateExecutionRuleStatusById(rule.getId(), PangyoEnum.ExecutionRuleStatus.RUNNING);
            }
        }
    }
}