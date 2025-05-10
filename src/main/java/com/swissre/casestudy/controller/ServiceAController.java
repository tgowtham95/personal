package com.swissre.casestudy.controller;

import com.swissre.casestudy.entity.UserRequest;
import com.swissre.casestudy.service.ServiceAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-a")
@RequiredArgsConstructor
public class ServiceAController {

    private final ServiceAService service;

    @PostMapping("/request")
    public ResponseEntity<String> processRequest(@RequestBody UserRequest userRequest) {
        String result = service.handleRequest(userRequest);
        return ResponseEntity.ok("Request processed. Final status: " + result);
    }
}