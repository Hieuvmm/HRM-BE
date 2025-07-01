package com.vworks.hs.home_service.models.response.warehouse;

import com.vworks.hs.home_service.models.DetailWareHouseModel;
import lombok.Data;

import java.util.List;

@Data
public class PostGetDetailWareHouseResponseBody {
    private List<DetailWareHouseModel> productList;
}
