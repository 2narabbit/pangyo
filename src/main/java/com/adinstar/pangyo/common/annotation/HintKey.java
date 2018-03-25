package com.adinstar.pangyo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface HintKey {
    String value();

    String STAR_ID = "statId";
    String CAMPAIGN_CANDIDATE = "campaignCandidate";
    String CAMPAIGN_CANDIDATE_ID = "campaignCandidateId";

    String POST = "post";
    String POST_ID = "postId";
}
