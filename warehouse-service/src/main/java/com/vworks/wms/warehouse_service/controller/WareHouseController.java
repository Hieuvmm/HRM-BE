package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.warehouse_service.models.request.warehouse.*;
import com.vworks.wms.warehouse_service.service.WareHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/ware-house/infos")
@CrossOrigin("*")
@RequiredArgsConstructor
public class WareHouseController {
    private final WareHouseService wareHouseService;

    @PostMapping("/create")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'create', this)")
    public BaseResponse<?> createWareHouse(@RequestBody CreateWareHouseRequestBody requestBody) throws WarehouseMngtSystemException {
        return wareHouseService.createWareHouse(requestBody);
    }

    @PostMapping("/search")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'search', this)")
    public BaseResponse<?> searchWareHouse(@RequestBody SearchWareHouseRequestBody requestBody) throws WarehouseMngtSystemException {
        return new BaseResponse<>(wareHouseService.searchWareHouseV2(requestBody));
    }

    @PostMapping("/update")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'update', this)")
    public BaseResponse<?> updateWareHouse(@RequestBody UpdateWareHouseRequestBody requestBody) throws WarehouseMngtSystemException {
        return wareHouseService.updateWareHouse(requestBody);
    }

    @PostMapping("/detail")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'detail', this)")
    public BaseResponse<?> detailWareHouse(@RequestBody PostGetWareHouseDetailRequestBody requestBody) throws WarehouseMngtSystemException {
        return new BaseResponse<>(wareHouseService.getDetailWareHouse(requestBody));
    }

    @PostMapping("/material-detail")
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'material-detail', this)")
    public BaseResponse<?> detailMaterialWareHouse(@RequestBody DetailWareHouseForExBillRequestBody requestBody) throws WarehouseMngtSystemException {
        return new BaseResponse<>(wareHouseService.detailWareHouse(requestBody));
    }
}
