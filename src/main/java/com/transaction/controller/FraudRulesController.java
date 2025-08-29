package com.transaction.controller;

import com.transaction.dto.FraudRulesDTO;
import com.transaction.entity.FraudRulesEntity;
import com.transaction.service.FlaggedTransactionsService;
import com.transaction.service.FraudRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FraudRulesController {

    @Autowired
    private FraudRulesService fraudRulesService;

    @PostMapping("/addRules")
    public ResponseEntity<Map<String, Object>> addRules(@RequestBody List<FraudRulesDTO> dtoList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fraudRulesService.addRules(dtoList));
    }

    @GetMapping("/searchRules")
    public ResponseEntity<List<FraudRulesEntity>> searchRules(
            @RequestParam(required = false) String ruleName,
            @RequestParam(required = false) String ruleDescription,
            @RequestParam(required = false) BigDecimal thresholdAmount,
            @RequestParam(required = false) Boolean enabled
    ) {
        List<FraudRulesEntity> list = fraudRulesService.searchRules(ruleName, ruleDescription, thresholdAmount, enabled);
        return list.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList())
                : ResponseEntity.ok(list);
    }

    @PutMapping("/updateRule/{id}")
    public ResponseEntity<FraudRulesEntity> updateRule(@PathVariable Long id, @RequestBody FraudRulesDTO dto) {
        return ResponseEntity.ok(fraudRulesService.updateRule(dto, id));
    }

    @DeleteMapping("/deleteRule/{id}")
    public ResponseEntity<String> deleteRule(@PathVariable Long id) {
        return ResponseEntity.ok(fraudRulesService.deleteById(id));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(fraudRulesService.deleteAll());
    }
}
