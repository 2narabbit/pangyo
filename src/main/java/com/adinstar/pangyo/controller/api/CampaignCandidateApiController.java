package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.controller.interceptor.annotation.CheckOwnership;
import com.adinstar.pangyo.controller.interceptor.annotation.MustLogin;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.service.CampaignCandidateService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaign-candidate")
public class CampaignCandidateApiController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @ApiOperation("getCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "page", value = "page number", paramType = "query", required = true, dataType = "int")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.GET)
    public List<CampaignCandidate> getRecentTurnList(@RequestParam long starId, @RequestParam int page) {
        return campaignCandidateService.getRecentTurnList(starId, Optional.of(page), Optional.empty());
    }

    @ApiOperation("addCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "CampaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.POST)
    @MustLogin
    public long add(@RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.add(campaignCandidate);
        return campaignCandidate.getId();
    }

    @ApiOperation("modifyCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "campaignCandidate")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(method = RequestMethod.PUT)
    @MustLogin
    @CheckOwnership(type=CampaignCandidate.class)
    public long modify(@RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.modify(campaignCandidate);
        return campaignCandidate.getId();
    }

    @ApiOperation("removeCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "campaignCandidate 의 id", paramType = "query", required = true, dataType = "long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(method = RequestMethod.DELETE)
    @MustLogin
    @CheckOwnership(type=CampaignCandidate.class)
    public void remove(@RequestParam long id) {
        campaignCandidateService.remove(id);
    }
}
