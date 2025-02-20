package com.vworks.wms.common_lib.service.impl;

import com.vworks.wms.common_lib.config.CommonLibConfigProperties;
import com.vworks.wms.common_lib.service.AppAuthorizer;
import com.vworks.wms.common_lib.utils.Commons;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("appAuthorizer")
public class AppAuthorizerImpl implements AppAuthorizer {
    private final CommonLibConfigProperties commonLibConfigProperties;

    @Override
    public boolean authorize(Authentication authentication, String action, Object callerObj) {
        log.info("{} authentication {}, callerObj {}", getClass().getSimpleName(), authentication, callerObj.getClass().getSimpleName());

        String securedPath = getCurrentRequestPath();
        String resourceName = callerObj.getClass().getSimpleName();
        log.info("{} securedPath {}", getClass().getSimpleName(), securedPath);
        if (StringUtils.isBlank(securedPath)) {
            return false;
        }

        if (Boolean.FALSE.equals(commonLibConfigProperties.getKeycloak().getEnable())
                || StringUtils.isBlank(action)) {
            return true;
        }

        if (hasAuthority(authentication, Commons.ROLE_REALM_ADMIN)
                || hasAuthority(authentication, Commons.ROLE_CLIENT_ADMIN)
                || hasAuthority(authentication, Commons.ROLE_REALM_ADMINISTRATOR)) {
            return true;
        }

        return hasAuthority(authentication, action);
    }

    private boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority::equals);
    }

    private String getCurrentRequestPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getRequestURI();
        }
        return null;
    }
}
