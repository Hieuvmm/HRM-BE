package com.vworks.hs.home_service.models.request.parameter;

import com.vworks.hs.home_service.utils.Commons;
import com.vworks.hs.home_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateOrUpdateParameterReqBody {
    private String code;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String name;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String parameterTypeCode;
    @Pattern(regexp = Commons.STATUS_REGEXP, message = WarehouseServiceMessages.FIELD_FORMAT)
    private String status;
    private String description;
}
