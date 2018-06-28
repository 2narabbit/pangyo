package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoAuthorizedKey;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.controller.interceptor.LoginInterceptor;
import com.adinstar.pangyo.model.User;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.model.authorization.LoginInfo;
import com.adinstar.pangyo.service.LoginService;
import com.adinstar.pangyo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    // api가 한번에 너무 많은 일은 하는게 아닐까??? 명시적으로 구분하는게 좋을 것 같은데...
    @ApiOperation("checkValidation")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recommendCode", value = "추천인 코드", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "닉네임", paramType = "query", dataType = "String")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(value = "/isValid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Boolean> isValid(@RequestParam(value = "recommendCode", required = false) String recommendCode,
                                        @RequestParam(value = "name", required = false) String name) {
        if (StringUtils.isEmpty(recommendCode) && StringUtils.isEmpty(name)){
            throw BadRequestException.INVALID_PARAM;
        }

        boolean result = true;
        if (!StringUtils.isEmpty(recommendCode)) {
            result = result && userService.isValidRecommendCode(recommendCode);
        }
        if (!StringUtils.isEmpty(name)) {
            result = result && userService.isUsableName(name);
        }

        return Collections.singletonMap("result", result);
    }

    @ApiOperation("join")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "user object", paramType = "body", required = true, dataType = "User")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.POST)
    public void join(@ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo, @RequestBody User user) {
        if (loginInfo == null) {
            throw UnauthorizedException.NEED_LOGIN;
        }

        User alreadyJoinedUser = userService.get(loginInfo.getService(), loginInfo.getId());
        if (alreadyJoinedUser != null) {
            throw BadRequestException.DUPLICATE_USER;
        }

        if (!userService.isUsableName(user.getName())) {
            throw BadRequestException.INVALID_PARAM;
        }

        user.setService(loginInfo.getService().name());
        user.setServiceUserId(String.valueOf(loginInfo.getId()));
        userService.add(user);  // 질문 : 추천인이 유효하지 않으면 어케해? 가입 안시켜 무시해? -- 보통 가입은 시키고 CS 들어오면 처리해준다.
    }

    @ApiOperation("logout")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    @MustLogin
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        request.getSession().removeAttribute(PangyoAuthorizedKey.CONTINUE);
        LoginInterceptor.expireCookieOfAccessToken(request, response);
    }

    // checked! PUT 요청시 body에 id를 반드시 넣어야 하는가? body는 변경될 데이터만 들어가야 하지 않을까?
    // me 요청인데 꼭 validation을 체크해야하는가? 걍 수정해줘야 하는가? 아오ㅠ api 설계 너무 어렵다.
    @ApiOperation("modify")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/me", method = RequestMethod.PUT)
    @MustLogin
    public void modify(@RequestBody User user,
                       @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        if (viewerInfo.getId() != user.getId()) {
            throw BadRequestException.INVALID_PARAM;
        }
        userService.modify(user);
    }

    @ApiOperation("withdrawal")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(value = "/me", method = RequestMethod.DELETE)
    @MustLogin
    public void withdrawal(HttpServletRequest request,
                           HttpServletResponse response,
                           @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        Map<String, Object> authMap = LoginInterceptor.getAuthInfoByCookie(request);
//        loginService.unlink((PangyoEnum.AccountType) authMap.get(PangyoAuthorizedKey.SERVICE), (String) authMap.get(PangyoAuthorizedKey.ACCESS_TOKEN));
        userService.withdrawal(viewerInfo.getId());
        LoginInterceptor.expireCookieOfAccessToken(request, response);
    }
}