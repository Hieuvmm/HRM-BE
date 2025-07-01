package com.vworks.hs.common_lib.service;

import org.springframework.security.core.Authentication;

public interface AppAuthorizer {
    boolean authorize(Authentication authentication, String action, Object callerObj);
}
