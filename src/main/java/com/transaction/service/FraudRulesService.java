package com.transaction.service;

import com.transaction.dto.FraudRulesDTO;
import com.transaction.entity.FraudRulesEntity;
import com.transaction.exception.FraudRuleException;
import com.transaction.repository.FraudRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FraudRulesService {

    @Autowired
    private FraudRulesRepository fraudRulesRepository;

    public Map<String, Object> addRules(List<FraudRulesDTO> rules) {
        List<FraudRulesEntity> toSave = new ArrayList<>();
        List<String> skipped = new ArrayList<>();

        for (FraudRulesDTO dto : rules) {
            if (fraudRulesRepository.findByRuleName(dto.getRuleName()).isPresent()) {
                skipped.add(dto.getRuleName());
                continue;
            }
            FraudRulesEntity entity = new FraudRulesEntity();
            entity.setRuleName(dto.getRuleName());
            entity.setRuleDescription(dto.getRuleDescription());
            entity.setThresholdAmount(dto.getThresholdAmount());
            entity.setEnabled(dto.getEnabled());
            toSave.add(entity);
        }

        List<FraudRulesEntity> saved = fraudRulesRepository.saveAll(toSave);
        Map<String, Object> result = new HashMap<>();
        result.put("savedRules", saved);
        result.put("skippedDuplicates", skipped);
        return result;
    }

    public List<FraudRulesEntity> searchRules(String ruleName, String ruleDescription, BigDecimal thresholdAmount, Boolean enabled) {
        return fraudRulesRepository.searchRules(ruleName, ruleDescription, thresholdAmount, enabled);
    }

    public FraudRulesEntity updateRule(FraudRulesDTO dto, Long id) {
        return fraudRulesRepository.findById(id).map(rule -> {
            if (dto.getRuleName() != null) rule.setRuleName(dto.getRuleName());
            if (dto.getRuleDescription() != null) rule.setRuleDescription(dto.getRuleDescription());
            if (dto.getThresholdAmount() != null) rule.setThresholdAmount(dto.getThresholdAmount());
            if (dto.getEnabled() != null) rule.setEnabled(dto.getEnabled());
            return fraudRulesRepository.save(rule);
        }).orElseThrow(() -> new FraudRuleException("Rule with ID " + id + " not found", HttpStatus.NOT_FOUND));
    }

    public String deleteById(Long id) {
        if (fraudRulesRepository.existsById(id)) {
            fraudRulesRepository.deleteById(id);
            return "Successfully deleted rule with ID: " + id;
        }
        return "No rule found with ID: " + id;
    }

    public String deleteAll() {
        fraudRulesRepository.deleteAll();
        return "All fraud rules deleted successfully.";
    }
}
