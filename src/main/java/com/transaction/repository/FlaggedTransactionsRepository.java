package com.transaction.repository;

import com.transaction.entity.FlaggedTransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlaggedTransactionsRepository extends JpaRepository<FlaggedTransactionsEntity, Long> {

    List<FlaggedTransactionsEntity> findAll();
    Optional<FlaggedTransactionsEntity> findById(Long id);
    Optional<FlaggedTransactionsEntity> findByTransactionId(String transactionId);
    List<FlaggedTransactionsEntity> findByReason(String reason);
    List<FlaggedTransactionsEntity> findByFlaggedAtAfter(Timestamp timestamp);
}
