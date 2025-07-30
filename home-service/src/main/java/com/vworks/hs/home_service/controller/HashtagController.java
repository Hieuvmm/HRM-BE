package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.request.hashtag.CreateHashtagRequest;
import com.vworks.hs.home_service.models.request.hashtag.DeleteHashtagRequest;
import com.vworks.hs.home_service.models.request.hashtag.GetHashtagByIdRequest;
import com.vworks.hs.home_service.models.request.hashtag.UpdateHashtagRequest;
import com.vworks.hs.home_service.service.HashtagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_HASHTAG)
public class HashtagController {

    private final HashtagService hashtagService;

    @PostMapping("/create")
    public BaseResponse<?> create(@RequestBody CreateHashtagRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return hashtagService.create(request, httpServletRequest);
    }

    @PostMapping("/update")
    public BaseResponse<?> update(@RequestBody UpdateHashtagRequest request, HttpServletRequest httpServletRequest) throws Exception {
        return hashtagService.update(request, httpServletRequest);
    }

    @PostMapping("/delete")
    public BaseResponse<?> delete(@RequestBody DeleteHashtagRequest request) throws Exception {
        return hashtagService.delete(request);
    }

    @PostMapping("/list")
    public BaseResponse<?> list() throws Exception {
        return hashtagService.list();
    }

    @PostMapping("/get-by-id")
    public BaseResponse<?> getById(@RequestBody GetHashtagByIdRequest request) throws Exception {
        return hashtagService.getById(request.getId());
    }
}
