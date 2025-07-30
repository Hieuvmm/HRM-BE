package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.hashtag.CreateHashtagRequest;
import com.vworks.hs.home_service.models.request.hashtag.DeleteHashtagRequest;
import com.vworks.hs.home_service.models.request.hashtag.UpdateHashtagRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface HashtagService {
    BaseResponse<?> create(CreateHashtagRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> update(UpdateHashtagRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> delete(DeleteHashtagRequest request) throws WarehouseMngtSystemException;
    BaseResponse<?> list() throws WarehouseMngtSystemException;
    BaseResponse<?> getById(Long id) throws Exception;
}