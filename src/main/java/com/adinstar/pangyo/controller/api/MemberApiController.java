package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoAuthorizedKey;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.controller.interceptor.LoginInterceptor;
import com.adinstar.pangyo.model.User;
import com.adinstar.pangyo.model.ViwerInfo;
import com.adinstar.pangyo.model.authorization.LoginInfo;
import com.adinstar.pangyo.service.LoginService;
import com.adinstar.pangyo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @ApiOperation("getRecommendedUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recommendCode", value = "추천인 코드", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = User.class)})
    @RequestMapping(method = RequestMethod.GET)
    public User getRecommendedUser(@RequestParam String recommendCode) {
        if (recommendCode.isEmpty()) {
            return null;
        }
        return userService.getRecommendedUser(recommendCode);
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
            throw BadRequestException.DUPLICATE_USER_REGISTER;
        }

        user.setService(loginInfo.getService().name());
        user.setServiceUserId(String.valueOf(loginInfo.getId()));
        userService.add(user);  // 질문 : 추천인이 유효하지 않으면 어케해? 가입 안시켜 무시해? -- 보통 가입은 시키고 CS 들어오면 처리해준다.
    }

    @ApiOperation("withdrawal")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.DELETE)
    @MustLogin
    public void withdrawal(HttpServletRequest request,
                           HttpServletResponse response,
                           @ModelAttribute(ViewModelName.VIEWER) ViwerInfo viwerInfo) {
        Map<String, Object> authMap = LoginInterceptor.getAuthInfoByCookie(request);
        loginService.unlink((PangyoEnum.AccountType) authMap.get(PangyoAuthorizedKey.SERVICE), (String) authMap.get(PangyoAuthorizedKey.ACCESS_TOKEN));
        userService.withdrawal(viwerInfo.getId());
        LoginInterceptor.expireCookieOfAccessToken(request, response);
    }

    @ApiOperation("logout")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    @MustLogin
    public void logout(HttpServletRequest request,
                           HttpServletResponse response,
                           @ModelAttribute(ViewModelName.VIEWER) ViwerInfo viwerInfo) {
        LoginInterceptor.expireCookieOfAccessToken(request, response);
    }
}