package com.vworks.hs.home_service.models.request.material;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListMaterialRequest extends PaginationRequest {
    private String status;
    private String whCode;
}
