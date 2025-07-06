package com.vworks.hs.admin_service.model.requestBody;

import com.vworks.hs.admin_service.utils.ASMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginRequestBody {
    @NotBlank(message = ASMessages.TEMPLATE_REQUEST_INVALID)
    private String username;
    @NotBlank(message = ASMessages.TEMPLATE_REQUEST_INVALID)
    private String password;
}
