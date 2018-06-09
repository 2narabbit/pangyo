package com.adinstar.pangyo.controller.view.fanClub;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.ExecutionRuleService;
import com.adinstar.pangyo.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}/campaign-candidate")
@MustLogin
@CheckAuthority
public class CampaignCandidateController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private PollService pollService;

    @Autowired
    private ExecutionRuleService executionRuleService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getList(@PathVariable("starId") long starId,
                          @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                          Model model) {
        List<CampaignCandidate> campaignCandidateList = campaignCandidateService.getRunningList(starId, Optional.empty(), Optional.empty());
        List<Long> ids = campaignCandidateList.stream()
                .map(CampaignCandidate::getId)
                .collect(Collectors.toList());

        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateList);
        model.addAttribute(POLLED_LIST, pollService.getContentIdList(PangyoEnum.ContentType.CANDIDATE, ids, viewerInfo.getId()));
        model.addAttribute(AD_EXECUTION_RULE, executionRuleService.getAdExecutionRuleByProgressExecuteRuleType(PangyoEnum.ExecutionRuleType.CANDIDATE));

        return "fanClub/campaignCandidate/list";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm() {
        return "fanClub/campaignCandidate/form";
    }
}