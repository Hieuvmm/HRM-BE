package com.vworks.hs.home_service.models.request.projectCategory;

import com.vworks.hs.common_lib.base.PaginationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostListProjectCategoryReqBody extends PaginationRequest {
    private String status;
}
