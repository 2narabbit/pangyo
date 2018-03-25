package com.adinstar.pangyo.common.aspect;

import com.adinstar.pangyo.common.aspect.strategy.CampaignCandidateAuthority;
import com.adinstar.pangyo.model.CampaignCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AuthorityStrategyFactory {

    @Autowired
    @Qualifier("campaignCandidate")
    private CampaignCandidateAuthority campaignCandidateAuthority;

    public AuthorityStrategy getInstance(Class type) {
        if (type == CampaignCandidate.class) {
            return campaignCandidateAuthority;
        }

        return null;
    }
}
