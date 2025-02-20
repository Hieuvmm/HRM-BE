package com.vworks.wms.warehouse_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${whs-properties.api-prefix}/im-wh-detail/")
@RequiredArgsConstructor
public class ImportWarehouseDetailController {
}
