package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.model.CampaignCandidateFeedResponse;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.StarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/campaign-candidate")
@MustLogin
public class CampaignCandidateApiController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private StarService starService;

    @ApiOperation("현재 진행 회차의 캠페인 후보군 list 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "page", value = "page number", paramType = "query", required = true, dataType = "int")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = CampaignCandidateFeedResponse.class)})
    @RequestMapping(value="/{starId}", method = RequestMethod.GET)
    @CheckAuthority
    public CampaignCandidateFeedResponse getRunningList(@PathVariable long starId,
                                                        @RequestParam int page,
                                                        @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        return campaignCandidateService.getRunningList(starId, Optional.of(page), Optional.empty(), viewerInfo == null? null : viewerInfo.getId());
    }

    @ApiOperation("캠페인 후보군 등록하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "CampaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody CampaignCandidate campaignCandidate,
                    @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        if (campaignCandidate.getStar() == null) {   // TODO: RequestBody 도 pathInterceptor에서 처리해줘야하나ㅠㅠ
            throw BadRequestException.INVALID_PARAM;
        }

        if (!starService.isJoined(campaignCandidate.getStar().getId(), viewerInfo.getId())) {
            throw UnauthorizedException.NEED_JOIN;
        }

        campaignCandidate.setUser(viewerInfo.getUser());
        campaignCandidateService.add(campaignCandidate);
    }

    @ApiOperation("캠페인 후보군 수정하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "campaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/{campaignCandidateId}", method = RequestMethod.PUT)
    @CheckAuthority(isOwner = true)
    public void modify(@RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.modify(campaignCandidate);
    }

    @ApiOperation("캠페인 후보군 삭제하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "campaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/{campaignCandidateId}", method = RequestMethod.DELETE)
    @CheckAuthority(isOwner = true)
    public void remove(@PathVariable("campaignCandidateId") long id) {
        campaignCandidateService.remove(id);
    }
}