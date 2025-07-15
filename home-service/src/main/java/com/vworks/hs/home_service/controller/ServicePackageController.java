package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.request.serviceCategory.AddServicePackageRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.DeleteServicePackageRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.UpdateServicePackageRequestBody;
import com.vworks.hs.home_service.service.ServicePackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_SERVICE_PACKAGE)
public class ServicePackageController {
    private final ServicePackageService service;

    @PostMapping("/add")
    public BaseResponse<?> add(@RequestBody AddServicePackageRequestBody request) {
        return service.add(request);
    }

    @PostMapping("/update")
    public BaseResponse<?> update(@RequestBody UpdateServicePackageRequestBody request) throws WarehouseMngtSystemException {
        return service.update(request);
    }

    @PostMapping("/delete")
    public BaseResponse<?> delete(@RequestBody DeleteServicePackageRequestBody request) throws WarehouseMngtSystemException {
        return service.delete(request);
    }

    @GetMapping("/list")
    public BaseResponse<?> list() {
        return service.list();
    }

    @GetMapping("/by-category")
    public BaseResponse<?> getByCategory(@RequestParam Long categoryId) {
        return service.getByCategoryId(categoryId);
    }
}
