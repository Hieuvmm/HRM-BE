package com.vworks.hs.home_service.models.request.order;

import com.vworks.hs.home_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUpdateStatusOrderRequestBody {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String code;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String status;
}
