package com.vworks.hs.admin_service.service;

import com.vworks.hs.admin_service.model.requestBody.PostLoginRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostRefreshTokenRequestBody;
import com.vworks.hs.admin_service.model.responseBody.PostLoginResponseBody;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public interface AuthService {
    PostLoginResponseBody postLogin(PostLoginRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostLoginResponseBody postRefreshToken(PostRefreshTokenRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Boolean postLogout(HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Map<String, Object> getProfile(Jwt jwt);

//    Object postFetchAccount(PostFetchAccountRequestBody requestBody, HttpServletRequest httpServletRequest);
}
