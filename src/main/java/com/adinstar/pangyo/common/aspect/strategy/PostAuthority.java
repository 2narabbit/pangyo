package com.adinstar.pangyo.common.aspect.strategy;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.HintKey;
import com.adinstar.pangyo.common.aspect.AuthorityStrategy;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("post")
public class PostAuthority implements AuthorityStrategy {

    @Autowired
    private PostService postService;

    @Override
    public boolean isValid(ViewerInfo viewerInfo, Method method, Object[] args, CheckAuthority checkAuthority) {
        Map paramMap = getParamMap(method.getParameters(), args);

        long starId = (long) paramMap.getOrDefault(HintKey.STAR_ID, 0);
        if (starId < 0) {
            throw BadRequestException.INVALID_PARAM;
        }

        Post post = null;
        switch (checkAuthority.checkType()) {
            case ID:
                long postId = (long) paramMap.getOrDefault(HintKey.POST_ID, 0);
                if (postId < 0) {
                    throw BadRequestException.INVALID_PARAM;
                }

                // fixme: 이게원래 starId와 postId 조합으로 post를 가져오는 부분이라 스타와 포스트의 일치여부를 확인할 수 있었는데, 포스트가 더이상 팬클럽 종속이 아니게 되면서 아래 로직은 사실상 무의미해져따
                post = postService.getById(postId);
                break;
            case OBJECT:
                post = (Post) paramMap.getOrDefault(HintKey.POST, post);
                if (post == null) {
                    throw BadRequestException.INVALID_PARAM;
                }
                break;
            default:
                throw BadRequestException.INVALID_PARAM;
        }

        if (post == null) {
            throw NotFoundException.CAMPAIGN_CANDIDATE;
        }

        if (starId != post.getStar().getId()) {
            throw NotFoundException.CAMPAIGN_CANDIDATE;
        }

        // 향후 로그인 로직이 추가되면 해당 if 문 수정하면 됨!!
        if (checkAuthority.isCheckOwner() && (post.getUser().getId() != post.getUser().getId() )) {  //viewerInfo.getId()
            throw UnauthorizedException.NO_OWNER_SHIP;
        }

        return true;
    }

    // 개선 : 이 부분을 이렇게 구리게 set하는게 아니라 param 정보로 한번에 가져올 수 없는지 고민해 보도록 한다!!!
    private Map getParamMap(Parameter[] parameters, Object[] args) {
        Map paramMap = new HashMap();
        for (int i = 0; i < parameters.length; i++) {
            HintKey hintKeyAnno = parameters[i].getDeclaredAnnotation(HintKey.class);
            if (hintKeyAnno == null) {
                continue;
            }

            switch (hintKeyAnno.value()) {
                case HintKey.STAR_ID:
                    paramMap.put(HintKey.STAR_ID, args[i]);
                    break;
                case HintKey.POST:
                    paramMap.put(HintKey.POST, args[i]);
                    break;
                case HintKey.POST_ID:
                    paramMap.put(HintKey.POST_ID, args[i]);
                    break;
                default:
                    throw BadRequestException.INVALID_PARAM;
            }
        }

        return paramMap;
    }
}
