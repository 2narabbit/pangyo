package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.mapper.ExecutionRuleMapper;
import com.adinstar.pangyo.model.ExecutionRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutionRuleService {
    @Autowired
    private ExecutionRuleMapper executionRuleMapper;

    public ExecutionRule getProgressExecuteRuleByType(PangyoEnum.ExecutionRuleType executionRuleType) {
        ExecutionRule progressExecutionRule = executionRuleMapper.selectProgressExecuteRuleByType(executionRuleType);
        return progressExecutionRule;
    }

    public long getProgressExecuteRuleIdByType(PangyoEnum.ExecutionRuleType executionRuleType) {
        ExecutionRule executionRule = getProgressExecuteRuleByType(executionRuleType);
        if (executionRule == null) {
            throw InvalidConditionException.EXECUTION_RULE;
        }
        return executionRule.getId();
    }

    public List<ExecutionRule> getExecutionRuleListByTurnNum(long turnNum) {
        return executionRuleMapper.selectListByTurnNum(turnNum);
    }

    public void insert(ExecutionRule nextRule) {
        executionRuleMapper.insert(nextRule);
    }

    public List<ExecutionRule> getExecutionRuleListByStatus(PangyoEnum.ExecutionRuleStatus status) {
        return executionRuleMapper.selectListByStatus(status);
    }

    public void updateExecutionRuleStatusById(long id, PangyoEnum.ExecutionRuleStatus status) {
        executionRuleMapper.updateExecutionRuleStatusById(id, status);
    }

    public void removeExecutionRuleByTurnNum(long turnNum) {
        executionRuleMapper.deleteByTurnNum(turnNum);
    }
}
