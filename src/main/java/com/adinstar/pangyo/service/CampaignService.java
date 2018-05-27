package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.CampaignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignMapper campaignMapper;

    public List<Long> getCampaignIdListOrderBySupportCount(long offset, int size) {
        return campaignMapper.selectCampaignIdListOrderBySupportCount(offset, size);
    }
}
