package com.vworks.wms.common_lib.service;

import com.vworks.wms.common_lib.model.request.KeyCloakUpdateUserAttributeRequest;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface KeycloakService {
    Integer handleToCreateUser(Map<Object, Object> additionProperties);

    Integer handleToUpdateUser(Map<Object, Object> additionProperties);

    AccessTokenResponse handleToFetchAccessToken(Map<Object, Object> additionProperties);

    Object handleToLogOut(Map<Object, Object> additionProperties);

    Object updateUserAttributes(KeyCloakUpdateUserAttributeRequest request);
    List<RoleRepresentation> getClientRoles(String clientId);
    RoleRepresentation getDetailRole(String roleName);
    Object getRolePermissions(String clientId, String roleId);
}
