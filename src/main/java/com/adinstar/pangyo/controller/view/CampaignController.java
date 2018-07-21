package com.adinstar.pangyo.controller.view;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.Campaign;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CampaignService;
import com.adinstar.pangyo.service.CommentService;
import com.adinstar.pangyo.service.ExecutionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ExecutionRuleService executionRuleService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getList(Model model) {
        model.addAttribute(CAMPAIGN_FEED, campaignService.getRunningList(1L, 100));
        model.addAttribute(CAMPAIGN_EXECUTION_RULE, executionRuleService.getProgressExecuteRuleByType(PangyoEnum.ExecutionRuleType.CAMPAIGN));
        model.addAttribute(CAMPAIGN_RANK_BENEFITS, campaignService.getCampaignRankBenefits());
        return "campaign/list";
    }

    @RequestMapping(value = "/{campaignId}", method = RequestMethod.GET)
    public String get(@PathVariable("campaignId") long campaignId,
                      @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                      @ModelAttribute(ViewModelName.CAMPAIGN) Campaign campaign,
                      Model model) {

        model.addAttribute(CAMPAIGN_RANK_BENEFITS, campaignService.getCampaignRankBenefitsByCampaignIdAndExecutionRuleId(campaign.getId(), campaign.getExecuteRuleId()));
        model.addAttribute(COMMENT_FEED, commentService.getList(PangyoEnum.ContentType.CAMPAIGN, campaignId, Optional.empty(), (viewerInfo == null) ? null : viewerInfo.getId()));
        campaignService.updateViewCount(campaignId, 1);

        return "campaign/detail";
    }
}
