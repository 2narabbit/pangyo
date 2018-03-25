package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

@Data
public class CampaignCandidate {
    private long id;
    private long executeRuleId;
    private Star star;
    private User user;
    private String title;
    private String body;
    private String randingUrl;
    private String bannerImg;
    private long viewCount;
    private long pollCount;
    private boolean display;
    private CampaignCandidateStatus status;
    private PangyoLocalDataTime dateTime;
}
