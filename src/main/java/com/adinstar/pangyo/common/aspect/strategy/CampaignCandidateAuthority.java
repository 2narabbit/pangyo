package com.adinstar.pangyo.common.aspect.strategy;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.HintKey;
import com.adinstar.pangyo.common.aspect.AuthorityStrategy;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.ViwerInfo;
import com.adinstar.pangyo.service.CampaignCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("campaignCandidate")
public class CampaignCandidateAuthority implements AuthorityStrategy {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Override
    public boolean isValid(ViwerInfo viwerInfo, Method method, Object[] args, CheckAuthority checkAuthority) {
        Map paramMap = getParamMap(method.getParameters(), args);

        long starId = (long) paramMap.getOrDefault(HintKey.STAR_ID, 0);
        if (starId < 0) {
            throw BadRequestException.INVALID_PARAM;
        }

        CampaignCandidate campaignCandidate = null;
        switch (checkAuthority.checkType()) {
            case ID:
                long campaignCandidateId = (long) paramMap.getOrDefault(HintKey.CAMPAIGN_CANDIDATE_ID, 0);
                if (campaignCandidateId < 0) {
                    throw BadRequestException.INVALID_PARAM;
                }

                campaignCandidate = campaignCandidateService.getByStarIdAndId(starId, campaignCandidateId);
                break;
            case OBJECT:
                campaignCandidate = (CampaignCandidate) paramMap.getOrDefault(HintKey.CAMPAIGN_CANDIDATE, campaignCandidate);
                if (campaignCandidate == null) {
                    throw BadRequestException.INVALID_PARAM;
                }
                break;
            default:
                new NotImplementedException();
        }

        if (campaignCandidate == null) {
            throw NotFoundException.CAMPAIGN_CANDIDATE;
        }

        if (starId != campaignCandidate.getStar().getId()) {
            throw NotFoundException.CAMPAIGN_CANDIDATE;
        }

        if (checkAuthority.isCheckOwner() && (viwerInfo.getId() != campaignCandidate.getUser().getId())) {
            throw UnauthorizedException.NO_OWNER_SHIP;
        }

        return true;
    }

    // TODO : 이 부분을 이렇게 구리게 set하는게 아니라 param 정보로 한번에 가져올 수 없는지 고민해 보도록 한다!!!
    private Map getParamMap(Parameter[] parameters, Object[] args) {
        Map paramMap = new HashMap();
        for (int i = 0; i < parameters.length; i++) {
            HintKey hintKeyAnno = parameters[i].getDeclaredAnnotation(HintKey.class);
            if (hintKeyAnno == null) {
                continue;
            }

            switch (hintKeyAnno.value()) {
                case HintKey.STAR_ID:
                    paramMap.put(HintKey.STAR_ID, args[i]);
                    break;
                case HintKey.CAMPAIGN_CANDIDATE:
                    paramMap.put(HintKey.CAMPAIGN_CANDIDATE, args[i]);
                    break;
                case HintKey.CAMPAIGN_CANDIDATE_ID:
                    paramMap.put(HintKey.CAMPAIGN_CANDIDATE_ID, args[i]);
                    break;
                default:
                    new NotImplementedException();
            }
        }

        return paramMap;
    }
}
