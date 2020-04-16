package com.adinstar.pangyo.controller.adviser;

import com.adinstar.pangyo.constant.PangyoAuthorizedKey;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@ControllerAdvice
public class ViewExceptionHandler {

    @ExceptionHandler({
            BadRequestException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            ServletRequestBindingException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public String handleBadRequestException(Exception e) throws UnsupportedEncodingException {
        return redirectErrorPage(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e) throws UnsupportedEncodingException {
        return redirectErrorPage(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) throws UnsupportedEncodingException {  // TODO : error code도 식별할 수 있도록 작업해야겠다.
        if (UnauthorizedException.NEED_LOGIN.equals(e)) {
            StringBuffer url = new StringBuffer("redirect:").append(PangyoAuthorizedKey.LOGIN_URL)
                .append("?continue=")
                .append(URLEncoder.encode(getRequestFullURL(request), "UTF-8"));

            return url.toString();
        }

        return redirectErrorPage(e.getMessage());
    }

    private String redirectErrorPage(String message) throws UnsupportedEncodingException {
        StringBuffer url = new StringBuffer("redirect:")
                .append(PangyoAuthorizedKey.ERRPR_URL)
                .append("?message=")
                .append(URLEncoder.encode(message, "UTF-8"));

        return url.toString();
    }

    private String getRequestFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public String handleServiceUnavailableException(ServiceUnavailableException e) throws UnsupportedEncodingException {
        return redirectErrorPage(e.getMessage());
    }

    @ExceptionHandler(InvalidConditionException.class)
    public String handleInvalidConditionException(InvalidConditionException e) throws UnsupportedEncodingException {
        return redirectErrorPage(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) throws UnsupportedEncodingException {
        return redirectErrorPage(e.getMessage());
    }
}
