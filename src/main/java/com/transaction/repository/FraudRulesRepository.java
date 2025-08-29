package com.transaction.repository;

import com.transaction.entity.FraudRulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FraudRulesRepository extends JpaRepository<FraudRulesEntity, Long> {

    Optional<FraudRulesEntity> findByRuleName(String ruleName);

    @Query("SELECT r FROM FraudRulesEntity r WHERE " +
            "(:ruleName IS NULL OR r.ruleName = :ruleName) AND " +
            "(:ruleDescription IS NULL OR r.ruleDescription = :ruleDescription) AND " +
            "(:thresholdAmount IS NULL OR r.thresholdAmount >= :thresholdAmount) AND " +
            "(:enabled IS NULL OR r.enabled = :enabled)")
    List<FraudRulesEntity> searchRules(
            @Param("ruleName") String ruleName,
            @Param("ruleDescription") String ruleDescription,
            @Param("thresholdAmount") BigDecimal thresholdAmount,
            @Param("enabled") Boolean enabled
    );
}
