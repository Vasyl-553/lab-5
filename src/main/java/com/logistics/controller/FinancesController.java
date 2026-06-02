package com.logistics.controller;

import com.logistics.model.Finances;
import com.logistics.service.FinancesManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finances")
public class FinancesController {

    private final FinancesManager financesManager;

    public FinancesController(FinancesManager financesManager) {
        this.financesManager = financesManager;
    }

    // GET http://localhost:8080/api/finances
    // Отримання всієї фінансової статистики (баланс, тарифи, успішні доставки тощо)
    @GetMapping
    public ResponseEntity<Finances> getFinances() {
        Finances finances = financesManager.getFinances();
        return ResponseEntity.ok(finances);
    }

    // GET http://localhost:8080/api/finances/earnings
    // Отримання лише поточного балансу зароблених грошей
    @GetMapping("/earnings")
    public ResponseEntity<Double> getEarnings() {
        return ResponseEntity.ok(financesManager.getFinances().getEarnings());
    }

    // GET http://localhost:8080/api/finances/deliveries
    // Отримання загальної кількості успішних доставок
    @GetMapping("/deliveries")
    public ResponseEntity<Integer> getSuccessfulDeliveriesCount() {
        return ResponseEntity.ok(financesManager.getFinances().getNumberOfSuccessfulDeliveries());
    }

    // GET http://localhost:8080/api/finances/pending/{vehicleId}
    // Отримання очікуваного прибутку за доставку, яку зараз виконує конкретна машина
    @GetMapping("/pending/{vehicleId}")
    public ResponseEntity<?> getPendingEarnings(@PathVariable String vehicleId) {
        Double pendingMoney = financesManager.getFinances().getMoneyToBeEarned(vehicleId);

        if (pendingMoney == null) {
            return ResponseEntity.badRequest().body("No active delivery or pending earnings found for vehicle ID: " + vehicleId);
        }

        return ResponseEntity.ok(pendingMoney);
    }
}