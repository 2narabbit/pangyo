package com.adinstar.pangyo.service;

import com.adinstar.pangyo.model.authorization.kakao.KOauthInfo;
import com.adinstar.pangyo.model.authorization.kakao.KTokenInfo;
import com.adinstar.pangyo.model.authorization.kakao.KakaoLoginInfo;
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
    private static final String TOKEN_DATA_URL = "/v1/user/access_token_info";

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
        } catch (IOException e) {
            e.printStackTrace();
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
            if (kOauthInfo.getRefreshToken() == null) {
                kOauthInfo.setRefreshToken(refreshToken);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kOauthInfo;
    }

    public String logout(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.exchange(getRequestUri(LOGOUT), HttpMethod.POST, new HttpEntity(null, headers), String.class);
        return (entity == null) ? null : entity.getBody();
    }

    public String signup(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.exchange(getRequestUri(SIGNUP_URL), HttpMethod.POST, new HttpEntity(null, headers), String.class);
        return (entity == null) ? null : entity.getBody();
    }

    public String unlink(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.exchange(getRequestUri(UNLINK_URL), HttpMethod.POST, new HttpEntity(null, headers), String.class);
        return (entity == null) ? null : entity.getBody();
    }

    public KakaoLoginInfo getKakaoLoginInfo(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoLoginInfo> entity = restTemplate.exchange(getRequestUri(ME_DATA_URL), HttpMethod.POST, new HttpEntity<>(null, headers), KakaoLoginInfo.class);
        return (entity == null) ? null : entity.getBody();
    }

    public KTokenInfo getKTokenInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KTokenInfo> entity = restTemplate.exchange(getRequestUri(TOKEN_DATA_URL), HttpMethod.GET, new HttpEntity<>(null, headers), KTokenInfo.class);
        return (entity == null) ? null : entity.getBody();
    }
}
