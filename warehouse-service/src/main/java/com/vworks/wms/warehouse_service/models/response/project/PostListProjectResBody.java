package com.vworks.wms.warehouse_service.models.response.project;

import com.vworks.wms.admin_service.entity.UserInfoEntity;
import com.vworks.wms.warehouse_service.entities.ProjectTypeEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostListProjectResBody {
    private String code;
    private String name;
    private ProjectTypeEntity projectType;
    private UserInfoEntity supervisor;
    private String startDate;
    private String endDate;
    private String approvals;
    private String status;
}
