package com.swissre.casestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class CaseStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaseStudyApplication.class, args);
    }
}
