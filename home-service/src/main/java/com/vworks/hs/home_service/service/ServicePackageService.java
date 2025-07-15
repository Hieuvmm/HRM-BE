package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.serviceCategory.AddServicePackageRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.DeleteServicePackageRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.UpdateServicePackageRequestBody;

public interface ServicePackageService {
    BaseResponse<?> add(AddServicePackageRequestBody request);
    BaseResponse<?> update(UpdateServicePackageRequestBody request) throws WarehouseMngtSystemException;
    BaseResponse<?> delete(DeleteServicePackageRequestBody request) throws WarehouseMngtSystemException;
    BaseResponse<?> list();
    BaseResponse<?> getByCategoryId(Long categoryId);
}
