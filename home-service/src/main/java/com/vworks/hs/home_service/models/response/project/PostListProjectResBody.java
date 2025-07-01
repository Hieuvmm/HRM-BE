package com.vworks.hs.home_service.models.response.project;

import com.vworks.hs.admin_service.entity.UserInfoEntity;
import com.vworks.hs.home_service.entities.ProjectTypeEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostListProjectResBody {
    private String code;
    private String name;
    private String approvals;
    private String customerCode;
    private String addressDetail;
    private String mainCategory;
    private int subItemCount;
    private String technicianCode;
    private ProjectTypeEntity projectType;
    private UserInfoEntity supervisor;
    private String endDate;
    private String startDate;
    private String status;
}
