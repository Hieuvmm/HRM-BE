package com.vworks.wms.common_lib.service;

import org.keycloak.representations.AccessTokenResponse;

import java.util.Map;

public interface KeycloakService {
    Integer handleToCreateUser(Map<Object, Object> additionProperties);

    Integer handleToUpdateUser(Map<Object, Object> additionProperties);

    AccessTokenResponse handleToFetchAccessToken(Map<Object, Object> additionProperties);

    Object handleToLogOut(Map<Object, Object> additionProperties);
}
