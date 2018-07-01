package com.adinstar.pangyo.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Mapper
@Repository
public interface CampaignRankMapper {
    LocalDateTime selectLastTime();

    void insert(@Param("campaignId") long campaignId, @Param("time") String time, @Param("ranking") long ranking);
}
