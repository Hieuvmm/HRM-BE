package com.vworks.hs.home_service.models.request.exchangeRate;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListExchangeRateRequest extends PaginationRequest {
    private String status;
}
