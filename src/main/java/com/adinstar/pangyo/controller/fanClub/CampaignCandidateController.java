package com.adinstar.pangyo.controller.fanClub;

import com.adinstar.pangyo.service.CampaignCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
                      Model model) {
        model.addAttribute(CAMPAIGN_CANDIDATE, campaignCandidateService.getByStarIdAndId(starId, id));
        return "fanClub/campaignCandidate/detail";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@PathVariable("starId") long starId,
                               @RequestParam(value = "id", required = false) Long id,
                               Model model) {
        model.addAttribute(STAR_ID, starId);
        if (id != null) {
            model.addAttribute(CAMPAIGN_CANDIDATE, campaignCandidateService.getByStarIdAndId(starId, id));
        }
        return "fanClub/campaignCandidate/form";
    }
}