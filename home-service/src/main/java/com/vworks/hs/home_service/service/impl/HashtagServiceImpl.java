package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.HashtagEntity;
import com.vworks.hs.home_service.models.request.hashtag.CreateHashtagRequest;
import com.vworks.hs.home_service.models.request.hashtag.DeleteHashtagRequest;
import com.vworks.hs.home_service.models.request.hashtag.UpdateHashtagRequest;
import com.vworks.hs.home_service.repository.HashtagRepository;
import com.vworks.hs.home_service.service.HashtagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public BaseResponse<?> create(CreateHashtagRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        HashtagEntity entity = new HashtagEntity();
        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        hashtagRepository.save(entity);
        return new BaseResponse<>(entity);
    }

    @Override
    public BaseResponse<?> update(UpdateHashtagRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        HashtagEntity entity = hashtagRepository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "HASHTAG_NOT_FOUND", "Không tìm thấy hashtag"));

        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        hashtagRepository.save(entity);
        return new BaseResponse<>(entity);
    }

    @Override
    public BaseResponse<?> delete(DeleteHashtagRequest request) throws WarehouseMngtSystemException {
        hashtagRepository.deleteById(request.getId());
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> list() throws WarehouseMngtSystemException {
        List<HashtagEntity> result = hashtagRepository.findAll();
        return new BaseResponse<>(result);
    }
    @Override
    public BaseResponse<?> getById(Long id) throws Exception {
        return new BaseResponse<>(hashtagRepository.findById(id)
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "HASHTAG_NOT_FOUND", "Không tìm thấy hashtag")));
    }
}