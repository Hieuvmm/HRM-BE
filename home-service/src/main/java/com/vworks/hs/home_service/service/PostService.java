package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.post.*;
import jakarta.servlet.http.HttpServletRequest;

public interface PostService {
    BaseResponse<?> create(CreatePostRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> update(UpdatePostRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> delete(DeletePostRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
    BaseResponse<?> get(GetPostRequest request) throws WarehouseMngtSystemException;
    BaseResponse<?> list(ListPostRequest request) throws WarehouseMngtSystemException;
}
