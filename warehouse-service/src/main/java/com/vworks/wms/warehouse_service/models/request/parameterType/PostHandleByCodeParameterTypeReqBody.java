package com.vworks.wms.warehouse_service.models.request.parameterType;

import com.vworks.wms.warehouse_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostHandleByCodeParameterTypeReqBody {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String code;
}
