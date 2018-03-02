package com.adinstar.pangyo.controller;

import com.adinstar.pangyo.service.CampaignCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/campaign-candidate")
public class CampaignCandidateController {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @RequestMapping(method = RequestMethod.GET)
    public String getRecentTurnList(@RequestParam("starId") long startId, Model model) {
        model.addAttribute("campaignCandidateList", campaignCandidateService.getRecentTurnList(startId, null, null));
        return "campaignCandidate/list";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@RequestParam(value = "id", required = false) Long id, Model model){
        if (id != null){
            model.addAttribute("campaignCandidate", campaignCandidateService.getById(id));
        }
        return "campaignCandidate/form";
    }
}
