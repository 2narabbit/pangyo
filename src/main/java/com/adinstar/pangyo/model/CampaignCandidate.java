package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

@Data
public class CampaignCandidate {
    private long id;
    private long executeRuleId;
    private long starId;
    private long userId;
    private String title;
    private String body;
    private String randingUrl;
    private String bannerImg;
    private long pollCount;
    private boolean display;
    private CampaignCandidateStatus status;
    private PangyoLocalDataTime dateTime;
}
