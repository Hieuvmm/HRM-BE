package com.vworks.hs.admin_service.model.requestBody;

import com.vworks.hs.common_lib.utils.MessageUtil;
import com.vworks.hs.common_lib.utils.RegexUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostUpdateDepartmentRequestBody {
    @NotBlank(message = MessageUtil.TEMPLATE_REQUEST_INVALID)
    private String code;
    @NotBlank(message = MessageUtil.TEMPLATE_REQUEST_INVALID)
    private String name;
    @NotBlank(message = MessageUtil.TEMPLATE_REQUEST_INVALID)
    @Pattern(regexp = RegexUtil.REGEX_STATUS_A_IN, message = MessageUtil.FIELD_INVALID)
    private String status;
    private String description;
}
