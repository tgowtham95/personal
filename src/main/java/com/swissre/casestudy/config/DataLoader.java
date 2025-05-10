package com.swissre.casestudy.config;

import com.swissre.casestudy.entity.UserRequest;
import com.swissre.casestudy.repository.UserRequestRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final UserRequestRepo userRequestRepo;

    @PostConstruct
    public void loadData() {
        userRequestRepo.save(UserRequest.builder()
                .name("Gowthamkumar")
                .age(29)
                .dob(LocalDate.of(1995, 10, 17))
                .status("PRELOADED")
                .createdAt(LocalDateTime.now())
                .build());
    }
}

