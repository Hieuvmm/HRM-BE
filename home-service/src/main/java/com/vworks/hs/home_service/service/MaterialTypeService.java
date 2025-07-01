package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.materialType.*;
import com.vworks.hs.home_service.models.response.materialType.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface MaterialTypeService {
    PostCreateMaterialTypeResponse postCreateMaterialType(PostCreateMaterialTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostUpdateMaterialTypeResponse postUpdateMaterialType(PostUpdateMaterialTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Page<PostListMaterialTypeResponse> postListMaterialType(PostListMaterialTypeRequest requestBody);

    PostDetailMaterialTypeResponse postDetailMaterialType(PostDetailMaterialTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostDeleteMaterialTypeResponse postDeleteMaterialType(PostDeleteMaterialTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
