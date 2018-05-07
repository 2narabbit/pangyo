package com.adinstar.pangyo.controller.view.fanClub;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.PangyoErrorMessage;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.LoginInfo;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.PollService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}/campaign-candidate")
public class CampaignCandidateController {

    @Autowired
    private StarService starService;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private PollService pollService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getList(@PathVariable("starId") long starId,
                          @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo,
                          Model model) {
        List<CampaignCandidate> campaignCandidateList = campaignCandidateService.getRunningList(starId, Optional.empty(), Optional.empty());
        List<Long> ids = campaignCandidateList.stream()
                .map(CampaignCandidate::getId)
                .collect(Collectors.toList());

        model.addAttribute(STAR, starService.getById(starId));
        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateList);
        model.addAttribute(POLLED_LIST, pollService.getContentIdList(PangyoEnum.ContentType.CANDIDATE, ids, loginInfo.getId()));

        return "fanClub/campaignCandidate/list";
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
                model.addAttribute(ViewModelName.ERROR_MESSAGE, PangyoErrorMessage.DUPLICATE_CANDIDATE_REGISTER);
                return "error/alert";
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