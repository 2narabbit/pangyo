package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.authentication.LoginInfo;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.controller.interceptor.annotation.CheckAuthority;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.service.CampaignCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class CheckOwnershipInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CheckAuthority checkAuthorityAnno = handlerMethod.getMethodAnnotation(CheckAuthority.class);
            if (checkAuthorityAnno == null) {
                return true;
            }

            LoginInfo loginInfo = (LoginInfo) request.getAttribute(ViewModelName.AUTH);
            if (loginInfo == null) {
                throw UnauthorizedException.NEED_LOGIN;
            }

            // 이부분에 대해서 좀더 나이스 하게 할 수 있는 방법은 없을까?? 구려구려-
            if (checkAuthorityAnno.type() == CampaignCandidate.class) {
//                final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//                long starId = Long.parseLong(pathVariables.get("starId"));
//                CampaignCandidate campaignCandidate;
//                switch (checkAuthorityAnno.hasObject()) {
//                    case ID:
//                        long id = Long.parseLong(request.getParameter("id"));
//
//                        campaignCandidate = campaignCandidateService.getByStarIdAndId(starId, id);
//                        if (campaignCandidate == null) {
//                            throw NotFoundException.CAMPAIGN_CANDIDATE;
//                        }
//                        break;
//                    case OBJECT:
//                        campaignCandidate = (CampaignCandidate) request.getAttribute(ViewModelName.CAMPAIGN_CANDIDATE);
//                        if (starId != campaignCandidate.getStar().getId()) {
//                            throw NotFoundException.CAMPAIGN_CANDIDATE;
//                        }
//
//                        campaignCandidate = campaignCandidateService.getByStarIdAndId(starId, campaignCandidate.getId());
//                        if (campaignCandidate == null) {
//                            throw NotFoundException.CAMPAIGN_CANDIDATE;
//                        }
//                        break;
//                    default:
//                        throw new NotImplementedException();
//                }
//
//
//                if (checkAuthorityAnno.isCheckOwner() && campaignCandidate.getUser().getId() == loginInfo.getId()) {
//                    return true;
//                }
//
//                throw UnauthorizedException.NO_OWNER_SHIP;
            }
        }
        return true;
    }
}
