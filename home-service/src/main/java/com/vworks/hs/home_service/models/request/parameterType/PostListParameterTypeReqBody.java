package com.vworks.hs.home_service.models.request.parameterType;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListParameterTypeReqBody extends PaginationRequest {
    private String status;
}
