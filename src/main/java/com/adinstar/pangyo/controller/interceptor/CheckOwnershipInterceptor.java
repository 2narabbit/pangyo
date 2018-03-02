package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.authentication.LoginInfo;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.controller.interceptor.annotation.CheckOwnership;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.service.CampaignCandidateService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckOwnershipInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CheckOwnership checkOwnershipAnno = handlerMethod.getMethodAnnotation(CheckOwnership.class);
            if (checkOwnershipAnno == null) {
                return true;
            }

            LoginInfo loginInfo = (LoginInfo) request.getAttribute(ViewModelName.AUTH);
            if (loginInfo == null) {
                throw UnauthorizedException.NEED_LOGIN;
            }

            // 이부분에 대해서 좀더 나이스 하게 할 수 있는 방법은 없을까?? 구려구려-
            if (checkOwnershipAnno.type() == CampaignCandidate.class) {
                CampaignCandidate campaignCandidate = campaignCandidateService.getById(Long.parseLong(request.getParameter("id")));
                if (campaignCandidate == null) {
                    throw new NotFoundException("존재하지 않습니다.");
                }

                if (campaignCandidate.getUser().getId() == loginInfo.getId()){
                    return true;
                }

                throw UnauthorizedException.NO_OWNER_SHIP;
            }
        }
        return true;
    }
}
