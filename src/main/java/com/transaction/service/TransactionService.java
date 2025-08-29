package com.transaction.service;

import com.transaction.dto.TransactionDTO;
import com.transaction.entity.TransactionEntity;
import com.transaction.exception.TransactionException;
import com.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionEntity> addTransactions(List<TransactionDTO> dtoList) {
        List<TransactionEntity> transactionsToSave = new ArrayList<>();

        for (TransactionDTO dto : dtoList) {
            Optional<TransactionEntity> existing = transactionRepository.findByTransactionId(dto.getTransactionId());
            if (existing.isPresent()) continue;

            TransactionEntity entity = new TransactionEntity();
            entity.setTransactionId(dto.getTransactionId());
            entity.setUserId(dto.getUserId());
            entity.setMerchant(dto.getMerchant());
            entity.setAmount(dto.getAmount());
            entity.setCurrency(dto.getCurrency());
            entity.setLocation(dto.getLocation());
            entity.setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : Timestamp.from(Instant.now()));

            transactionsToSave.add(entity);
        }

        return transactionRepository.saveAll(transactionsToSave);
    }

    public List<TransactionEntity> search(String transactionId, String userId, String merchant, String currency, String location, BigDecimal amount, Timestamp timestamp) {
        return transactionRepository.searchTransactions(transactionId, userId, merchant, currency, location, amount, timestamp);
    }

    public TransactionEntity updateTransaction(TransactionDTO dto, Integer id) {
        Optional<TransactionEntity> optional = transactionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new TransactionException("Transaction not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

        TransactionEntity entity = optional.get();

        // Check if the new transactionId already exists in another record
        if (dto.getTransactionId() != null) {
            Optional<TransactionEntity> existing = transactionRepository.findByTransactionId(dto.getTransactionId());

            // If another transaction exists with the same transactionId and a different database ID, raise error
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new TransactionException("Transaction ID '" + dto.getTransactionId() + "' already exists!", HttpStatus.CONFLICT);
            }

            entity.setTransactionId(dto.getTransactionId());
        }

        if (dto.getUserId() != null) entity.setUserId(dto.getUserId());
        if (dto.getMerchant() != null) entity.setMerchant(dto.getMerchant());
        if (dto.getAmount() != null) entity.setAmount(dto.getAmount());
        if (dto.getCurrency() != null) entity.setCurrency(dto.getCurrency());
        if (dto.getLocation() != null) entity.setLocation(dto.getLocation());
        if (dto.getTimestamp() != null) entity.setTimestamp(dto.getTimestamp());

        return transactionRepository.save(entity);
    }

    public String deleteById(Integer id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionException("Transaction not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        transactionRepository.deleteById(id);
        return "Transaction deleted with ID: " + id;
    }

    public String deleteAll() {
        transactionRepository.deleteAll();
        return "All transactions deleted";
    }

    public Page<TransactionEntity> findAll(Pageable pageable) {

        return transactionRepository.findAll(pageable);
    }

    public Page<TransactionEntity> getFilteredTransactions(String merchant, String location, String currency, BigDecimal amount, Pageable pageable) {
        return transactionRepository.searchWithFilters(merchant, location, currency, amount, pageable);
    }
}
