package com.adinstar.pangyo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CampaignRankMapper {
    String selectLastTime();

    void insertCampaignRank(@Param("campaignId") long campaignId, @Param("time") String time, @Param("ranking") int ranking);
}
