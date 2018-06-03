package com.adinstar.pangyo.controller.adviser;

import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.*;
import com.adinstar.pangyo.model.authorization.LoginInfo;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ModelAttributeAdviser {

    @ModelAttribute(ViewModelName.AUTH)
    public LoginInfo setAuth(HttpServletRequest request) {
        return (LoginInfo) request.getAttribute(ViewModelName.AUTH);
    }

    @ModelAttribute(ViewModelName.VIEWER)
    public ViewerInfo setViewer(HttpServletRequest request) {
        return (ViewerInfo) request.getAttribute(ViewModelName.VIEWER);
    }

    @ModelAttribute(ViewModelName.STAR)
    public Star setStar(HttpServletRequest request) {
        return (Star) request.getAttribute(ViewModelName.STAR);
    }

    @ModelAttribute(ViewModelName.POST)
    public Post setPost(HttpServletRequest request) {
        return (Post) request.getAttribute(ViewModelName.POST);
    }

    @ModelAttribute(ViewModelName.CAMPAIGN_CANDIDATE)
    public CampaignCandidate setCampaignCandidate(HttpServletRequest request) {
        return (CampaignCandidate) request.getAttribute(ViewModelName.CAMPAIGN_CANDIDATE);
    }

    @ModelAttribute(ViewModelName.COMMENT)
    public Comment setComment(HttpServletRequest request) {
        return (Comment) request.getAttribute(ViewModelName.COMMENT);
    }
}
