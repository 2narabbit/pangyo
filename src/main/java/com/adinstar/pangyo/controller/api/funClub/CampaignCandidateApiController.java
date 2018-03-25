package com.adinstar.pangyo.controller.api.funClub;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.service.CampaignCandidateService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/fanClub/{starId}/campaign-candidate")
public class CampaignCandidateApiController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @ApiOperation("현재 진행 회차의 캠페인 후보군 list 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "page", value = "page number", paramType = "query", required = true, dataType = "int")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.GET)
    public List<CampaignCandidate> getRunningList(@PathVariable long starId, @RequestParam int page) {
        return campaignCandidateService.getRunningList(starId, Optional.of(page), Optional.empty());
    }

    @ApiOperation("캠페인 후보군 등록하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "CampaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.POST)
    @MustLogin
    public void add(@PathVariable long starId, @RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.add(starId, campaignCandidate);
    }

    @ApiOperation("캠페인 후보군 수정하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "campaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.PUT)
    @MustLogin
    public void modify(@PathVariable long starId, @RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.modify(starId, campaignCandidate);
    }

    @ApiOperation("캠페인 후보군 삭제하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "campaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.DELETE)
    @MustLogin
    public void remove(@PathVariable long starId, @RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.remove(starId, campaignCandidate);
    }


    ////////////////////////////////////////////////////// 2depth //////////////////////////////////////////////////////
    @ApiOperation("캠페인 후보군에 투표하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "campaignCandidateId", value = "campaignCandidate 의 id", paramType = "query", required = true, dataType = "long")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(value = "/{campaignCandidateId}/poll", method = RequestMethod.PUT)
    @MustLogin
    public void poll(@PathVariable long starId, @RequestParam("campaignCandidateId") long id, @RequestParam(value = "pollCount", defaultValue = "1") int pollCount) {
        campaignCandidateService.increasePollCount(starId, id, pollCount);
    }
}