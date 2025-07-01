package com.vworks.hs.common_lib.model.idm.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vworks.hs.common_lib.model.idm.IdmAppResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdmAppResourceListResponse {
    private List<IdmAppResource> data;
}
