package com.vworks.hs.admin_service.service;

import com.vworks.hs.admin_service.model.requestBody.PostDetailPermissionByRoleRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostSearchPermissionRequestBody;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.common_lib.model.idm.IdmAppPermission;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PermissionService {
    List<IdmAppPermission> postDetailPermissionByRole(PostDetailPermissionByRoleRequestBody requestBody, HttpServletRequest httpServletRequest);

    Page<IdmAppPermission> postSearchPermission(PostSearchPermissionRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
