package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.warehouse_service.models.request.warehouse.*;
import com.vworks.wms.warehouse_service.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/ware-house/")
@CrossOrigin("*")
public class WareHouseController {
    @Autowired
    WareHouseService wareHouseService;

    @PostMapping("create")
    public BaseResponse createWareHouse(@RequestBody CreateWareHouseRequestBody requestBody) throws WarehouseMngtSystemException {
        return wareHouseService.createWareHouse(requestBody);
    }

    @PostMapping("search")
    public BaseResponse searchWareHouse(@RequestBody SearchWareHouseRequestBody requestBody) throws WarehouseMngtSystemException {
//        return wareHouseService.searchWareHouse(requestBody);
        return new BaseResponse(wareHouseService.searchWareHouseV2(requestBody));
    }

    @PostMapping("update")
    public BaseResponse updateWareHouse(@RequestBody UpdateWareHouseRequestBody requestBody) throws WarehouseMngtSystemException {
        return wareHouseService.updateWareHouse(requestBody);
    }

    @PostMapping("detail")
    public BaseResponse<?> detailWareHouse(@RequestBody PostGetWareHouseDetailRequestBody requestBody) throws WarehouseMngtSystemException {
        return new BaseResponse<>(wareHouseService.getDetailWareHouse(requestBody));
    }

    @PostMapping("material-detail")
    public BaseResponse<?> detailMaterialWareHouse(@RequestBody DetailWareHouseForExBillRequestBody requestBody) throws WarehouseMngtSystemException {
        return new BaseResponse<>(wareHouseService.detailWareHouse(requestBody));
    }
}
