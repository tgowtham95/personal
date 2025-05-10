package com.swissre.casestudy.service;

import com.swissre.casestudy.entity.UserRequest;
import com.swissre.casestudy.entity.TransactionLog;
import com.swissre.casestudy.repository.TransactionLogRepo;
import com.swissre.casestudy.repository.UserRequestRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ServiceAServiceTest {

    @Mock
    private UserRequestRepo userRequestRepo;

    @Mock
    private TransactionLogRepo transactionLogRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ServiceAService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //@Test
    void testHandleRequest_success() {
        UserRequest request = new UserRequest();
        request.setName("John");

        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("Processed by Service B", HttpStatus.OK));

        String status = service.handleRequest(request);

        assertEquals("COMPLETED", status);
        verify(userRequestRepo, times(2)).save(any(UserRequest.class));
        verify(transactionLogRepo, atLeastOnce()).save(any(TransactionLog.class));
    }

    @Test
    void testHandleRequest_failure() {
        UserRequest request = new UserRequest();
        request.setName("John");

        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("Service B down"));

        String status = service.handleRequest(request);

        assertEquals("FAILED", status);
        verify(userRequestRepo, times(2)).save(any(UserRequest.class));
        verify(transactionLogRepo, atLeastOnce()).save(any(TransactionLog.class));
    }
}
