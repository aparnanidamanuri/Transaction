package com.transaction.service;

import com.transaction.dto.FlaggedTransactionsDTO;
import com.transaction.dto.FraudRulesDTO;
import com.transaction.entity.FlaggedTransactionsEntity;
import com.transaction.entity.FraudRulesEntity;
import com.transaction.repository.FlaggedTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class FlaggedTransactionsService {

    @Autowired
    private FlaggedTransactionsRepository flaggedTransactionsRepository;

    public FlaggedTransactionsEntity addFlaggedTransaction(FlaggedTransactionsDTO flaggedTransactionsDTO) {

        FlaggedTransactionsEntity flaggedTransactionsEntity = new FlaggedTransactionsEntity();

        flaggedTransactionsEntity.setTransactionId(flaggedTransactionsDTO.getTransactionId());
        flaggedTransactionsEntity.setReason(flaggedTransactionsDTO.getReason());
        flaggedTransactionsEntity.setFlaggedAt(Timestamp.from(Instant.now()));

        return flaggedTransactionsRepository.save(flaggedTransactionsEntity);
    }

    public List<FlaggedTransactionsEntity> getAllFlaggedTransactions() {
        return flaggedTransactionsRepository.findAll();
    }

    public Optional<FlaggedTransactionsEntity> getById(Long id) {
        return flaggedTransactionsRepository.findById(id);
    }

    public Optional<FlaggedTransactionsEntity> getByTransactionId(String transactionId) {
        return flaggedTransactionsRepository.findByTransactionId(transactionId);
    }

    public List<FlaggedTransactionsEntity> getByReason(String reason) {
        return flaggedTransactionsRepository.findByReason(reason);
    }

    public List<FlaggedTransactionsEntity> getByFlaggedAtAfter(Timestamp timestamp) {
        return flaggedTransactionsRepository.findByFlaggedAtAfter(timestamp);
    }
}
