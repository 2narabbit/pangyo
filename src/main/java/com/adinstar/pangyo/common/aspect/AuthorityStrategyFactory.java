package com.adinstar.pangyo.common.aspect;

import com.adinstar.pangyo.common.aspect.strategy.CampaignCandidateAuthority;
import com.adinstar.pangyo.common.aspect.strategy.PostAuthority;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AuthorityStrategyFactory {

    @Autowired
    @Qualifier("campaignCandidate")
    private CampaignCandidateAuthority campaignCandidateAuthority;

    @Autowired
    @Qualifier("post")
    private PostAuthority postAuthority;

    public AuthorityStrategy getInstance(Class type) {
        if (type == CampaignCandidate.class) {
            return campaignCandidateAuthority;
        } else if (type == Post.class) {
            return postAuthority;
        }

        return null;
    }
}
