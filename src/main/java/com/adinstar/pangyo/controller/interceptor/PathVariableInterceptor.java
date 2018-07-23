package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.*;
import com.adinstar.pangyo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
@Order(2)
public class PathVariableInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CampaignService campaignService;

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
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        // TODO: 진짜 메소드가 해당 @RequestParam 쿼리파라미터를 변수로 받는지 확인
        Map paramVariables = request.getParameterMap();
        if (pathVariables == null && paramVariables == null) {
            return true;
        }

        ViewerInfo viewerInfo = (ViewerInfo) request.getAttribute(ViewModelName.VIEWER);

        boolean isCheckFan = false;
        boolean isCheckOwner = false;
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            CheckAuthority checkAuthorityClass = handlerMethod.getMethod().getDeclaringClass().getAnnotation(CheckAuthority.class);
            if (checkAuthorityClass != null) {
                isCheckFan = checkAuthorityClass.isFan();
                isCheckOwner = checkAuthorityClass.isOwner();
            }

            CheckAuthority checkAuthorityMethod = handlerMethod.getMethodAnnotation(CheckAuthority.class);
            if (checkAuthorityMethod != null) {
                isCheckFan = checkAuthorityMethod.isFan();
                isCheckOwner = checkAuthorityMethod.isOwner();
            }
        }

        if ((isCheckFan || isCheckOwner) && (viewerInfo == null)) {
            throw UnauthorizedException.NEED_LOGIN;
        }

        final boolean finalIsCheckFan = isCheckFan;
        final boolean finalIsCheckOwner = isCheckOwner;
        BiConsumer checkAuthority = (name, value) -> {
            if (((String) value).startsWith("{")) {
                throw BadRequestException.INVALID_PATH;
            }

            if (name.equals("campaignId")) {
                Campaign campaign = campaignService.getById(Long.valueOf(String.valueOf(value)));
                if (campaign == null) {
                    throw NotFoundException.CAMPAIGN;
                }

                if (campaign.getCampaignCandidate() == null) {
                    throw InvalidConditionException.NOT_FOUND_CAMPAIGN_CANDIDATE;
                }

                if (finalIsCheckFan) {
                    throw InvalidConditionException.UNSUPPORTED_ANNOTATION;
                }

                if (finalIsCheckOwner) {
                    throw InvalidConditionException.UNSUPPORTED_ANNOTATION;
                }
                request.setAttribute(ViewModelName.CAMPAIGN, campaign);

            } else if (name.equals("starId")) {
                Star star = starService.getById(Long.valueOf(String.valueOf(value)));
                if (star == null) {
                    throw NotFoundException.STAR;
                }

                if (finalIsCheckFan && !starService.isJoined(star.getId(), viewerInfo.getId())) {
                    throw UnauthorizedException.NEED_JOIN;
                }
                request.setAttribute(ViewModelName.STAR, star);

            } else if (name.equals("postId")) {
                Post post = postService.getById(Long.valueOf(String.valueOf(value)));
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

            } else if (name.equals("campaignCandidateId")) {
                CampaignCandidate campaignCandidate = campaignCandidateService.getById(Long.valueOf(String.valueOf(value)));
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

            } else if (name.equals("commentId")) {
                Comment comment = commentService.getById(Long.valueOf(String.valueOf(value)));
                if (comment == null) {
                    throw NotFoundException.COMMENT;
                }

                if (finalIsCheckFan) {
                    throw InvalidConditionException.UNSUPPORTED_ANNOTATION;
                }

                if (finalIsCheckOwner && comment.getUser().getId() != viewerInfo.getId()) {
                    throw UnauthorizedException.NO_OWNER_SHIP;
                }
                request.setAttribute(ViewModelName.COMMENT, comment);
            }
        };

        pathVariables.forEach(checkAuthority);
        paramVariables.forEach((name, valueAry) -> {
            if (((String[]) valueAry).length > 0) {
                checkAuthority.accept(name, ((String[]) valueAry)[0]);
            }
        });

        return true;
    }
}
