package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.profession.PostCreateOrUpdateProfessionReqBody;
import com.vworks.hs.home_service.models.request.profession.PostHandleByCodeProfessionReqBody;
import com.vworks.hs.home_service.models.request.profession.PostListProfessionReqBody;
import com.vworks.hs.home_service.models.response.profession.PostGetProfessionResBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface ProfessionService {
    Page<PostGetProfessionResBody> postListProfession(PostListProfessionReqBody reqBody);

    Object postCreateProfession(PostCreateOrUpdateProfessionReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    Object postUpdateProfession(PostCreateOrUpdateProfessionReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    PostGetProfessionResBody postDetailProfession(PostHandleByCodeProfessionReqBody reqBody) throws WarehouseMngtSystemException;

    Object postDeleteProfession(PostHandleByCodeProfessionReqBody reqBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
