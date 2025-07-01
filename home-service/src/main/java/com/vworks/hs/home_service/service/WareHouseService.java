package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.warehouse.*;

import com.vworks.hs.home_service.models.response.SearchWareHouseResponseBody;
import com.vworks.hs.home_service.models.response.warehouse.DetailWareHouseForExBillResponseBody;
import com.vworks.hs.home_service.models.response.warehouse.PostGetDetailWareHouseResponseBody;

public interface WareHouseService {
    BaseResponse<?> createWareHouse(CreateWareHouseRequestBody requestBody) throws WarehouseMngtSystemException;

    BaseResponse<?> searchWareHouse(SearchWareHouseRequestBody requestBody) throws WarehouseMngtSystemException;

    BaseResponse<?> updateWareHouse(UpdateWareHouseRequestBody requestBody) throws WarehouseMngtSystemException;

    PostGetDetailWareHouseResponseBody getDetailWareHouse(PostGetWareHouseDetailRequestBody requestBody) throws WarehouseMngtSystemException;

    SearchWareHouseResponseBody searchWareHouseV2(SearchWareHouseRequestBody requestBody) throws WarehouseMngtSystemException;

    DetailWareHouseForExBillResponseBody detailWareHouse(DetailWareHouseForExBillRequestBody requestBody) throws WarehouseMngtSystemException;
}
