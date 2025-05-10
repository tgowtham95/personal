package com.swissre.casestudy.service;

import com.swissre.casestudy.entity.TransactionLog;
import com.swissre.casestudy.entity.UserRequest;
import com.swissre.casestudy.repository.TransactionLogRepo;
import com.swissre.casestudy.repository.UserRequestRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceAService {

    private final UserRequestRepo userRequestRepo;
    private final TransactionLogRepo logRepo;
    private final RestTemplate restTemplate;

    @Value("${service.b.url}")
    private String serviceBUrl;

    @Transactional
    @Retryable(
            value = {ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public String handleRequest(UserRequest request) {
        request.setStatus("INITIATED");
        request.setCreatedAt(LocalDateTime.now());
        userRequestRepo.save(request);

        logRepo.save(TransactionLog.builder()
                .message("User request saved")
                .phase("SAVE")
                .timestamp(LocalDateTime.now())
                .build());
        log.info("User request saved: {}", request);

        try {
            var response = restTemplate.getForEntity(serviceBUrl, String.class);
            logRepo.save(TransactionLog.builder()
                    .message("Service B called")
                    .phase("CALL_B")
                    .timestamp(LocalDateTime.now())
                    .build());

            request.setStatus("COMPLETED");
            log.info("Service B response: {}", response.getBody());

        } catch (Exception e) {
            request.setStatus("FAILED");
            logRepo.save(TransactionLog.builder()
                    .message("Service B failed: " + e.getMessage())
                    .phase("ERROR")
                    .timestamp(LocalDateTime.now())
                    .build());
            log.error("Service B error", e);
        }

        userRequestRepo.save(request);
        return request.getStatus();
    }
}
