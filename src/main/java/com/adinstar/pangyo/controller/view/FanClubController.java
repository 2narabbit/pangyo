package com.adinstar.pangyo.controller.view;


import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.PostService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.CAMPAIGN_CANDIDATE_LIST;
import static com.adinstar.pangyo.constant.ViewModelName.STAR;

@Controller
@RequestMapping("/fanClub/{starId}")
public class FanClubController {

    @Autowired
    private StarService starService;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getTopFeed(@PathVariable("starId") long starId, Model model) {
        model.addAttribute(STAR, starService.getById(starId));
        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateService.getRunningList(starId, Optional.of(1), Optional.of(2)));
        // TODO: 수정
        model.addAttribute("response", postService.getListByStarId(starId, Optional.empty()));
        return "fanClub/list";
    }
}
