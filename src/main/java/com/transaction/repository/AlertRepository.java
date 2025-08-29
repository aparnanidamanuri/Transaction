package com.transaction.repository;

import com.transaction.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, Long> {

    List<AlertEntity> findByTransactionId(String transactionId);
    List<AlertEntity> findByAlertType(String alertType);
}
