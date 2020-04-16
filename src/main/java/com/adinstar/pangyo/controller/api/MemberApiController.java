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

    @ApiOperation("checkValidationRecommendCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "추천인 코드", paramType = "query", dataType = "String")
    })
    @GetMapping(value = "/valid/recommendCode")
    public Map<String, Boolean> isValid(@RequestParam(value = "code") String recommendCode) {
        return Collections.singletonMap("result", userService.isValidRecommendCode(recommendCode));
    }

    @ApiOperation("checkUsableName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "닉네임", paramType = "query", dataType = "String")
    })
    @GetMapping(value = "/valid/usable")
    public Map<String, Boolean> isUsable(@RequestParam(value = "name") String name) {
        return Collections.singletonMap("result", userService.isUsableName(name));
    }

    @ApiOperation("join")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "user object", paramType = "body", required = true, dataType = "User")
    })
    @PostMapping
    public void join(@ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo, @RequestBody User user) {
        if (loginInfo == null) {
            throw UnauthorizedException.NEED_LOGIN;
        }

        User alreadyJoinedUser = userService.get(loginInfo.getService(), loginInfo.getId());
        if (alreadyJoinedUser != null) {
            throw BadRequestException.DUPLICATE_USER;
        }

        if (user.getRecommendCode() != null && !userService.isValidRecommendCode(user.getRecommendCode())) {
            throw BadRequestException.INVALID_PARAM;
        }

        if (!userService.isUsableName(user.getName())) {
            throw BadRequestException.INVALID_PARAM;
        }

        user.setService(loginInfo.getService().name());
        user.setServiceUserId(String.valueOf(loginInfo.getId()));
        userService.add(user);
    }

    @ApiOperation("logout")
    @GetMapping(value = {"/logout"})
    @MustLogin
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        request.getSession().removeAttribute(PangyoAuthorizedKey.CONTINUE);
        LoginInterceptor.expireCookieOfAccessToken(request, response);
    }

    @ApiOperation("modify")
    @PutMapping(value = "/me")
    @MustLogin
    public void modify(@RequestBody User user,
                       @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        user.setId(viewerInfo.getId());
        userService.modify(user);
    }

    @ApiOperation("withdrawal")
    @DeleteMapping(value = "/me")
    @MustLogin
    public void withdrawal(HttpServletRequest request,
                           HttpServletResponse response,
                           @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        Map<String, Object> authMap = LoginInterceptor.getAuthInfoByCookie(request);
        loginService.unlink((PangyoEnum.AccountType) authMap.get(PangyoAuthorizedKey.SERVICE), (String) authMap.get(PangyoAuthorizedKey.ACCESS_TOKEN));
        userService.withdrawal(viewerInfo.getId());
        LoginInterceptor.expireCookieOfAccessToken(request, response);
    }
}