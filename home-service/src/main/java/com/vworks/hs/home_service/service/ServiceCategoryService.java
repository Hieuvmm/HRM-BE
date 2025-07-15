package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.serviceCategory.AddServiceCategoryRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.DeleteServiceCategoryRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.UpdateServiceCategoryRequestBody;

public interface ServiceCategoryService {
    BaseResponse<?> add(AddServiceCategoryRequestBody request);
    BaseResponse<?> update(UpdateServiceCategoryRequestBody request) throws WarehouseMngtSystemException;
    BaseResponse<?> delete(DeleteServiceCategoryRequestBody request) throws WarehouseMngtSystemException;
    BaseResponse<?> list();
    BaseResponse<?> getById(Long id);
}
