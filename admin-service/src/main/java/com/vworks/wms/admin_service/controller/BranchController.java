package com.vworks.wms.admin_service.controller;

import com.vworks.wms.admin_service.model.requestBody.PostCreateBranchRequestBody;
import com.vworks.wms.admin_service.model.requestBody.PostDeleteBranchRequestBody;
import com.vworks.wms.admin_service.model.requestBody.PostSearchBranchRequestBody;
import com.vworks.wms.admin_service.model.requestBody.PostUpdateBranchRequestBody;
import com.vworks.wms.admin_service.service.BranchService;
import com.vworks.wms.admin_service.utils.ASExceptionTemplate;
import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemExceptionList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${as-properties.api-prefix}/branch")
@CrossOrigin("*")
public class BranchController {
    private final BranchService branchService;

    @PostMapping("/create")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'CREATE', this)")
    public BaseResponse<Object> postCreateBranch(@RequestBody @Valid PostCreateBranchRequestBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException, WarehouseMngtSystemExceptionList {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ASExceptionTemplate.ERROR_REQUEST_LIST.getCode(), ASExceptionTemplate.ERROR_REQUEST_LIST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(branchService.postCreateBranch(requestBody, httpServletRequest));
    }

    @PostMapping("/update")
    public BaseResponse<Object> postUpdateBranch(@RequestBody @Valid PostUpdateBranchRequestBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException, WarehouseMngtSystemExceptionList {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ASExceptionTemplate.ERROR_REQUEST_LIST.getCode(), ASExceptionTemplate.ERROR_REQUEST_LIST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(branchService.postUpdateBranch(requestBody, httpServletRequest));
    }

    @PostMapping("/search")
    public BaseResponse<?> postSearchBranch(@RequestBody PostSearchBranchRequestBody requestBody, HttpServletRequest httpServletRequest) {
        return new BaseResponse<>(branchService.postSearchBranch(requestBody, httpServletRequest));
    }

    @PostMapping("/delete")
    public BaseResponse<Object> postDeleteBranch(@RequestBody @Valid PostDeleteBranchRequestBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException, WarehouseMngtSystemExceptionList {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ASExceptionTemplate.ERROR_REQUEST_LIST.getCode(), ASExceptionTemplate.ERROR_REQUEST_LIST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(branchService.postDeleteBranch(requestBody, httpServletRequest));
    }
}
