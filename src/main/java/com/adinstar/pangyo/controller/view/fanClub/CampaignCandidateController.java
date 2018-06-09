package com.adinstar.pangyo.controller.view.fanClub;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.PangyoErrorMessage;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.ExecutionRuleService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}/campaign-candidate")
public class CampaignCandidateController {

    @Autowired
    private StarService starService;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private ExecutionRuleService executionRuleService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getList(@PathVariable("starId") long starId,
                          @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                          Model model) {
        model.addAttribute(STAR, starService.getById(starId));
        model.addAttribute(CAMPAIGN_CANDIDATE_FEED, campaignCandidateService.getRunningList(starId, Optional.empty(), Optional.empty(), viewerInfo == null ? null : viewerInfo.getId()));
        model.addAttribute(AD_EXECUTION_RULE, executionRuleService.getAdExecutionRuleByProgressExecuteRuleByType(PangyoEnum.ExecutionRuleType.CANDIDATE));

        return "fanClub/campaignCandidate/list";
    }

    @MustLogin
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@PathVariable("starId") long starId,
                               @RequestParam(value = "campaignCandidateId", required = false) Long id,
                               @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                               Model model) {

        model.addAttribute(STAR_ID, starId);
        if (id == null) { // 글 작성을 목적으로
            CampaignCandidate campaignCandidate = campaignCandidateService.getRunningCandidateByStarIdAndUserId(starId, viewerInfo.getId());
            if (campaignCandidate != null) {
                model.addAttribute(ViewModelName.ERROR_MESSAGE, PangyoErrorMessage.DUPLICATE_CANDIDATE_REGISTER);
                return "error/alert";
            }
        } else {  // 수정을 목적으로
            CampaignCandidate campaignCandidate = campaignCandidateService.getById(id);
            if (campaignCandidate == null) {
                throw NotFoundException.CAMPAIGN_CANDIDATE;
            }

            model.addAttribute(CAMPAIGN_CANDIDATE, campaignCandidate);
        }
        return "fanClub/campaignCandidate/form";
    }
}