package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemExceptionList;
import com.vworks.wms.warehouse_service.models.request.projectCategory.PostDetailProjectCategoryReqBody;
import com.vworks.wms.warehouse_service.models.request.projectCategory.PostListProjectCategoryReqBody;
import com.vworks.wms.warehouse_service.models.request.projectCategory.PostUpdateProjectCategoryReqBody;
import com.vworks.wms.warehouse_service.service.ProjectCategoryService;
import com.vworks.wms.warehouse_service.utils.ExceptionTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/project-category/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectCategoryController {
    private final ProjectCategoryService projectCategoryService;

    @PostMapping("list")
    public BaseResponse<?> postListProjectCategory(@Valid @RequestBody PostListProjectCategoryReqBody requestBody) {

        return new BaseResponse<>(projectCategoryService.postListProjectCategory(requestBody));
    }

    @PostMapping("create")
    public BaseResponse<?> postCreateProjectCategory(@Valid @RequestBody PostUpdateProjectCategoryReqBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException, WarehouseMngtSystemExceptionList {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectCategoryService.postCreateProjectCategory(requestBody, httpServletRequest));
    }

    @PostMapping("update")
    public BaseResponse<?> postUpdateProjectCategory(@Valid @RequestBody PostUpdateProjectCategoryReqBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectCategoryService.postUpdateProjectCategory(requestBody, httpServletRequest));
    }

    @PostMapping("detail")
    public BaseResponse<?> postDetailProjectCategory(@Valid @RequestBody PostDetailProjectCategoryReqBody requestBody, BindingResult bindingResult) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectCategoryService.postDetailProjectCategory(requestBody));
    }

    @PostMapping("delete")
    public BaseResponse<?> postDeleteProjectCategory(@Valid @RequestBody PostDetailProjectCategoryReqBody requestBody, BindingResult bindingResult) throws WarehouseMngtSystemExceptionList, WarehouseMngtSystemException {
        if (bindingResult.hasErrors()) {
            throw new WarehouseMngtSystemExceptionList(ExceptionTemplate.BAD_REQUEST.getCode(), ExceptionTemplate.BAD_REQUEST.getMessage(), bindingResult.getAllErrors());
        }
        return new BaseResponse<>(projectCategoryService.postDeleteProjectCategory(requestBody));
    }
}
