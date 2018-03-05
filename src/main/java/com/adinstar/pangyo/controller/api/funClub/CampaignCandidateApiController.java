package com.adinstar.pangyo.controller.api.funClub;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.interceptor.annotation.CheckAuthority;
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
@RequestMapping("/api/fanClub/{starId}/campaign-candidate")
public class CampaignCandidateApiController {

    private static int INCREASE_POLL_COUNT = 1;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @ApiOperation("getCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "page", value = "page number", paramType = "query", required = true, dataType = "int")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.GET)
    public List<CampaignCandidate> getRecentTurnList(@PathVariable long starId, @RequestParam int page) {
        return campaignCandidateService.getRunningList(starId, Optional.of(page), Optional.empty());
    }

    @ApiOperation("addCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "CampaignCandidate")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.POST)
    @MustLogin
    @CheckAuthority(type = CampaignCandidate.class, hasObject = PangyoEnum.CheckingType.OBJECT, isCheckOwner = false)
    public long add(@PathVariable long starId, @RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.add(campaignCandidate);
        return campaignCandidate.getId();
    }

    @ApiOperation("modifyCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "campaignCandidate", value = "campaignCandidate object", paramType = "body", required = true, dataType = "campaignCandidate")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(method = RequestMethod.PUT)
    @MustLogin
    @CheckAuthority(type = CampaignCandidate.class, hasObject = PangyoEnum.CheckingType.OBJECT)
    public long modify(@PathVariable long starId, @RequestBody CampaignCandidate campaignCandidate) {
        campaignCandidateService.modify(campaignCandidate);
        return campaignCandidate.getId();
    }

    @ApiOperation("removeCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "id", value = "campaignCandidate 의 id", paramType = "path", required = true, dataType = "long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @MustLogin
    @CheckAuthority(type = CampaignCandidate.class, hasObject = PangyoEnum.CheckingType.ID)
    public void remove(@PathVariable long starId, @PathVariable long id) {
        campaignCandidateService.remove(starId, id);
    }


    @ApiOperation("pollCampaignCandidate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "campaignCandidate 의 starId", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "id", value = "campaignCandidate 의 id", paramType = "path", required = true, dataType = "long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(value = "{id}/poll", method = RequestMethod.PUT)
    @MustLogin
    @CheckAuthority(type = CampaignCandidate.class, hasObject = PangyoEnum.CheckingType.ID)
    public void poll(@PathVariable long starId, @PathVariable long id) {
        campaignCandidateService.increasePollCount(starId, id, INCREASE_POLL_COUNT);
    }
}
