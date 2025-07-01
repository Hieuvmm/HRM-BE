package com.vworks.hs.admin_service.model.requestBody;

import com.vworks.hs.common_lib.utils.MessageUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDeleteJobPositionRequestBody {
    @NotBlank(message = MessageUtil.TEMPLATE_REQUEST_INVALID)
    private String code;
}
