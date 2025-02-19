package com.vworks.wms.warehouse_service.models.request.material;

import com.vworks.wms.warehouse_service.models.request.DetailWholesalePrice;
import com.vworks.wms.warehouse_service.utils.Commons;
import com.vworks.wms.warehouse_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseMaterialRequest {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String code;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String name;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String materialTypeCode;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String unitTypeCode;
    @Min(value = 1, message = WarehouseServiceMessages.REQUEST_INVALID)
    private BigDecimal listPrice;
    private String parameter;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String origin;
    @Min(value = 1, message = WarehouseServiceMessages.REQUEST_INVALID)
    private Long minInventory;
    @NotNull(message = WarehouseServiceMessages.REQUEST_INVALID)
    @Size(min = 1, message = WarehouseServiceMessages.REQUEST_INVALID)
    private List<DetailWholesalePrice> detailWholesalePrice;
    private MultipartFile image;
    private String description;
    @Pattern(regexp = Commons.STATUS_REGEXP, message = WarehouseServiceMessages.FIELD_FORMAT)
    private String status;
}
