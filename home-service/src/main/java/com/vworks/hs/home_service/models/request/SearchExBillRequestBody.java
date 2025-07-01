package com.vworks.hs.home_service.models.request;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchExBillRequestBody extends PaginationRequest {
    private String billCode;
    private String status;
    private String pageSize;
    private String pageNumber;
}
