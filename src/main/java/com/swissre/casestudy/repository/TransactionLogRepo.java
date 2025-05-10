package com.swissre.casestudy.repository;

import com.swissre.casestudy.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepo extends JpaRepository<TransactionLog, Long> {
}