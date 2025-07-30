package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.contens.*;
import jakarta.servlet.http.HttpServletRequest;

public interface PostHashtagService {
    BaseResponse<?> add(PostHashtagCreateRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> addMultiple(PostHashtagBatchRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> getByPostId(PostHashtagGetRequest request) throws WarehouseMngtSystemException;
    BaseResponse<?> delete(PostHashtagDeleteRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> deleteByPostId(PostHashtagDeleteByPostRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
