package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.request.serviceCategory.AddServiceCategoryRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.DeleteServiceCategoryRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.UpdateServiceCategoryRequestBody;
import com.vworks.hs.home_service.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_SERVICE_CATEGORY)
public class ServiceCategoryController {
    private final ServiceCategoryService service;

    @PostMapping("/add")
    public BaseResponse<?> add(@RequestBody AddServiceCategoryRequestBody request) {
        return service.add(request);
    }

    @PostMapping("/update")
    public BaseResponse<?> update(@RequestBody UpdateServiceCategoryRequestBody request) throws WarehouseMngtSystemException {
        return service.update(request);
    }

    @PostMapping("/delete")
    public BaseResponse<?> delete(@RequestBody DeleteServiceCategoryRequestBody request) throws WarehouseMngtSystemException {
        return service.delete(request);
    }

    @GetMapping("/list")
    public BaseResponse<?> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public BaseResponse<?> detail(@PathVariable Long id) {
        return service.getById(id);
    }
}
