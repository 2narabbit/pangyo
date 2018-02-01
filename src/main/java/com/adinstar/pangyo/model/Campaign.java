package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

@Data
public class Campaign {
    private long id;
    private long campaignCandidateId;
    private long viewCount;
    private long commentCount;
    private long supportCount;
    private boolean isRegisterAd;
    private boolean hasReport;
    private int ranking;
    private long goalExposureCount;
    private boolean display;
    private CampaignStatus status;
    private PangyoLocalDataTime dateTime;
}
