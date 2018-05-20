package com.adinstar.pangyo.controller.adviser;

import com.adinstar.pangyo.constant.PangyoAuthorizedKey;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(Exception e) {
        return "/error/alert";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        return "/error/alert";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) throws UnsupportedEncodingException {  // TODO : error code도 식별할 수 있도록 작업해야겠다.
        StringBuffer url = new StringBuffer("redirect:")
                .append(PangyoAuthorizedKey.LOGIN_URL)
                .append("?continue=")
                .append(URLEncoder.encode(getRequestFullURL(request), "UTF-8"));

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
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public String handleServiceUnavailableException(ServiceUnavailableException e) {
        return "/error/alert";
    }

    @ExceptionHandler(InvalidConditionException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String handleInvalidConditionException(InvalidConditionException e) {
        return "/error/alert";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllException(Exception e) {
        return "/error/alert";
    }
}
