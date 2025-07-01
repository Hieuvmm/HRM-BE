package com.vworks.hs.admin_service.model.requestBody;

import com.vworks.hs.admin_service.utils.ASMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRefreshTokenRequestBody {
    private String userCode;
    @NotBlank(message = ASMessages.TEMPLATE_REQUEST_INVALID)
    private String refreshToken;
}
