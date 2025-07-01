package com.vworks.hs.home_service.models.request.exchangeRate;

import com.vworks.hs.home_service.utils.Commons;
import com.vworks.hs.home_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseExchangeRateRequest {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String code;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String name;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String exchangeType;
    private BigDecimal value;
    @Pattern(regexp = Commons.STATUS_REGEXP, message = WarehouseServiceMessages.FIELD_FORMAT)
    private String status;
}
