package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.object.PostDetailObjectReqBody;
import com.vworks.hs.home_service.models.request.object.PostListObjectReqBody;
import com.vworks.hs.home_service.models.request.object.PostUpdateObjectReqBody;
import com.vworks.hs.home_service.models.response.object.PostDetailObjectResBody;
import com.vworks.hs.home_service.models.response.object.PostListObjectResBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface PartnerObjectService {
    Page<PostListObjectResBody> postListObject(PostListObjectReqBody reqBody, HttpServletRequest httpServletRequest);

    Object postCreateObject(PostUpdateObjectReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Object postUpdateObject(PostUpdateObjectReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostDetailObjectResBody postDetailObject(PostDetailObjectReqBody resBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Object postDeleteObject(PostDetailObjectReqBody resBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
