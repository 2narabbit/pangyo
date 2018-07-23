package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleStatus;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.model.ExecutionRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExecutionRuleMapper {
    ExecutionRule selectProgressExecuteRuleByType(ExecutionRuleType type);

    List<ExecutionRule> selectListByTurnNum(long turnNum);

    List<ExecutionRule> selectListByStatus(ExecutionRuleStatus status);

    void insert(ExecutionRule executionRule);

    void updateExecutionRuleStatusById(@Param("id") long id, @Param("status") ExecutionRuleStatus status);

    void deleteByTurnNum(long turnNum);
}