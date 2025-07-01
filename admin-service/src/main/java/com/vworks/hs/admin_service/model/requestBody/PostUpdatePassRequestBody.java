package com.vworks.hs.admin_service.model.requestBody;

import com.vworks.hs.common_lib.utils.MessageUtil;
import com.vworks.hs.common_lib.utils.RegexUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostUpdatePassRequestBody {
    @NotBlank(message = MessageUtil.TEMPLATE_REQUEST_INVALID)
    @Pattern(regexp = RegexUtil.REGEX_ACTION_UPDATE_PASS, message = MessageUtil.FIELD_INVALID)
    private String action;
    @NotBlank(message = MessageUtil.TEMPLATE_REQUEST_INVALID)
    private String userCode;
    private String oldPass;
    private String newPass;
}
