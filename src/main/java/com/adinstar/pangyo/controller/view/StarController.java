package com.adinstar.pangyo.controller.view;


import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/star")
public class StarController {

    public static final int LIST_SIZE = 20;

    @Autowired
    private StarService starService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getTopFeed(@ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                             Model model) {
        if (viewerInfo == null) {
            model.addAttribute(MY_STAR_FEED, FeedResponse.EMPTY_LIST);
            model.addAttribute(STAR_FEED, starService.getStarRankList(Optional.empty(), LIST_SIZE));
        } else {
            model.addAttribute(MY_STAR_FEED, starService.getJoinedStarRankListByUserId(viewerInfo.getId(), Optional.empty(), 3));
            model.addAttribute(STAR_FEED, starService.getNotJoinedStarRankListByUserId(viewerInfo.getId(), Optional.empty(), 3));
        }
        return "star/list";
    }

    // 여기 위치 하지 않는게 안 맞는것 같지만;;;; 별도 컨트롤러를 두는게ㅠㅠ
    @RequestMapping(value = {"/my"}, method = RequestMethod.GET)
    @MustLogin
    public String getMyStar(@ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                             Model model) {
        model.addAttribute(MY_STAR_FEED, starService.getJoinedStarRankListByUserId(viewerInfo.getId(), Optional.empty(), 5));
        return "star/my/list";
    }
}
