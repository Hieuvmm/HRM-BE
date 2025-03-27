package com.vworks.wms.common_lib.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vworks.wms.common_lib.config.CommonLibConfigProperties;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.model.request.KeyCloakUpdateUserAttributeRequest;
import com.vworks.wms.common_lib.service.CaffeineCacheService;
import com.vworks.wms.common_lib.service.KeycloakService;
import com.vworks.wms.common_lib.utils.Commons;
import com.vworks.wms.common_lib.utils.MessageUtil;
import com.vworks.wms.common_lib.utils.StatusUtil;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;
import org.keycloak.representations.userprofile.config.UPConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final CaffeineCacheService cacheService;
    private final RestTemplate restTemplate;
    private final Keycloak keycloakClientForAdmin;
    private final CommonLibConfigProperties commonLibConfigProperties;

    private final Gson gson = new Gson();

    @Override
    public Integer handleToCreateUser(Map<Object, Object> properties) {
        log.info("{} handleToCreateUser with properties {}, realm {}", getClass().getSimpleName(), properties, commonLibConfigProperties.getKeycloak().getRealm());

        UsersResource userResource = keycloakClientForAdmin.realm(commonLibConfigProperties.getKeycloak().getRealm()).users();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(Boolean.FALSE);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(String.valueOf(properties.get(Commons.FIELD_PASSWORD)));

        UserRepresentation user = new UserRepresentation();
        user.setUsername(String.valueOf(properties.get(Commons.FIELD_USER_NAME)));
        user.setLastName(String.valueOf(properties.get(Commons.FIELD_FULL_NAME)));
        user.setEmail(String.valueOf(properties.get(Commons.FIELD_EMAIL)));
        user.setEnabled(true);
        user.setCredentials(List.of(credential));

        Map<String, List<String>> userAttributes = new HashMap<>();
        userAttributes.put(Commons.FIELD_USER_CODE, List.of(String.valueOf(properties.get(Commons.FIELD_USER_CODE))));
        userAttributes.put(Commons.FIELD_USER_ID, List.of(String.valueOf(properties.get(Commons.FIELD_USER_ID))));

        user.setAttributes(userAttributes);
        Response response = userResource.create(user);
        log.info("{} handleToCreateUser with response {}", getClass().getSimpleName(), response.getStatusInfo().toString());

        if (response.getStatus() == 201 && StringUtils.isNotBlank(commonLibConfigProperties.getTempKcGroupId())) { // Kiểm tra nếu tạo user thành công
            String userId = userResource.search(user.getUsername()).get(0).getId();
            //tạm thời hard code để add group mặc định cho user
            userResource.get(userId).joinGroup(commonLibConfigProperties.getTempKcGroupId());
        }
        return response.getStatus();
    }

    @Override
    public Integer handleToUpdateUser(Map<Object, Object> properties) {
        log.info("{} handleToUpdateUser with properties {}, realm {}", getClass().getSimpleName(), properties, commonLibConfigProperties.getKeycloak().getRealm());

        UsersResource usersResource = keycloakClientForAdmin.realm(commonLibConfigProperties.getKeycloak().getRealm()).users();
        Optional<UserRepresentation> existingUser = usersResource.search(String.valueOf(properties.get(Commons.FIELD_USER_NAME))).stream().findFirst();
        if (existingUser.isEmpty()) {
            return 404;
        }

        UserResource userResource = usersResource.get(existingUser.get().getId());
        UserRepresentation userRepresentation = userResource.toRepresentation();
        boolean isUpdate = false;
        if (Objects.nonNull(properties.get(Commons.FIELD_STATUS))) {
            Boolean isEnable = !StringUtils.equals(String.valueOf(properties.get(Commons.FIELD_STATUS)), StatusUtil.ACTIVE.name()) ? Boolean.FALSE : userRepresentation.isEnabled();
            userRepresentation.setEnabled(isEnable);
            isUpdate = true;
        }

        if (Objects.nonNull(properties.get(Commons.FIELD_PASSWORD))) {
            if (Objects.nonNull(userRepresentation.getCredentials())) {
                userRepresentation.getCredentials().clear();
            } else {
                userRepresentation.setCredentials(new ArrayList<>());
            }

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(String.valueOf(properties.get(Commons.FIELD_PASSWORD)));
            credential.setTemporary(false);

            userRepresentation.getCredentials().add(credential);
            isUpdate = true;
        }

        if (!isUpdate) {
            return 400;
        }
        userResource.update(userRepresentation);
        return 200;
    }

    @Override
    public AccessTokenResponse handleToFetchAccessToken(Map<Object, Object> properties) {
        try {
            log.info("{} handleToFetchAccessToken with realm {} with type {}", getClass().getSimpleName(), commonLibConfigProperties.getKeycloak().getRealm(), properties.get(OAuth2Constants.GRANT_TYPE));
            if (Objects.isNull(properties.get(Commons.FIELD_GRANT_TYPE))) {
                throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), String.format(MessageUtil.TEMPLATE_REQUEST_INVALID, Commons.FIELD_GRANT_TYPE));
            }

            String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token",
                    commonLibConfigProperties.getKeycloak().getServerUrl(), commonLibConfigProperties.getKeycloak().getRealm());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add(OAuth2Constants.CLIENT_ID, commonLibConfigProperties.getKeycloak().getClientId());
            body.add(OAuth2Constants.CLIENT_SECRET, commonLibConfigProperties.getKeycloak().getClientSecret());

            if (Objects.equals(OAuth2Constants.PASSWORD, properties.get(OAuth2Constants.GRANT_TYPE))) {
                body.add(OAuth2Constants.GRANT_TYPE, OAuth2Constants.PASSWORD);
                body.add(OAuth2Constants.USERNAME, String.valueOf(properties.get(Commons.FIELD_USER_NAME)));
                body.add(OAuth2Constants.PASSWORD, String.valueOf(properties.get(Commons.FIELD_PASSWORD)));
            }

            if (Objects.equals(OAuth2Constants.REFRESH_TOKEN, properties.get(OAuth2Constants.GRANT_TYPE))) {
                body.add(OAuth2Constants.GRANT_TYPE, OAuth2Constants.REFRESH_TOKEN);
                body.add(OAuth2Constants.REFRESH_TOKEN, String.valueOf(properties.get(Commons.FIELD_REFRESH_TOKEN)));
            }

            if (Objects.equals(OAuth2Constants.UMA_GRANT_TYPE, properties.get(OAuth2Constants.GRANT_TYPE))) {
                body.add(OAuth2Constants.GRANT_TYPE, OAuth2Constants.UMA_GRANT_TYPE);
                body.add(OAuth2Constants.AUDIENCE, Objects.nonNull(properties.get(OAuth2Constants.AUDIENCE)) ? String.valueOf(properties.get(OAuth2Constants.AUDIENCE)) : commonLibConfigProperties.getKeycloak().getClientId());
                headers.set(HttpHeaders.AUTHORIZATION, String.valueOf(properties.get(Commons.FIELD_AUTHORIZATION)));
            }

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(
                    tokenUrl,
                    requestEntity,
                    AccessTokenResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            log.error("{} handleToFetchAccessToken exception: {}", getClass().getSimpleName(), e);
            return null;
        }
    }

    @Override
    public Object handleToLogOut(Map<Object, Object> properties) {
        log.info("{} handleToLogOut with realm {}, properties {}", getClass().getSimpleName(), commonLibConfigProperties.getKeycloak().getRealm(), properties.size());
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/logout",
                commonLibConfigProperties.getKeycloak().getServerUrl(), commonLibConfigProperties.getKeycloak().getRealm());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(OAuth2Constants.CLIENT_ID, commonLibConfigProperties.getKeycloak().getClientId());
        body.add(OAuth2Constants.CLIENT_SECRET, commonLibConfigProperties.getKeycloak().getClientSecret());
        body.add(OAuth2Constants.REFRESH_TOKEN, String.valueOf(properties.get(Commons.FIELD_REFRESH_TOKEN)));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                tokenUrl,
                requestEntity,
                Object.class
        );
        return response.getBody();
    }

    @Override
    public Object updateUserAttributes(KeyCloakUpdateUserAttributeRequest request) {
        log.info("{} updateUserAttributes with request {}", getClass().getSimpleName(), request);
        UserProfileResource userProfileResource = keycloakClientForAdmin.realm(commonLibConfigProperties.getKeycloak().getRealm()).users().userProfile();
        UPConfig upConfig = userProfileResource.getConfiguration();
        request.getAttributes().forEach(upConfig::addOrReplaceAttribute);
        log.info("{} updateUserAttributes with upConfig {}", getClass().getSimpleName(), upConfig);
        userProfileResource.update(upConfig);
        return true;
    }

    @Override
    public List<RoleRepresentation> getClientRoles(String clientId) {
        try {
            log.info("{} getClientRoles for client = {}", getClass().getSimpleName(), clientId);

            ClientsResource clientsResource = keycloakClientForAdmin.realm(commonLibConfigProperties.getKeycloak().getRealm()).clients();

            if (StringUtils.isBlank(clientId)) {
                clientId = commonLibConfigProperties.getKeycloak().getClientId();
            }

            String clientUuid = clientsResource.findByClientId(clientId).get(0).getId();

            List<RoleRepresentation> roleRepresentations = clientsResource.get(clientUuid).roles().list(false);

            return roleRepresentations.stream().filter(e -> !StringUtils.equals(e.getName(), "uma_protection")).toList();
        } catch (Exception e) {
            log.error("{} getClientRoles exception = {}", getClass().getSimpleName(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public RoleRepresentation getDetailRole(String roleName) {
        try {
            log.info("{} getDetailRole for role = {}", getClass().getSimpleName(), roleName);
            String cacheRoleListKey = String.join("_", commonLibConfigProperties.getKeycloak().getClientId(), Commons.SUFFIX_ALL_ROLES_CACHE_KEY);
            String cacheName = Commons.PREFIX_KEYCLOAK_CACHE_NAME + commonLibConfigProperties.getKeycloak().getRealm();
            Object cacheRoleList = cacheService.get(cacheName, cacheRoleListKey);
            Map<String, RoleRepresentation> mapRoles = new HashMap<>();
            if (Objects.nonNull(cacheRoleList)) {
                log.info("{} getDetailRole in cache", getClass().getSimpleName());
                mapRoles = gson.fromJson(gson.toJson(cacheRoleList), new TypeToken<Map<String, RoleRepresentation>>() {
                }.getType());
            } else {
                List<RoleRepresentation> roleRepresentations = this.getClientRoles(commonLibConfigProperties.getKeycloak().getClientId());
                if (!CollectionUtils.isEmpty(roleRepresentations)) {
                    mapRoles = roleRepresentations.stream().collect(Collectors.toMap(RoleRepresentation::getName, Function.identity()));
                }
            }

            cacheService.put(cacheRoleListKey, mapRoles, cacheName);
            return mapRoles.get(roleName);
        } catch (Exception e) {
            log.error("{} getClientRoles exception = {}", getClass().getSimpleName(), e);
            return null;
        }
    }

    @Override
    public Object getRolePermissions(String clientId, String roleId) {
        log.info("{} getRolePermissions for client = {} and role = {}", getClass().getSimpleName(), clientId, roleId);
        ClientsResource clientsResource = keycloakClientForAdmin.realm(commonLibConfigProperties.getKeycloak().getRealm()).clients();
        if (StringUtils.isBlank(clientId)) {
            clientId = commonLibConfigProperties.getKeycloak().getClientId();
        }

        String clientUuid = clientsResource.findByClientId(clientId).get(0).getId();

        AuthorizationResource authorizationResource = clientsResource.get(clientUuid).authorization();

        String cacheName = Commons.PREFIX_KEYCLOAK_CACHE_NAME + commonLibConfigProperties.getKeycloak().getRealm();
        String cacheResourceKey = String.join("_", clientId, Commons.SUFFIX_ALL_RESOURCES_CACHE_KEY);
        Object clientResourcesCache = cacheService.get(cacheName, cacheResourceKey);
        List<ResourceRepresentation> cacheResources = gson.fromJson(gson.toJson(clientResourcesCache), new TypeToken<List<ResourceRepresentation>>() {
        }.getType());
        if (CollectionUtils.isEmpty(cacheResources)) {
            cacheResources = authorizationResource.resources().resources();
            cacheService.put(cacheResourceKey, cacheResources, cacheName);
        }

        PoliciesResource policiesResource = authorizationResource.policies();
        List<PolicyRepresentation> allPolicies = policiesResource.policies();

        String cacheAllPermissionsKey = String.join("_", clientId, Commons.SUFFIX_ALL_PERMISSIONS_CACHE_KEY);
        Object cacheAllPermissions = cacheService.get(cacheName, cacheAllPermissionsKey);
        List<PolicyRepresentation> allPermission = gson.fromJson(gson.toJson(cacheAllPermissions), new TypeToken<List<PolicyRepresentation>>() {
        }.getType());
        if (CollectionUtils.isEmpty(allPermission) && !CollectionUtils.isEmpty(allPolicies)) {
            allPermission = allPolicies.stream().filter(e -> StringUtils.equals(e.getType(), new ResourcePermissionRepresentation().getType())).toList();

            cacheService.put(cacheAllPermissionsKey, allPermission, cacheName);
        }

        String cachePolicyForRoleKey = String.join("_", clientId, Commons.SUFFIX_ALL_POLICIES_CACHE_KEY);
        Object cachePolicyForRoles = cacheService.get(cacheName, cachePolicyForRoleKey);
        List<PolicyRepresentation> policyForRoles = gson.fromJson(gson.toJson(cachePolicyForRoles), new TypeToken<List<PolicyRepresentation>>() {
        }.getType());
        if (CollectionUtils.isEmpty(policyForRoles) && !CollectionUtils.isEmpty(allPolicies)) {
            policyForRoles = allPolicies.stream().filter(e -> StringUtils.equals(e.getType(), new RolePolicyRepresentation().getType())).toList();

            cacheService.put(cachePolicyForRoleKey, policyForRoles, cacheName);
        }

        if (policyForRoles.isEmpty()) {
            return null;
        }

        PolicyRepresentation cachePolicyForRole = policyForRoles.stream()
                .filter(policy -> hasRoleInPolicy(policy, roleId))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(cachePolicyForRole)) {
            return null;
        }

        return authorizationResource.policies().policy(cachePolicyForRole.getId()).dependentPolicies();
    }

    private boolean hasRoleInPolicy(PolicyRepresentation policy, String roleId) {
        if (Objects.nonNull(policy.getConfig()) && StringUtils.isNotBlank(policy.getConfig().get("roles"))) {
            List<RolePolicyRepresentation.RoleDefinition> roleDefinitions = gson.fromJson(policy.getConfig().get("roles"), new TypeToken<List<RolePolicyRepresentation.RoleDefinition>>() {
            }.getType());
            return roleDefinitions.stream().anyMatch(role -> StringUtils.equals(role.getId(), roleId));
        }
        return false;
    }
}
