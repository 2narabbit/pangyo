package com.adinstar.pangyo.admin.service;

import com.adinstar.pangyo.constant.PangyoEnum.PolicyKey;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.mapper.PolicyMapper;
import com.adinstar.pangyo.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    @Autowired
    private PolicyMapper policyMapper;

    public long getPolicyValueByKey(PolicyKey key) {
        Policy policy = policyMapper.selectPolicyByKey(key);
        if (policy == null) {
            throw InvalidConditionException.POLICY;
        }

        return Long.valueOf(policy.getValue());
    }

    public long getPolicyValueByKey(String name) {
        PolicyKey key = getPolicyByKeyName(name);
        return getPolicyValueByKey(key);
    }

    // 엄마;; 세상 구리다- 고민해보자규!
    private PolicyKey getPolicyByKeyName(String name) {
        switch (name) {
            case "COMMENT":
                return PolicyKey.COMMENT;
            case "POST":
                return PolicyKey.POST;
            case "LIST":
                return PolicyKey.LIKE;
            case "CANDIDATE_TURN":
                return PolicyKey.CANDIDATE_TURN;
            case "CANDIDATE_DONE":
                return PolicyKey.CANDIDATE_DONE;
            case "CAMPAIGN_TURN":
                return PolicyKey.CAMPAIGN_TURN;
            case "CAMPAIGN_DONE":
                return PolicyKey.CAMPAIGN_DONE;
            case "AD_TURN":
                return PolicyKey.AD_TURN;
            case "AD_DONE":
                return PolicyKey.AD_DONE;
            case "CAMPAIGN_SNAPSHOT_TERM":
                return PolicyKey.CAMPAIGN_SNAPSHOT_TERM;
            case "STAR_SNAPSHOT_TERM":
                return PolicyKey.STAR_SNAPSHOT_TERM;
            default:
                throw InvalidConditionException.POLICY;
        }
    }
}