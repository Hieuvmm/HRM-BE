package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.request.post.*;
import com.vworks.hs.home_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_POST)
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public BaseResponse<?> create(@RequestBody CreatePostRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return postService.create(request, httpServletRequest);
    }

    @PostMapping("/update")
    public BaseResponse<?> update(@RequestBody UpdatePostRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return postService.update(request, httpServletRequest);
    }

    @PostMapping("/delete")
    public BaseResponse<?> delete(@RequestBody DeletePostRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return postService.delete(request, httpServletRequest);
    }

    @PostMapping("/get")
    public BaseResponse<?> get(@RequestBody GetPostRequest request) throws Exception {
        return postService.get(request);
    }

    @PostMapping("/list")
    public BaseResponse<?> list(@RequestBody ListPostRequest request) throws Exception {
        return postService.list(request);
    }
}