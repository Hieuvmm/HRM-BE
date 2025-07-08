package com.vworks.hs.home_service.models.request.exportRequest;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchExportRequestBody extends PaginationRequest {
    private String code;
    private String status;
    private Long requesterId;
    private String pageSize;
    private String pageNumber;
}
