package com.vworks.hs.home_service.models.request.importBill;

import com.vworks.hs.home_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostApproveImportBillRequestBody {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String importBillCode;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String userId;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String status;
    private String note;
}
