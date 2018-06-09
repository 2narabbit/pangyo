package com.adinstar.pangyo.model;

import lombok.Data;

import java.util.List;

@Data
public class CampaignCandidateFeedResponse extends FeedResponse {
    private List<Long> pollList;

    public CampaignCandidateFeedResponse(List<CampaignCandidate> campaignCandidateList, int expactListSize, List<Long> pollList) {
        super(campaignCandidateList, expactListSize);
        this.pollList = pollList;
    }
}
