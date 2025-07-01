package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.projectType.PostDetailProjectTypeReqBody;
import com.vworks.hs.home_service.models.request.projectType.PostListProjectTypeReqBody;
import com.vworks.hs.home_service.models.request.projectType.PostUpdateProjectTypeReqBody;
import com.vworks.hs.home_service.models.response.projectType.PostDetailProjectTypeResBody;
import com.vworks.hs.home_service.models.response.projectType.PostListProjectTypeResBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface ProjectTypeService {
    Page<PostListProjectTypeResBody> postListProjectType(PostListProjectTypeReqBody reqBody);

    Object postCreateProjectType(PostUpdateProjectTypeReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Object postUpdateProjectType(PostUpdateProjectTypeReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostDetailProjectTypeResBody postDetailProjectType(PostDetailProjectTypeReqBody reqBody) throws WarehouseMngtSystemException;

    Object postDeleteProjectType(PostDetailProjectTypeReqBody reqBody) throws WarehouseMngtSystemException;
}
