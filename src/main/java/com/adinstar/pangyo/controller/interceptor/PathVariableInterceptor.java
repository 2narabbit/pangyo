package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.*;
import com.adinstar.pangyo.service.CampaignCandidateService;
import com.adinstar.pangyo.service.CommentService;
import com.adinstar.pangyo.service.PostService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Order(2)
public class PathVariableInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StarService starService;

    @Autowired
    private PostService postService;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Autowired
    private CommentService commentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables == null) {
            return true;
        }

        ViewerInfo viewerInfo = (ViewerInfo) request.getAttribute(ViewModelName.VIEWER);

        boolean isCheckFan = false;
        boolean isCheckOwner = false;
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            CheckAuthority checkAuthorityClass = handlerMethod.getMethod().getDeclaringClass().getAnnotation(CheckAuthority.class);
            if (checkAuthorityClass != null) {
                isCheckFan = true;
                isCheckOwner = checkAuthorityClass.isOwner();
            }

            CheckAuthority checkAuthorityMethod = handlerMethod.getMethodAnnotation(CheckAuthority.class);
            if (checkAuthorityMethod != null) {
                isCheckFan = true;
                isCheckOwner = checkAuthorityMethod.isOwner();
            }
        }

        if (isCheckFan && (viewerInfo == null)) {
            throw UnauthorizedException.NEED_LOGIN;
        }

        boolean finalIsCheckFan = isCheckFan;
        boolean finalIsCheckOwner = isCheckOwner;
        pathVariables.forEach((pathValueName, pathValue) -> {
            if (((String) pathValue).startsWith("{")) {
                throw BadRequestException.INVALID_PATH;
            }

            if (pathValueName.equals("starId")) {
                Star star = starService.getById(Long.valueOf(String.valueOf(pathValue)));
                if (star == null) {
                    throw NotFoundException.STAR;
                }

                if (finalIsCheckFan && !starService.isJoined(star.getId(), viewerInfo.getId())) {
                    throw UnauthorizedException.NEED_JOIN;
                }
                request.setAttribute(ViewModelName.STAR, star);
            }

            if (pathValueName.equals("postId")) {
                Post post = postService.getById(Long.valueOf(String.valueOf(pathValue)));
                if (post == null) {
                    throw NotFoundException.POST;
                }

                if (finalIsCheckFan && !starService.isJoined(post.getStar().getId(), viewerInfo.getId())) {
                    throw UnauthorizedException.NEED_JOIN;
                }

                if (finalIsCheckOwner && post.getUser().getId() != viewerInfo.getId()) {
                    throw UnauthorizedException.NO_OWNER_SHIP;
                }
                request.setAttribute(ViewModelName.POST, post);
            }

            if (pathValueName.equals("campaignCandidateId")) {
                CampaignCandidate campaignCandidate = campaignCandidateService.getById(Long.valueOf(String.valueOf(pathValue)));
                if (campaignCandidate == null) {
                    throw NotFoundException.CAMPAIGN_CANDIDATE;
                }

                if (finalIsCheckFan && !starService.isJoined(campaignCandidate.getStar().getId(), viewerInfo.getId())) {
                    throw UnauthorizedException.NEED_JOIN;
                }

                if (finalIsCheckOwner && campaignCandidate.getUser().getId() != viewerInfo.getId()) {
                    throw UnauthorizedException.NO_OWNER_SHIP;
                }
                request.setAttribute(ViewModelName.CAMPAIGN_CANDIDATE, campaignCandidate);
            }

            if (pathValueName.equals("commentId")) {
                Comment comment = commentService.getById(Long.valueOf(String.valueOf(pathValue)));
                if (comment == null) {
                    throw NotFoundException.COMMENT;
                }

                if (finalIsCheckOwner && comment.getUser().getId() != viewerInfo.getId()) {
                    throw UnauthorizedException.NO_OWNER_SHIP;
                }
                request.setAttribute(ViewModelName.COMMENT, comment);
            }
        });

        return true;
    }
}
