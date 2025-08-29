package com.transaction.controller;

import com.transaction.dto.AlertDTO;
import com.transaction.entity.AlertEntity;
import com.transaction.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("/addAlerts")
    public ResponseEntity<Map<String, Object>> addAlerts(@RequestBody List<AlertDTO> alertDTOs) {
        List<AlertEntity> savedAlerts = alertService.addAlerts(alertDTOs);
        Map<String, Object> response = new HashMap<>();
        response.put("savedAlerts", savedAlerts);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAllAlerts")
    public List<AlertEntity> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/getAlert/{id}")
    public ResponseEntity<AlertEntity> getAlertById(@PathVariable Long id) {
        return alertService.getAlertById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/updateAlert/{id}")
    public ResponseEntity<AlertEntity> updateAlert(@PathVariable Long id, @RequestBody AlertDTO dto) {
        return ResponseEntity.ok(alertService.updateAlert(id, dto));
    }

    @DeleteMapping("/deleteAlert/{id}")
    public ResponseEntity<String> deleteAlert(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.deleteAlert(id));
    }

    @DeleteMapping("/deleteAllAlerts")
    public ResponseEntity<String> deleteAllAlerts() {
        return ResponseEntity.ok(alertService.deleteAllAlerts());
    }
}
