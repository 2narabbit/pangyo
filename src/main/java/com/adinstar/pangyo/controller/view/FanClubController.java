package com.adinstar.pangyo.controller.view;


import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}")
@MustLogin
@CheckAuthority
public class FanClubController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private PostService postService;

    @GetMapping
    public String getTopFeed(@PathVariable("starId") long starId,
                             @RequestParam(defaultValue = "10") int size,
                             @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                             Model model) {
        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateService.getRunningList(starId, Optional.of(1), Optional.of(2), null).getList());
        model.addAttribute(POST_FEED, postService.getListByStarId(starId, Optional.empty(), size, Optional.ofNullable(viewerInfo).map(v -> v.getId()).orElse(null)));
        return "fanClub/list";
    }
}
