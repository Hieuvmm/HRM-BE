package com.vworks.hs.home_service.models.request.project;

import com.vworks.hs.home_service.utils.Commons;
import com.vworks.hs.home_service.utils.WarehouseServiceMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostApproveProjectReqBody {
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String projectCode;
    @NotBlank(message = WarehouseServiceMessages.REQUEST_INVALID)
    private String userId;
    @Pattern(regexp = Commons.STATUS_APPROVE_PROJECT_REGEXP, message = WarehouseServiceMessages.REQUEST_INVALID)
    private String status;
    private String note;
}
