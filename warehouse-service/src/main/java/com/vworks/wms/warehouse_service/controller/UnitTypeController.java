package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemExceptionList;
import com.vworks.wms.warehouse_service.models.request.unitType.*;
import com.vworks.wms.warehouse_service.service.UnitTypeService;
import com.vworks.wms.warehouse_service.utils.ExceptionTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/unit-type/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UnitTypeController {
    private final UnitTypeService unitTypeService;

    @PostMapping("list")
    public BaseResponse<?> postListUnitType(@Valid @RequestBody PostListUnitTypeRequest requestBody) {

        return new BaseResponse<>(unitTypeService.postListUnitType(requestBody));
    }

    @PostMapping("create")
    public BaseResponse<?> postCreateUnitType(@Valid @RequestBody PostCreateUnitTypeRequest requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(unitTypeService.postCreateUnitType(requestBody, httpServletRequest));
    }

    @PostMapping("update")
    public BaseResponse<?> postUpdateUnitType(@Valid @RequestBody PostUpdateUnitTypeRequest requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(unitTypeService.postUpdateUnitType(requestBody, httpServletRequest));
    }

    @PostMapping("detail")
    public BaseResponse<?> postDetailUnitType(@Valid @RequestBody PostDetailUnitTypeRequest requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(unitTypeService.postDetailUnitType(requestBody, httpServletRequest));
    }

    @PostMapping("delete")
    public BaseResponse<?> postDeleteUnitType(@Valid @RequestBody PostDeleteUnitTypeRequest requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(unitTypeService.postDeleteUnitType(requestBody, httpServletRequest));
    }
}
