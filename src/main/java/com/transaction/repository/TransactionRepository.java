package com.transaction.repository;

import com.transaction.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    Optional<TransactionEntity> findByTransactionId(String transactionId);

    @Query("SELECT t FROM TransactionEntity t WHERE " +
            "(:transactionId IS NULL OR t.transactionId = :transactionId) AND " +
            "(:userId IS NULL OR t.userId = :userId) AND " +
            "(:merchant IS NULL OR t.merchant = :merchant) AND " +
            "(:currency IS NULL OR t.currency = :currency) AND " +
            "(:location IS NULL OR t.location = :location) AND " +
            "(:amount IS NULL OR t.amount >= :amount) AND " +
            "(:timestamp IS NULL OR t.timestamp >= :timestamp)")
    List<TransactionEntity> searchTransactions(
            @Param("transactionId") String transactionId,
            @Param("userId") String userId,
            @Param("merchant") String merchant,
            @Param("currency") String currency,
            @Param("location") String location,
            @Param("amount") BigDecimal amount,
            @Param("timestamp") Timestamp timestamp
    );


    boolean existsByTransactionId(String transactionId);

    @Query("SELECT t FROM TransactionEntity t WHERE " +
            "(:merchant IS NULL OR t.merchant = :merchant) AND " +
            "(:location IS NULL OR t.location = :location) AND " +
            "(:currency IS NULL OR t.currency = :currency) AND " +
            "(:amount IS NULL OR t.amount >= :amount)")
    Page<TransactionEntity> searchWithFilters(
            @Param("merchant") String merchant,
            @Param("location") String location,
            @Param("currency") String currency,
            @Param("amount") BigDecimal amount,
            Pageable pageable
    );
}
