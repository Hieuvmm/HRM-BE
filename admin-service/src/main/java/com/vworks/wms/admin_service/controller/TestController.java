package com.vworks.wms.admin_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${as-properties.api-prefix}/test")
@CrossOrigin("*")
public class TestController {
    @PostMapping("/info")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'INFO', this)")
    public BaseResponse<Object> postTestInfo(HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return new BaseResponse<>(getClass().getSimpleName() + " postTestInfo => OK!");
    }
}
