package com.adinstar.pangyo.controller.fanClub;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.LoginInfo;
import com.adinstar.pangyo.service.CampaignCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}/campaign-candidate")
public class CampaignCandidateController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getList(@PathVariable("starId") long starId,
                          Model model) {
        model.addAttribute(STAR_ID, starId);
        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateService.getRunningList(starId, Optional.empty(), Optional.empty()));
        return "fanClub/campaignCandidate/list";
    }

    @RequestMapping(value = "/{campaignCandidateId}", method = RequestMethod.GET)
    public String get(@PathVariable("starId") long starId,
                      @PathVariable("campaignCandidateId") long id,
                      @RequestParam(value = "viewCount", defaultValue = "1") int viewCount,
                      Model model) {
        model.addAttribute(STAR_ID, starId);
        model.addAttribute(CAMPAIGN_CANDIDATE, campaignCandidateService.getAndUpByStarIdAndId(starId, id, viewCount));
        return "fanClub/campaignCandidate/detail";
    }

    @MustLogin
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@PathVariable("starId") long starId,
                               @RequestParam(value = "campaignCandidateId", required = false) Long id,
                               HttpServletRequest request,
                               Model model) {

        model.addAttribute(STAR_ID, starId);
        if (id == null) { // 글 작성을 목적으로
            LoginInfo loginInfo = (LoginInfo) request.getAttribute(ViewModelName.AUTH);  // 이 부분도 AOP도 채워 넣을지는 고민해보자!
            CampaignCandidate campaignCandidate = campaignCandidateService.getRunningCandidateByStarIdAndUserId(starId, loginInfo.getId());
            if (campaignCandidate != null) {
                throw UnauthorizedException.DUPLICATE_CANDIDATE_REGISTER;
            }
        } else {  // 수정을 목적으로
            CampaignCandidate campaignCandidate = campaignCandidateService.getByStarIdAndId(starId, id);
            if (campaignCandidate == null) {
                throw NotFoundException.CAMPAIGN_CANDIDATE;
            }

            model.addAttribute(CAMPAIGN_CANDIDATE, campaignCandidate);
        }
        return "fanClub/campaignCandidate/form";
    }
}