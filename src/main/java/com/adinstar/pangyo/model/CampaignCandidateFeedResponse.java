package com.adinstar.pangyo.model;

import lombok.Data;

import java.util.List;

@Data
public class CampaignCandidateFeedResponse extends FeedResponse {
    private List<Long> pollList;
    private List<Long> myList;
    private int page;

    public CampaignCandidateFeedResponse(List<CampaignCandidate> campaignCandidateList, int page, int expactListSize, List<Long> pollList, List<Long> myList) {
        super(campaignCandidateList, expactListSize);

        this.page = page;
        this.pollList = pollList;
        this.myList = myList;

        // 캠페인후보는 lastId 대신 page값 이용
        this.setLastId(0);
    }
}
