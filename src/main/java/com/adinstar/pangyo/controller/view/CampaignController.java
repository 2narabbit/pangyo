package com.adinstar.pangyo.controller.view;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.Campaign;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CampaignService;
import com.adinstar.pangyo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.COMMENT_FEED;
import static com.adinstar.pangyo.constant.ViewModelName.RANK_BENEFITS;

@Controller
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/{campaignId}", method = RequestMethod.GET)
    public String get(@PathVariable("campaignId") long campaignId,
                      @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                      @ModelAttribute(ViewModelName.CAMPAIGN) Campaign campaign,
                      Model model) {

        model.addAttribute(RANK_BENEFITS, campaignService.getRankBenefitsOfCampaign(campaign));
        model.addAttribute(COMMENT_FEED, commentService.getList(PangyoEnum.ContentType.CAMPAIGN, campaignId, Optional.empty(), (viewerInfo == null) ? null : viewerInfo.getId()));
        campaignService.updateViewCount(campaignId, 1);

        return "campaign/detail";
    }

}
