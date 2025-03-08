package com.vworks.wms.warehouse_service.models.request.parameter;

import com.vworks.wms.common_lib.base.PaginationRequest;
import com.vworks.wms.warehouse_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListParameterReqBody extends PaginationRequest {
    private String status;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String prTypeCode;
}
