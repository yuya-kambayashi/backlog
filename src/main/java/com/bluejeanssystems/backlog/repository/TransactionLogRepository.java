package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
}
