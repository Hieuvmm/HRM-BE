package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.PostHashtagEntity;
import com.vworks.hs.home_service.models.request.contens.*;
import com.vworks.hs.home_service.repository.PostHashtagRepository;
import com.vworks.hs.home_service.service.PostHashtagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostHashtagServiceImpl implements PostHashtagService {

    private final PostHashtagRepository postHashtagRepository;

    @Override
    public BaseResponse<?> add(PostHashtagCreateRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        PostHashtagEntity entity = new PostHashtagEntity();
        entity.setPostId(request.getPostId());
        entity.setHashtagId(request.getHashtagId());
        postHashtagRepository.save(entity);
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> addMultiple(PostHashtagBatchRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        List<PostHashtagEntity> entities = new ArrayList<>();
        for (Long hashtagId : request.getHashtagIds()) {
            PostHashtagEntity entity = new PostHashtagEntity();
            entity.setPostId(request.getPostId());
            entity.setHashtagId(hashtagId);
            entities.add(entity);
        }
        postHashtagRepository.saveAll(entities);
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> getByPostId(PostHashtagGetRequest request) throws WarehouseMngtSystemException {
        List<PostHashtagEntity> result = postHashtagRepository.findByPostId(request.getPostId());
        return new BaseResponse<>(result);
    }

    @Override
    public BaseResponse<?> delete(PostHashtagDeleteRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        postHashtagRepository.deleteById(request.getId());
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> deleteByPostId(PostHashtagDeleteByPostRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        postHashtagRepository.deleteByPostId(request.getPostId());
        return new BaseResponse<>();
    }
}
