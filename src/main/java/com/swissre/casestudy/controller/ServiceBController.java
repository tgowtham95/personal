package com.swissre.casestudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/service-b")
public class ServiceBController {

    @GetMapping("/process")
    public String simulateProcessing() {
        log.info("Service B received the request");
        return "Processed by Service B";
    }
}