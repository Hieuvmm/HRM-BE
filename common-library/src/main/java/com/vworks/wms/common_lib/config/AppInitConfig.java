package com.vworks.wms.common_lib.config;

import com.google.gson.Gson;
import com.vworks.wms.common_lib.service.CaffeineCacheService;
import com.vworks.wms.common_lib.service.KeycloakService;
import com.vworks.wms.common_lib.utils.Commons;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppInitConfig {
    private final CaffeineCacheService cacheService;
    private final KeycloakService keycloakService;
    private final CommonLibConfigProperties commonConfigProperties;
    @PostConstruct
    public void init() {
        log.info("{} has been initialized at {}.", getClass().getSimpleName(), new Timestamp(System.currentTimeMillis()));
        String cacheClientIdKey = commonConfigProperties.getKeycloak().getClientId();
        String cacheName = Commons.PREFIX_KEYCLOAK_CACHE_NAME + commonConfigProperties.getKeycloak().getRealm();
        String authorizationCacheName = Commons.PREFIX_APP_AUTHORIZATION_CACHE_NAME + commonConfigProperties.getKeycloak().getRealm();
        cacheService.regisCache(cacheName);
        cacheService.regisCache(authorizationCacheName);
        if (Boolean.TRUE.equals(commonConfigProperties.getKeycloak().getEnable())) {
            List<RoleRepresentation> clientRoles = keycloakService.getClientRoles(cacheClientIdKey);

            if (!CollectionUtils.isEmpty(clientRoles)) {
                Map<String, Object> mapRoles = new HashMap<>();
                Map<String, Object> mapRolePermissions = new HashMap<>();
                for (RoleRepresentation role : clientRoles) {
                    mapRoles.put(role.getName(), role);
                    mapRolePermissions.put(role.getName(), keycloakService.getRolePermissions(cacheClientIdKey, role.getId()));
                }

                String cacheRoleListKey = String.join("_", cacheClientIdKey, Commons.SUFFIX_ALL_ROLES_CACHE_KEY);
                String cacheRolePermissionListKey = String.join("_", cacheClientIdKey, Commons.SUFFIX_ROLE_PERMISSIONS_CACHE_KEY);
                cacheService.put(cacheRoleListKey, mapRoles, cacheName);
                cacheService.put(cacheRolePermissionListKey, mapRolePermissions, cacheName);
            }
        }
        log.info("{} has been finished at {}.", getClass().getSimpleName(), new Timestamp(System.currentTimeMillis()));
    }
}
