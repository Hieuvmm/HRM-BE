package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.request.contens.*;
import com.vworks.hs.home_service.service.PostHashtagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_POST_HASHTAG)
public class PostHashtagController {

    private final PostHashtagService postHashtagService;


    @PostMapping("/add")
    public BaseResponse<?> add(@RequestBody PostHashtagCreateRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return postHashtagService.add(request, httpServletRequest);
    }

    @PostMapping("/add-multiple")
    public BaseResponse<?> addMultiple(@RequestBody PostHashtagBatchRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return postHashtagService.addMultiple(request, httpServletRequest);
    }

    @PostMapping("/get-by-post")
    public BaseResponse<?> getByPostId(@RequestBody PostHashtagGetRequest request) throws Exception {
        return postHashtagService.getByPostId(request);
    }

    @PostMapping("/delete")
    public BaseResponse<?> delete(@RequestBody PostHashtagDeleteRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return postHashtagService.delete(request, httpServletRequest);
    }

    @PostMapping("/delete-by-post")
    public BaseResponse<?> deleteByPostId(@RequestBody PostHashtagDeleteByPostRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return postHashtagService.deleteByPostId(request, httpServletRequest);
    }
}
