package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.home_service.models.request.workTicketMaterial.AddWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.models.request.workTicketMaterial.DeleteWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.models.request.workTicketMaterial.UpdateWorkTicketMaterialRequestBody;
import org.springframework.stereotype.Service;



public interface WorkTicketMaterialService {
    BaseResponse<?> add(AddWorkTicketMaterialRequestBody request);
    BaseResponse<?> update(UpdateWorkTicketMaterialRequestBody request);
    BaseResponse<?> delete(DeleteWorkTicketMaterialRequestBody request);
    BaseResponse<?> list(String workTicketId);
}
