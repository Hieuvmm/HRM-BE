package com.vworks.wms.admin_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.cache.CaffeineCacheService;
import com.vworks.wms.common_lib.config.CommonLibConfigProperties;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.utils.Commons;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${as-properties.api-prefix}/test")
@CrossOrigin("*")
@Slf4j
public class TestController {
    private final CommonLibConfigProperties commonConfigProperties;
    private final CaffeineCacheService cacheService;
    private final JwtDecoder jwtDecoder;
    @PostMapping("/info")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'INFO', this)")
//    @PreAuthorize("@appAuthorizer.authorize(authentication, null, this)")
    public BaseResponse<Object> postTestInfo(HttpServletRequest request) throws WarehouseMngtSystemException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = authHeader.replace("Bearer ", "");
        Jwt jwt = jwtDecoder.decode(accessToken);
        String authorizationCacheName = Commons.PREFIX_APP_AUTHORIZATION_CACHE_NAME + commonConfigProperties.getKeycloak().getRealm();
        log.info("{} cache size {}", getClass().getSimpleName(), cacheService.size(authorizationCacheName));
        return new BaseResponse<>(cacheService.get(authorizationCacheName, jwt.getClaimAsString("sid")));
    }
}
