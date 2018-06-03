package com.adinstar.pangyo.controller.view;


import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.LikeService;
import com.adinstar.pangyo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getTopFeed(@PathVariable("starId") long starId,
                             @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                             Model model) {
        FeedResponse<Post> postFeedResponse = postService.getListByStarId(starId, Optional.empty());
        List<Long> ids = postFeedResponse.getList().stream()
                .map(Post::getId)
                .collect(Collectors.toList());

        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateService.getRunningList(starId, Optional.empty(), Optional.of(2)));
        model.addAttribute(POST_FEED, postFeedResponse);
        model.addAttribute(LIKED_LIST, CollectionUtils.isEmpty(ids) ? Collections.EMPTY_LIST :
                likeService.getContentIdList(PangyoEnum.ContentType.POST, ids, viewerInfo.getId()));

        return "fanClub/list";
    }
}
