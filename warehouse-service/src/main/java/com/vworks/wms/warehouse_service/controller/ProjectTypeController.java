package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemExceptionList;
import com.vworks.wms.warehouse_service.models.request.projectType.PostDetailProjectTypeReqBody;
import com.vworks.wms.warehouse_service.models.request.projectType.PostListProjectTypeReqBody;
import com.vworks.wms.warehouse_service.models.request.projectType.PostUpdateProjectTypeReqBody;
import com.vworks.wms.warehouse_service.service.ProjectTypeService;
import com.vworks.wms.warehouse_service.utils.ExceptionTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/project-type/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectTypeController {
    private final ProjectTypeService projectTypeService;

    @PostMapping("list")
    public BaseResponse<?> postListProjectType(@Valid @RequestBody PostListProjectTypeReqBody requestBody) {
        return new BaseResponse<>(projectTypeService.postListProjectType(requestBody));
    }

    @PostMapping("create")
    public BaseResponse<?> postCreateProjectType(@Valid @RequestBody PostUpdateProjectTypeReqBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException, WarehouseMngtSystemExceptionList {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectTypeService.postCreateProjectType(requestBody, httpServletRequest));
    }

    @PostMapping("update")
    public BaseResponse<?> postUpdateProjectType(@Valid @RequestBody PostUpdateProjectTypeReqBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectTypeService.postUpdateProjectType(requestBody, httpServletRequest));
    }

    @PostMapping("detail")
    public BaseResponse<?> postDetailProjectType(@Valid @RequestBody PostDetailProjectTypeReqBody requestBody, BindingResult bindingResult) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectTypeService.postDetailProjectType(requestBody));
    }

    @PostMapping("delete")
    public BaseResponse<?> postDeleteProjectType(@Valid @RequestBody PostDetailProjectTypeReqBody requestBody, BindingResult bindingResult) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectTypeService.postDeleteProjectType(requestBody));
    }
}
