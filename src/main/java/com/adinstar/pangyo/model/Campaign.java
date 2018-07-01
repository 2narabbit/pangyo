package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

import java.util.Optional;

@Data
public class Campaign {
    private long id;
    private long executeRuleId;
    private CampaignCandidate campaignCandidate;
    private long viewCount;
    private long supportCount;
    private boolean isRegisterAd;
    private boolean hasReport;
    private long goalExposureCount;
    private boolean display;
    private CampaignStatus status;
    private PangyoLocalDataTime dateTime;

    private String displayTitle;
    private String displayBody;
    private String displayRandingUrl;
    private String displayBannerImg;

    public String getDisplayTitle() {
        return (displayTitle != null) ? displayTitle : Optional.ofNullable(campaignCandidate).map(CampaignCandidate::getTitle).orElse(null);
    }

    public String getDisplayBody() {
        return (displayBody != null) ? displayBody : Optional.ofNullable(campaignCandidate).map(CampaignCandidate::getBody).orElse(null);
    }

    public String getDisplayRandingUrl() {
        return (displayRandingUrl != null) ? displayRandingUrl : Optional.ofNullable(campaignCandidate).map(CampaignCandidate::getRandingUrl).orElse(null);
    }

    public String getDisplayBannerImg() {
        return (displayBannerImg != null) ? displayBannerImg : Optional.ofNullable(campaignCandidate).map(CampaignCandidate::getBannerImg).orElse(null);
    }
}
