package com.vworks.hs.home_service.models.response;

import com.vworks.hs.home_service.models.SearchExBillModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchExBillResponseBody {
    private List<SearchExBillModel> exBillList;
    private Integer totalPage;
    private long totalElement;
}
