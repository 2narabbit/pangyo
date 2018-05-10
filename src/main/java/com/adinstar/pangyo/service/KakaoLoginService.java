package com.adinstar.pangyo.service;

import com.adinstar.pangyo.model.authorization.KakaoLoginInfo;
import com.adinstar.pangyo.model.authorization.KOauthInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

/*
 * https://developers.kakao.com/docs/restapi/user-management#%EC%95%B1-%EC%97%B0%EA%B2%B0
 */

@Service
public class KakaoLoginService {

    private static final String SCHEME = "https";
    private static final String AUTH_HOST = "kauth.kakao.com";
    private static final String API_HOST = "kapi.kakao.com";

    private static final String GET_TOKEN_URL = "/oauth/token";
    private static final String LOGOUT = "/v1/user/logout";
    private static final String SIGNUP_URL = "/v1/user/signup";
    private static final String UNLINK_URL = "/v1/user/unlink";
    private static final String ME_DATA_URL = "/v1/user/me";

    @Value("${kakaotalk.rest-api}")
    private String CLIENT_ID;
    @Value("${kakaotalk.redirect-uri}")
    private String REDIRECT_URI;

    @Autowired
    private ObjectMapper mapper;

    private URI getRequestUri(final String requestPath) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(API_HOST).path(requestPath).build().toUri();
    }

    public KOauthInfo getKOauthInfo(final String authorizedCode) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(AUTH_HOST)
                .path(GET_TOKEN_URL)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("code", authorizedCode)
                .build()
                .encode()
                .toUri();

        RestTemplate responseTemplate = new RestTemplate();
        String responseStr = responseTemplate.getForObject(uri, String.class);

        KOauthInfo KOauthInfo = null;
        try {
            KOauthInfo = mapper.readValue(responseStr, KOauthInfo.class);
            KOauthInfo.setLoginDateTime(LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();  // try-catch로 잡을지 throw 할 지 고민해보자!
        }

        return KOauthInfo;
    }

    public KOauthInfo refreshToken(final String refreshToken) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(AUTH_HOST)
                .path(GET_TOKEN_URL)
                .queryParam("grant_type", "refresh_token")
                .queryParam("client_id", CLIENT_ID)
                .queryParam("refresh_token", refreshToken)
                .build()
                .encode()
                .toUri();

        RestTemplate responseTemplate = new RestTemplate();
        String responseStr = responseTemplate.getForObject(uri, String.class);

        KOauthInfo kOauthInfo = null;
        try {
            kOauthInfo = mapper.readValue(responseStr, KOauthInfo.class);
            kOauthInfo.setLoginDateTime(LocalDateTime.now());
            if (kOauthInfo.getRefreshToken() == null){
                kOauthInfo.setRefreshToken(refreshToken);
            }
        } catch (IOException e) {
            e.printStackTrace();  // try-catch로 잡을지 throw 할 지 고민해보자!
        }

        return kOauthInfo;
    }

    public String logout(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> logoutString = restTemplate.exchange(getRequestUri(LOGOUT), HttpMethod.POST, new HttpEntity(null, headers), String.class);
        return (logoutString == null) ? null : logoutString.getBody();
    }

    // (?)  왜 때문에 signup이 정상적으로 동작하지 않을까? 400 이라니...
    public String signup(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> signupString = restTemplate.exchange(getRequestUri(SIGNUP_URL), HttpMethod.POST, new HttpEntity(null, headers), String.class);
        return (signupString == null) ? null :signupString.getBody();
    }

    public String unlink(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> unlinkString = restTemplate.exchange(getRequestUri(UNLINK_URL), HttpMethod.POST, new HttpEntity(null, headers), String.class);
        return (unlinkString == null) ? null : unlinkString.getBody();
    }

    public KakaoLoginInfo getKakaoLoginInfo(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entityForString = restTemplate.exchange(getRequestUri(ME_DATA_URL), HttpMethod.GET, new HttpEntity<>("", headers), String.class);
        ResponseEntity<KakaoLoginInfo> entity = restTemplate.exchange(getRequestUri(ME_DATA_URL), HttpMethod.GET, new HttpEntity<>("", headers), KakaoLoginInfo.class);

        return entity.getBody();
    }
}
