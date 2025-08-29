package com.transaction.controller;

import com.transaction.dto.TransactionDTO;
import com.transaction.entity.TransactionEntity;
import com.transaction.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {


    @Autowired
    private TransactionService transactionService;

//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest request){
//        return (CsrfToken) request.getAttribute("_csrf");
//    }

    @PostMapping("/addTransactions")
    public ResponseEntity<List<TransactionEntity>> addTransactions(@RequestBody @Valid List<@Valid TransactionDTO> dtoList) {
        List<TransactionEntity> savedTransactions = transactionService.addTransactions(dtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransactions);
    }

    //@GetMapping("/transactions")
    //public Page<TransactionEntity> getAll(Pageable pageable) {
        //return transactionService.findAll(pageable);
    //}

    @GetMapping("/transactions")
    public Page<TransactionEntity> getAllTransactions(
            @RequestParam(required = false) String merchant,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) BigDecimal amount,
            @PageableDefault(size = 2, sort = "id") Pageable pageable
    ) {
        return transactionService.getFilteredTransactions(merchant, location, currency, amount, pageable);
    }

    @GetMapping("/searchTransactions")
    public ResponseEntity<?> searchTransactions(
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String merchant,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Timestamp timestamp


    ) {
        List<TransactionEntity> result = transactionService.search(transactionId, userId, merchant, currency, location, amount, timestamp);
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "No transactions found"));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateTransaction/{id}")
    public ResponseEntity<TransactionEntity> updateTransaction(@PathVariable Integer id, @RequestBody TransactionDTO dto) {
        return ResponseEntity.ok(transactionService.updateTransaction(dto, id));
    }

    @DeleteMapping("/deleteTransaction/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionService.deleteById(id));
    }

    @DeleteMapping("/deleteAllTransactions")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(transactionService.deleteAll());
    }

}
