package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.provider.*;
import com.vworks.hs.home_service.models.response.provider.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface ProviderService {
    PostCreateProviderResponse postCreateProvider(PostCreateProviderRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostUpdateProviderResponse postUpdateProvider(PostUpdateProviderRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Page<PostListProviderResponse> postListProvider(PostListProviderRequest requestBody);

    PostDetailProviderResponse postDetailProvider(PostDetailProviderRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostDeleteProviderResponse postDeleteProvider(PostDeleteProviderRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
