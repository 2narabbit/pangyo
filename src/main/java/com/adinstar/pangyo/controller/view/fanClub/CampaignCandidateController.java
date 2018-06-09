package com.adinstar.pangyo.controller.view.fanClub;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.ExecutionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}/campaign-candidate")
@MustLogin
@CheckAuthority
public class CampaignCandidateController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private ExecutionRuleService executionRuleService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getList(@PathVariable("starId") long starId,
                          @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                          Model model) {
        model.addAttribute(CAMPAIGN_CANDIDATE_FEED, campaignCandidateService.getRunningList(starId, Optional.empty(), Optional.empty(), viewerInfo == null ? null : viewerInfo.getId()));
        model.addAttribute(AD_EXECUTION_RULE, executionRuleService.getAdExecutionRuleByProgressExecuteRuleType(PangyoEnum.ExecutionRuleType.CANDIDATE));

        return "fanClub/campaignCandidate/list";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm() {
        return "fanClub/campaignCandidate/form";
    }
}