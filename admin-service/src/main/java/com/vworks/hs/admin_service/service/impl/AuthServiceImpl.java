package com.vworks.hs.admin_service.service.impl;

import com.vworks.hs.admin_service.model.requestBody.PostLoginRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostRefreshTokenRequestBody;
import com.vworks.hs.admin_service.model.responseBody.PostLoginResponseBody;
import com.vworks.hs.admin_service.service.AuthService;
import com.vworks.hs.admin_service.utils.ASExceptionTemplate;
import com.vworks.hs.common_lib.config.CommonLibConfigProperties;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.common_lib.service.IdmService;
import com.vworks.hs.common_lib.utils.Commons;
import com.vworks.hs.common_lib.utils.ExceptionTemplate;
import com.vworks.hs.common_lib.utils.StatusUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CommonLibConfigProperties commonLibConfigProperties;
    private final IdmService idmService;

    @Override
    public PostLoginResponseBody postLogin(PostLoginRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postLogin requestBody {} ", getClass().getSimpleName(), requestBody);
        if (Boolean.FALSE.equals(commonLibConfigProperties.getKeycloak().getEnable())) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.INTERNAL_SERVER_ERROR.getCode(), ExceptionTemplate.INTERNAL_SERVER_ERROR.getMessage());
        }

        Map<Object, Object> properties = new HashMap<>();
        properties.put(Commons.FIELD_USER_NAME, requestBody.getUsername());
        properties.put(Commons.FIELD_PASSWORD, requestBody.getPassword());
        properties.put(Commons.FIELD_GRANT_TYPE, OAuth2Constants.PASSWORD);
        AccessTokenResponse accessTokenResponse = idmService.handleToFetchAccessToken(properties);
        if (Objects.isNull(accessTokenResponse)) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ASExceptionTemplate.LOGIN_FAILED.getCode(), ASExceptionTemplate.LOGIN_FAILED.getMessage());
        }
        return PostLoginResponseBody.builder()
                .accessToken(accessTokenResponse.getToken())
                .expiresIn((int) accessTokenResponse.getExpiresIn())
                .refreshToken(accessTokenResponse.getRefreshToken())
                .refreshExpiresIn((int) accessTokenResponse.getRefreshExpiresIn())
                .status(StatusUtil.ACTIVE.name())
                .build();
    }

    @Override
    public PostLoginResponseBody postRefreshToken(PostRefreshTokenRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        log.info("{} postRefreshToken for user {} ", getClass().getSimpleName(), requestBody.getUserCode());
        if (Boolean.FALSE.equals(commonLibConfigProperties.getKeycloak().getEnable())) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ExceptionTemplate.INTERNAL_SERVER_ERROR.getCode(), ExceptionTemplate.INTERNAL_SERVER_ERROR.getMessage());
        }

        Map<Object, Object> properties = new HashMap<>();
        properties.put(Commons.FIELD_GRANT_TYPE, OAuth2Constants.REFRESH_TOKEN);
        properties.put(Commons.FIELD_REFRESH_TOKEN, requestBody.getRefreshToken());
        AccessTokenResponse accessTokenResponse = idmService.handleToFetchAccessToken(properties);

        if (Objects.isNull(accessTokenResponse)) {
            throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), ASExceptionTemplate.REFRESH_TOKEN_FAILED.getCode(), ASExceptionTemplate.REFRESH_TOKEN_FAILED.getMessage());
        }

        return PostLoginResponseBody.builder()
                .accessToken(accessTokenResponse.getToken())
                .expiresIn((int) accessTokenResponse.getExpiresIn())
                .refreshToken(accessTokenResponse.getRefreshToken())
                .refreshExpiresIn((int) accessTokenResponse.getRefreshExpiresIn())
                .build();
    }

    @Override
    public Boolean postLogout(HttpServletRequest httpServletRequest) {
        log.info("{} postLogout for user {} ", getClass().getSimpleName(), httpServletRequest.getHeader(Commons.FIELD_USER_CODE));
        if (Boolean.FALSE.equals(commonLibConfigProperties.getKeycloak().getEnable())) {
            return Boolean.FALSE;
        }

        if (StringUtils.isEmpty(httpServletRequest.getHeader(Commons.FIELD_REFRESH_TOKEN))) {
            return Boolean.FALSE;
        }

        Map<Object, Object> properties = new HashMap<>();
        properties.put(Commons.FIELD_REFRESH_TOKEN, httpServletRequest.getHeader(Commons.FIELD_REFRESH_TOKEN));
        Object response = idmService.handleToLogOut(properties);

        log.info("{} postLogout with Keycloak response {} ", getClass().getSimpleName(), response);
        return Boolean.TRUE;

    }

//    @Override
//    public Map<String, Object> getProfile(Jwt jwt) {
//        String username = jwt.getClaimAsString(Commons.FIELD_USER_PROFILE);
//
//        Map<String, Object> realmAccess = jwt.getClaim(Commons.FIELD_REALM_ACCESS);
//        List<String> roles = Collections.emptyList();
//
//        if (realmAccess != null && realmAccess.get(Commons.FIELD_ROLES) instanceof List) {
//            roles = (List<String>) realmAccess.get(Commons.FIELD_ROLES);
//        }
//
//        return Map.of(
//                "username", username != null ? username : "",
//                "roles", roles
//        );
//    }

}
