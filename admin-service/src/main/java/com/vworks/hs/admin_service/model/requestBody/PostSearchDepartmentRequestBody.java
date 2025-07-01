package com.vworks.hs.admin_service.model.requestBody;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostSearchDepartmentRequestBody extends PaginationRequest {
    private String status;
}
