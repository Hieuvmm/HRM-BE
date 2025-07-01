package com.vworks.hs.home_service.models.request.order;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostListOrderReqBody extends PaginationRequest {
    private String status;
}
