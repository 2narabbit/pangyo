package com.adinstar.pangyo.controller;

import com.adinstar.pangyo.service.CampaignCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.CAMPAIGN_CANDIDATE;
import static com.adinstar.pangyo.constant.ViewModelName.CAMPAIGN_CANDIDATE_LIST;

@Controller
@RequestMapping("/campaign-candidate")
public class CampaignCandidateController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @RequestMapping(method = RequestMethod.GET)
    public String getRecentTurnList(@RequestParam("starId") long startId, Model model) {
        model.addAttribute(CAMPAIGN_CANDIDATE_LIST, campaignCandidateService.getRecentTurnList(startId, Optional.empty(), Optional.empty()));
        return "campaignCandidate/list";
    }

    @RequestMapping(value = "/{campaignCandidateId}", method = RequestMethod.GET)
    public String get(Model model, @PathVariable("campaignCandidateId") long campaignCandidateId) {
        model.addAttribute(CAMPAIGN_CANDIDATE, campaignCandidateService.getById(campaignCandidateId));
        return "campaignCandidate/detail";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@RequestParam(value = "id", required = false) Long id, Model model){
        if (id != null) {
            model.addAttribute(CAMPAIGN_CANDIDATE, campaignCandidateService.getById(id));
        }
        return "campaignCandidate/form";
    }
}
