package com.vworks.wms.common_lib.service.impl;

import com.vworks.wms.common_lib.config.CommonLibConfigProperties;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
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
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final RestTemplate restTemplate;
    private final Keycloak keycloakClientForAdmin;
    private final CommonLibConfigProperties commonLibConfigProperties;

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
            log.info("{} handleToFetchAccessToken with realm {}, properties {}", getClass().getSimpleName(), commonLibConfigProperties.getKeycloak().getRealm(), properties);
            if (Objects.isNull(properties.get(Commons.FIELD_GRANT_TYPE))) {
                throw new WarehouseMngtSystemException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), String.format(MessageUtil.TEMPLATE_REQUEST_INVALID, Commons.FIELD_GRANT_TYPE));
            }

            String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token",
                    commonLibConfigProperties.getKeycloak().getServerUrl(), commonLibConfigProperties.getKeycloak().getRealm());

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", commonLibConfigProperties.getKeycloak().getClientId());
            body.add("client_secret", commonLibConfigProperties.getKeycloak().getClientSecret());

            if (Objects.equals(OAuth2Constants.PASSWORD, properties.get(Commons.FIELD_GRANT_TYPE))) {
                body.add("grant_type", OAuth2Constants.PASSWORD);
                body.add("username", String.valueOf(properties.get(Commons.FIELD_USER_NAME)));
                body.add("password", String.valueOf(properties.get(Commons.FIELD_PASSWORD)));
            }

            if (Objects.equals(OAuth2Constants.REFRESH_TOKEN, properties.get(Commons.FIELD_GRANT_TYPE))) {
                body.add("grant_type", OAuth2Constants.REFRESH_TOKEN);
                body.add("refresh_token", String.valueOf(properties.get(Commons.FIELD_REFRESH_TOKEN)));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

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
        log.info("{} handleToFetchAccessToken with realm {}, properties {}", getClass().getSimpleName(), commonLibConfigProperties.getKeycloak().getRealm(), properties);
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/logout",
                commonLibConfigProperties.getKeycloak().getServerUrl(), commonLibConfigProperties.getKeycloak().getRealm());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", commonLibConfigProperties.getKeycloak().getClientId());
        body.add("client_secret", commonLibConfigProperties.getKeycloak().getClientSecret());
        body.add("refresh_token", String.valueOf(properties.get(Commons.FIELD_REFRESH_TOKEN)));
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
}
