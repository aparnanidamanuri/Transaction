package com.transaction.controller;

import com.transaction.dto.FlaggedTransactionsDTO;
import com.transaction.dto.FraudRulesDTO;
import com.transaction.entity.FlaggedTransactionsEntity;
import com.transaction.entity.FraudRulesEntity;
import com.transaction.service.FlaggedTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FlaggedTransactionsController {

    @Autowired
    private FlaggedTransactionsService flaggedTransactionsService;

    @PostMapping("/addFlaggedTransaction")
    public FlaggedTransactionsEntity addFlaggedTransaction(@RequestBody FlaggedTransactionsDTO flaggedTransactionsDTO){
        return flaggedTransactionsService.addFlaggedTransaction(flaggedTransactionsDTO);
    }

    @GetMapping("/flaggedTransactions")
    public List<FlaggedTransactionsEntity> getAllFlaggedTransactions(){
        return flaggedTransactionsService.getAllFlaggedTransactions();
    }

    @GetMapping("/flaggedTransactions/id/{id}")
    public Optional<FlaggedTransactionsEntity> getById(@PathVariable Long id){
        return flaggedTransactionsService.getById(id);
    }

    @GetMapping("/flaggedTransactions/transactionId/{transactionId}")
    public Optional<FlaggedTransactionsEntity> getByTransactionId(@PathVariable String transactionId) {
        return flaggedTransactionsService.getByTransactionId(transactionId);
    }

    @GetMapping("/flaggedTransactions/reason/{reason}")
    public List<FlaggedTransactionsEntity> getByReason(@PathVariable String reason) {
        return flaggedTransactionsService.getByReason(reason);
    }

    @GetMapping("/flaggedTransactions/after/{timestamp}")
    public List<FlaggedTransactionsEntity> getByFlaggedAtAfter(@PathVariable Timestamp timestamp) {
        return flaggedTransactionsService.getByFlaggedAtAfter(timestamp);
    }
}
