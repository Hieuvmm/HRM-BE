//package com.vworks.wms.admin_service.controller;
//
//import com.vworks.wms.admin_service.model.requestBody.PostCreateRoleRequestBody;
//import com.vworks.wms.admin_service.model.requestBody.PostSearchRoleRequestBody;
//import com.vworks.wms.admin_service.model.requestBody.PostUpdateRoleRequestBody;
//import com.vworks.wms.admin_service.service.RoleService;
//import com.vworks.wms.admin_service.utils.ASExceptionTemplate;
//import com.vworks.wms.common_lib.base.BaseResponse;
//import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
//import com.vworks.wms.common_lib.exception.WarehouseMngtSystemExceptionList;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("${as-properties.api-prefix}/role")
//@CrossOrigin("*")
//public class RoleController {
//    private final RoleService roleService;
//
//    @PostMapping("/create")
//    public BaseResponse<Object> postCreateRole(@RequestBody @Valid PostCreateRoleRequestBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException, WarehouseMngtSystemExceptionList {
//        if (bindingResult.hasErrors()) {
//            throw new WarehouseMngtSystemExceptionList(ASExceptionTemplate.ERROR_REQUEST_LIST.getCode(), ASExceptionTemplate.ERROR_REQUEST_LIST.getMessage(), bindingResult.getAllErrors());
//        }
//        return new BaseResponse<>(roleService.postCreateRole(requestBody, httpServletRequest));
//    }
//
//    @PostMapping("/update")
//    public BaseResponse<Object> postUpdateRole(@RequestBody @Valid PostUpdateRoleRequestBody requestBody, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException, WarehouseMngtSystemExceptionList {
//        if (bindingResult.hasErrors()) {
//            throw new WarehouseMngtSystemExceptionList(ASExceptionTemplate.ERROR_REQUEST_LIST.getCode(), ASExceptionTemplate.ERROR_REQUEST_LIST.getMessage(), bindingResult.getAllErrors());
//        }
//        return new BaseResponse<>(roleService.postUpdateRole(requestBody, httpServletRequest));
//    }
//
//    @PostMapping("/search")
//    public BaseResponse<Object> postSearchRole(@RequestBody PostSearchRoleRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
//        return new BaseResponse<>(roleService.postSearchRole(requestBody, httpServletRequest));
//    }
//}
