package com.vworks.hs.home_service.models.request.parameterType;

import com.vworks.hs.home_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostHandleByCodeParameterTypeReqBody {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String code;
}
