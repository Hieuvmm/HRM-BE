package com.vworks.hs.home_service.models.request.importBill;

import com.vworks.hs.home_service.utils.Commons;
import com.vworks.hs.home_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostChangeStatusImportBillRequestBody {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String id;
    @Pattern(regexp = Commons.STATUS_BILL_REGEXP, message = WarehouseServiceMessages.FIELD_FORMAT)
    private String status;
}
