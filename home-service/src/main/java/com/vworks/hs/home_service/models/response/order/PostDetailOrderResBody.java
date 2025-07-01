package com.vworks.hs.home_service.models.response.order;

import com.vworks.hs.home_service.entities.OrderEntity;
import com.vworks.hs.home_service.models.MaterialOrderModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailOrderResBody {
    private OrderEntity order;
    private List<MaterialOrderModel> materialOrders;
}
