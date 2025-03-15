package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemExceptionList;
import com.vworks.wms.warehouse_service.models.request.material.*;
import com.vworks.wms.warehouse_service.service.MaterialService;
import com.vworks.wms.warehouse_service.utils.ExceptionTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/material/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MaterialController {
    private final MaterialService materialService;

    @PostMapping("list")
    public BaseResponse<?> postListMaterial(@Valid @RequestBody PostListMaterialRequest requestBody) {

        return new BaseResponse<>(materialService.postListMaterial(requestBody));
    }

    @PostMapping("create")
    public BaseResponse<?> postCreateMaterial(@Valid PostCreateMaterialRequest requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(materialService.postCreateMaterial(requestBody, httpServletRequest));
    }

    @PostMapping("update")
    public BaseResponse<?> postUpdateMaterialType(@Valid PostUpdateMaterialRequest requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(materialService.postUpdateMaterial(requestBody, httpServletRequest));
    }

    @PostMapping("detail")
    public BaseResponse<?> postDetailMaterial(@Valid @RequestBody PostDetailMaterialRequest requestBody, BindingResult bindingResult) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(materialService.postDetailMaterial(requestBody));
    }

    @PostMapping("list-detail")
    public BaseResponse<?> postDetailMaterial(@Valid @RequestBody PostDetailMaterialListRequestBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(materialService.postDetailMaterialList(requestBody, httpServletRequest));
    }

    @PostMapping("delete")
    public BaseResponse<?> postDeleteMaterial(@Valid @RequestBody PostDeleteMaterialRequest requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(materialService.postDeleteMaterial(requestBody, httpServletRequest));

    }

    @PostMapping("fetch-by-condition")
    public BaseResponse<?> postFetchCondition(@Valid @RequestBody PostGetByConditionRequestBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(materialService.postGetByCondition(requestBody, httpServletRequest));

    }
}
