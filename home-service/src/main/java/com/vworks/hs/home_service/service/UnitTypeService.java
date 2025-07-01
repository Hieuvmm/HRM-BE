package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.unitType.*;
import com.vworks.hs.home_service.models.response.unitType.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface UnitTypeService {
    PostCreateUnitTypeResponse postCreateUnitType(PostCreateUnitTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostUpdateUnitTypeResponse postUpdateUnitType(PostUpdateUnitTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Page<PostListUnitTypeResponse> postListUnitType(PostListUnitTypeRequest requestBody);

    PostDetailUnitTypeResponse postDetailUnitType(PostDetailUnitTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostDeleteUnitTypeResponse postDeleteUnitType(PostDeleteUnitTypeRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
