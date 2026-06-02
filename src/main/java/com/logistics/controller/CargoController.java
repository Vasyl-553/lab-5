package com.logistics.controller;

import com.logistics.model.Cargo;
import com.logistics.service.CargoManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {
    private final CargoManager cargoManager;

    public CargoController(CargoManager cargoManager) {
        this.cargoManager = cargoManager;
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> getAllCargos() {
        List<Cargo> warehouse = cargoManager.getWarehouse();
        if(warehouse.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(warehouse);
    }

    @PostMapping
    public ResponseEntity<Cargo> createCargo(@RequestBody Map<String, Double> dimensions) {
        var newCargo = new Cargo.Builder()
                .setLength(dimensions.getOrDefault("length", 0.0d))
                .setWidth(dimensions.getOrDefault("width", 0.0d))
                .setHeight(dimensions.getOrDefault("height", 0.0d))
                .build();
        cargoManager.addCargo(newCargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCargo);
    }

    @DeleteMapping
    public ResponseEntity<String> clearWarehouse() {
        if(!cargoManager.getWarehouse().isEmpty())
        {
            cargoManager.getWarehouse().clear();
            return ResponseEntity.ok("Warehouse has been cleared!");
        }
        return ResponseEntity.badRequest().body("There is nothing to empty out!");
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<String> deleteCargo(@PathVariable int index) {
        List<Cargo> warehouse = cargoManager.getWarehouse();
        if(warehouse.isEmpty() || index < 0 || index >= warehouse.size())
            return ResponseEntity.badRequest().body("Invalid index or empty warehouse!");
        warehouse.remove(index);
        return ResponseEntity.ok("Cargo deleted successfully!");
    }
}
