package com.vworks.hs.admin_service.service;

import com.vworks.hs.admin_service.entity.JobTitleEntity;
import com.vworks.hs.admin_service.model.requestBody.PostCreateJobTitleRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostDeleteJobTitleRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostSearchJobTitleRequestBody;
import com.vworks.hs.admin_service.model.requestBody.PostUpdateJobTitleRequestBody;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface JobTitleService {
    Object postCreateJobTitle(PostCreateJobTitleRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Object postUpdateJobTitle(PostUpdateJobTitleRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Page<JobTitleEntity> postSearchJobTitle(PostSearchJobTitleRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Object postDeleteJobTitle(PostDeleteJobTitleRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
