package com.adinstar.pangyo.controller;


import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.PostService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.CAMPAIGN_CANDIDATE_LIST;
import static com.adinstar.pangyo.constant.ViewModelName.STAR;

@Controller
@RequestMapping("/fanClub/{starId}")
public class FanClubController {
    public static final int TOP_SIZE = 3;

    @Autowired
    private PostService postService;

    @Autowired
    private StarService starService;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getTopFeed(@PathVariable("starId") long starId, Model model) {
        model.addAttribute(STAR, starService.findById(starId));
        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateService.getRunningList(starId, Optional.empty(), Optional.of(TOP_SIZE)));
        model.addAttribute("response", postService.findAllByStarId(starId, null));  // ASK : 이름을 어떻게 하는게 좋을까?
        return "fanClub/list";
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public String getPostDetail(@PathVariable("starId") long starId,
                                @PathVariable("postId") long postId,
                                Model model) {
        postService.increaseViewCount(postId);

        model.addAttribute("post", postService.findById(postId));
        model.addAttribute("starId", starId);
        return "fanClub/post/detail";
    }

    // TODO : uri 가 적절하지 못한것 같다,, (/post/write로 하는게 맞을것 같기도 하고,,)
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getPostWriteForm(@PathVariable("starId") long starId,
                                   @RequestParam(value = "postId", required = false) Long postId,
                                   Model model) {
        if (postId != null) {
            model.addAttribute("post", postService.findById(postId));
        }
        model.addAttribute("star", starService.findById(starId));
        return "fanClub/post/form";
    }
}
