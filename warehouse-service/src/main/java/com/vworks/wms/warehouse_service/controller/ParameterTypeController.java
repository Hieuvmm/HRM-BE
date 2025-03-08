package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemExceptionList;
import com.vworks.wms.warehouse_service.models.request.parameterType.PostCreateOrUpdateParameterTypeReqBody;
import com.vworks.wms.warehouse_service.models.request.parameterType.PostHandleByCodeParameterTypeReqBody;
import com.vworks.wms.warehouse_service.models.request.parameterType.PostListParameterTypeReqBody;
import com.vworks.wms.warehouse_service.service.ParameterTypeService;
import com.vworks.wms.warehouse_service.utils.ExceptionTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/parameter-type/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ParameterTypeController {
    private final ParameterTypeService parameterTypeService;

    @PostMapping("list")
    public BaseResponse<?> postListParameterType(@Valid @RequestBody PostListParameterTypeReqBody requestBody) {

        return new BaseResponse<>(parameterTypeService.postListParameterType(requestBody));
    }

    @PostMapping("create")
    public BaseResponse<?> postCreateParameterType(@Valid @RequestBody PostCreateOrUpdateParameterTypeReqBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(parameterTypeService.postCreateParameterType(requestBody, httpServletRequest));
    }

    @PostMapping("update")
    public BaseResponse<?> postUpdateParameterType(@Valid @RequestBody PostCreateOrUpdateParameterTypeReqBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(parameterTypeService.postUpdateParameterType(requestBody, httpServletRequest));
    }

    @PostMapping("detail")
    public BaseResponse<?> postDetailParameterType(@Valid @RequestBody PostHandleByCodeParameterTypeReqBody requestBody, BindingResult bindingResult) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(parameterTypeService.postDetailParameterType(requestBody));
    }

    @PostMapping("delete")
    public BaseResponse<?> postDeleteParameterType(@Valid @RequestBody PostHandleByCodeParameterTypeReqBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(parameterTypeService.postDeleteParameterType(requestBody, httpServletRequest));

    }
}
