package com.transaction.service;

import com.transaction.dto.AlertDTO;
import com.transaction.entity.AlertEntity;
import com.transaction.exception.AlertException;
import com.transaction.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;


    public List<AlertEntity> addAlerts(List<AlertDTO> alertDTOs) {
        List<AlertEntity> alertsToSave = new ArrayList<>();

        for (AlertDTO dto : alertDTOs) {
            AlertEntity alert = new AlertEntity();
            alert.setTransactionId(dto.getTransactionId());
            alert.setAlertType(dto.getAlertType());
            alert.setMessage(dto.getMessage());
            alert.setSentAt(dto.getSentAt() != null ? dto.getSentAt() : Timestamp.from(Instant.now()));
            alertsToSave.add(alert);
        }

        return alertRepository.saveAll(alertsToSave);
    }

    public List<AlertEntity> getAllAlerts() {
        return alertRepository.findAll();
    }

    public Optional<AlertEntity> getAlertById(Long id) {
        return alertRepository.findById(id);
    }

    public AlertEntity updateAlert(Long id, AlertDTO alertDTO) {
        AlertEntity existing = alertRepository.findById(id)
                .orElseThrow(() -> new AlertException("Alert with ID " + id + " not found", HttpStatus.NOT_FOUND));

        if (alertDTO.getTransactionId() != null) existing.setTransactionId(alertDTO.getTransactionId());
        if (alertDTO.getAlertType() != null) existing.setAlertType(alertDTO.getAlertType());
        if (alertDTO.getMessage() != null) existing.setMessage(alertDTO.getMessage());
        if (alertDTO.getSentAt() != null) existing.setSentAt(alertDTO.getSentAt());

        return alertRepository.save(existing);
    }

    public String deleteAlert(Long id) {
        if (!alertRepository.existsById(id)) {
            throw new AlertException("Alert with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
        alertRepository.deleteById(id);
        return "Deleted alert with ID " + id;
    }

    public String deleteAllAlerts() {
        alertRepository.deleteAll();
        return "Deleted all alerts successfully";
    }
}
