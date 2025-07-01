package com.vworks.hs.home_service.models.request.object;

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
public class PostDetailObjectReqBody {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String code;
}
