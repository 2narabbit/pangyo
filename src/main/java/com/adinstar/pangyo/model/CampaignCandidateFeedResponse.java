package com.adinstar.pangyo.model;

import lombok.Data;

import java.util.List;

@Data
public class CampaignCandidateFeedResponse extends FeedResponse {
    private List<Long> pollList;
    private int page;

    public CampaignCandidateFeedResponse(List<CampaignCandidate> campaignCandidateList, int page, int expactListSize, List<Long> pollList) {
        super(campaignCandidateList, expactListSize);

        this.page = page;
        this.pollList = pollList;

        // 캠페인후보는 lastId 대신 page값 이용
        this.setLastId(0);
    }
}
