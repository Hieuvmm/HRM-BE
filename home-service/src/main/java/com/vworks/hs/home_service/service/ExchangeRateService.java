package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.exchangeRate.*;
import com.vworks.hs.home_service.models.response.exchangeRate.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface ExchangeRateService {
    PostCreateExchangeRateResponse postCreateExchangeRate(PostCreateExchangeRateRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostUpdateExchangeRateResponse postUpdateExchangeRate(PostUpdateExchangeRateRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Page<PostListExchangeRateResponse> postListExchangeRate(PostListExchangeRateRequest requestBody);

    PostDetailExchangeRateResponse postDetailExchangeRate(PostDetailExchangeRateRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostDeleteExchangeRateResponse postDeleteExchangeRate(PostDeleteExchangeRateRequest requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
