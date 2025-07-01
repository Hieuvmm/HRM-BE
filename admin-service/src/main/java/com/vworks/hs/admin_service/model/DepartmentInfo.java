package com.vworks.hs.admin_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vworks.hs.admin_service.entity.DepartmentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentInfo extends DepartmentEntity {
}
