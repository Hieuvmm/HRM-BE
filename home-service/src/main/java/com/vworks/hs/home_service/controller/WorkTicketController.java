package com.vworks.hs.home_service.controller;


import com.vworks.hs.home_service.config.WhsConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_WORK_TICKET)
public class WorkTicketController {
}
