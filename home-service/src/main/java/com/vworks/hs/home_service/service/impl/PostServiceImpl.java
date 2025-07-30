package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.PostEntity;
import com.vworks.hs.home_service.models.request.post.*;
import com.vworks.hs.home_service.repository.PostRepository;
import com.vworks.hs.home_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public BaseResponse<?> create(CreatePostRequest request, HttpServletRequest httpServletRequest) {
        PostEntity entity = new PostEntity();
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setSeoTitle(request.getSeoTitle());

        entity.setStatus(request.getStatus());

        entity.setCreatedAt(LocalDateTime.now());
        postRepository.save(entity);
        return new BaseResponse<>(entity);
    }

    @Override
    public BaseResponse<?> update(UpdatePostRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        PostEntity entity = postRepository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "POST_NOT_FOUND", "Không tìm thấy bài viết"));

        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setSeoTitle(request.getSeoTitle());
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        postRepository.save(entity);
        return new BaseResponse<>(entity);
    }

    @Override
    public BaseResponse<?> delete(DeletePostRequest request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        PostEntity entity = postRepository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "POST_NOT_FOUND", "Không tìm thấy bài viết"));
        postRepository.delete(entity);
        return new BaseResponse<>("Đã xóa bài viết");
    }

    @Override
    public BaseResponse<?> get(GetPostRequest request) throws WarehouseMngtSystemException {
        PostEntity entity = postRepository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "POST_NOT_FOUND", "Không tìm thấy bài viết"));
        return new BaseResponse<>(entity);
    }

    @Override
    public BaseResponse<?> list(ListPostRequest request) {
        List<PostEntity> result;
        if (request.getStatus() != null) {
            result = postRepository.findAll().stream()
                    .filter(p -> request.getStatus().equalsIgnoreCase(p.getStatus()))
                    .toList();
        } else {
            result = postRepository.findAll();
        }
        return new BaseResponse<>(result);
    }
}