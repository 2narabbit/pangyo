package com.adinstar.pangyo.admin.service;

import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.mapper.PolicyMapper;
import com.adinstar.pangyo.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    @Autowired
    private PolicyMapper policyMapper;

    public long getPolicyValueByKey(String key) {
        Policy policy = policyMapper.selectPolicyByKey(key);
        if (policy == null) {
            throw InvalidConditionException.POLICY;
        }

        return Long.valueOf(policy.getValue());
    }
}
