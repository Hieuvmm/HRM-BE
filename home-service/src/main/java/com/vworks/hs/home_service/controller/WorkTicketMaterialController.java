package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.request.workTicketMaterial.AddWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.models.request.workTicketMaterial.DeleteWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.models.request.workTicketMaterial.UpdateWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.service.WorkTicketMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_WORK_TICKET_MATERIAL)
public class WorkTicketMaterialController {

    private final WorkTicketMaterialService service;

    @PostMapping("/add")
    public BaseResponse<?> add(@RequestBody AddWorkTicketMaterialRequestBody request) {
        return service.add(request);
    }

    @PostMapping("/update")
    public BaseResponse<?> update(@RequestBody UpdateWorkTicketMaterialRequestBody request) {
        return service.update(request);
    }

    @PostMapping("/delete")
    public BaseResponse<?> delete(@RequestBody DeleteWorkTicketMaterialRequestBody request) {
        return service.delete(request);
    }

    @GetMapping("/list")
    public BaseResponse<?> list(@RequestParam String workTicketId) {
        return service.list(workTicketId);
    }
}
