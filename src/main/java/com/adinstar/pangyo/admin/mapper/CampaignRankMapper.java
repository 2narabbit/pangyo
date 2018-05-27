package com.adinstar.pangyo.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface CampaignRankMapper {
    LocalDateTime selectLastTime();

    void insert(@Param("campaignId") long campaignId, @Param("time") String time, @Param("ranking") long ranking);
}
