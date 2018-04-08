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
    private int ranking;  // 캠페인 기간이 끝나고 fix된 랭킹을 의미하는것일까?? 어떤 의도로 가져가는지 나래님과 이야기 해보도록 한다!
    private long goalExposureCount;
    private boolean display;
    private CampaignStatus status;
    private PangyoLocalDataTime dateTime;
}
