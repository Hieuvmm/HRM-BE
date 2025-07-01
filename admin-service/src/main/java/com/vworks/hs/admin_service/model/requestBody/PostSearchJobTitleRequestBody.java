package com.vworks.hs.admin_service.model.requestBody;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostSearchJobTitleRequestBody extends PaginationRequest {
    private String status;
}
