package com.vworks.hs.common_lib.config;

import com.vworks.hs.common_lib.service.CaffeineCacheService;
import com.vworks.hs.common_lib.service.IdmService;
import com.vworks.hs.common_lib.utils.Commons;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppInitConfig {
    private final CaffeineCacheService cacheService;
    private final IdmService idmService;
    private final CommonLibConfigProperties commonConfigProperties;
    @PostConstruct
    public void init() {
        try {
            log.info("{} has been initialized at {}.", getClass().getSimpleName(), new Timestamp(System.currentTimeMillis()));
            String realm = commonConfigProperties.getKeycloak().getRealm();
            String clientId = commonConfigProperties.getKeycloak().getClientId();
            String cacheName = Commons.PREFIX_KEYCLOAK_CACHE_NAME + realm;
            String authorizationCacheName = Commons.PREFIX_APP_AUTHORIZATION_CACHE_NAME + realm;
            cacheService.regisCache(cacheName);
            cacheService.regisCache(authorizationCacheName);
            if (Boolean.TRUE.equals(commonConfigProperties.getKeycloak().getEnable())) {
                idmService.getAllAppResources(realm, clientId);
                idmService.getAllAppPermissions(realm, clientId);
                idmService.getAllAppRoles(realm, clientId);
            }
            log.info("{} has been finished at {}.", getClass().getSimpleName(), new Timestamp(System.currentTimeMillis()));
        }catch (Exception e) {
            log.info("{} has a error {}", getClass().getSimpleName(), e);
        }

    }
}
