package com.swissre.casestudy.repository;

import com.swissre.casestudy.entity.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepo extends JpaRepository<UserRequest, Long> {
}
