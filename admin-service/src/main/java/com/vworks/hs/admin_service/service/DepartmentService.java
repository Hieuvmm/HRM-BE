package com.vworks.hs.admin_service.service;

import com.vworks.hs.admin_service.entity.DepartmentEntity;
import com.vworks.hs.admin_service.model.requestBody.PostCreateDepartmentRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostDeleteDepartmentRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostSearchDepartmentRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostUpdateDepartmentRequestBody;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Object postCreateDepartment(PostCreateDepartmentRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Object postUpdateDepartment(PostUpdateDepartmentRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Page<DepartmentEntity> postSearchDepartment(PostSearchDepartmentRequestBody requestBody, HttpServletRequest httpServletRequest);

    Object postDeleteDepartment(PostDeleteDepartmentRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
