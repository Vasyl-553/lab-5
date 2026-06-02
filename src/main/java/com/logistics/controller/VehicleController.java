package com.logistics.controller;

import com.logistics.model.Vehicle;
import com.logistics.service.Repairing;
import com.logistics.service.VehicleCreator;
import com.logistics.service.VehicleManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleManager vehicleManager;
    private final VehicleCreator vehicleCreator;

    public VehicleController(VehicleManager vehicleManager, VehicleCreator vehicleCreator) {
        this.vehicleManager = vehicleManager;
        this.vehicleCreator = vehicleCreator;
    }

    // 2. Display your fleet (GET http://localhost:8080/api/vehicles)
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> fleet = vehicleManager.getFleet();
        if (fleet.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Опціонально: можна додати сортування тут, якщо у вас є бін fleetSorter,
        // але зазвичай для API повертають як є, а фронтенд сам сортує.
        return ResponseEntity.ok(fleet);
    }

    // 1. Add a new transport (POST http://localhost:8080/api/vehicles)
    // Тіло запиту (JSON): {"type": 1, "mileage": 50000.0}
    // Типи: 1. Tanker Truck; 2. Container Truck; 3. Plane; 4. Train
    @PostMapping
    public ResponseEntity<String> addVehicle(@RequestBody VehicleRequest request) {
        int type = request.getType();
        if (type < 1 || type > 4) {
            return ResponseEntity.badRequest().body("Invalid vehicle type! Must be between 1 and 4.");
        }

        if (request.getMileage() < 0) {
            return ResponseEntity.badRequest().body("Mileage cannot be negative.");
        }

        String typeName = vehicleManager.getType(type);
        Vehicle newVehicle = vehicleCreator.create(type, request.getMileage(), typeName);
        vehicleManager.addVehicle(newVehicle);

        return ResponseEntity.status(HttpStatus.CREATED).body("Your vehicle has been successfully added!");
    }

    // Додатковий ендпоінт для отримання тільки поламаних машин (щоб фронтенд знав, що показувати)
    @GetMapping("/broken")
    public ResponseEntity<List<Vehicle>> getBrokenVehicles() {
        List<Vehicle> brokenVehicles = vehicleManager.getFleet().stream()
                .filter(Vehicle::isBroken)
                .toList();

        if (brokenVehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(brokenVehicles);
    }

    // 3. Repair a particular vehicle (PUT http://localhost:8080/api/vehicles/{index}/repair)
    @PutMapping("/{index}/repair")
    public ResponseEntity<String> repairVehicle(@PathVariable int index) {
        List<Vehicle> fleet = vehicleManager.getFleet();

        if (fleet.isEmpty() || index < 0 || index >= fleet.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found!");
        }

        Vehicle vehicle = fleet.get(index);

        if (!vehicle.isBroken()) {
            return ResponseEntity.badRequest().body("This vehicle is not broken, there is nothing to repair!");
        }

        Repairing.repair(vehicle);
        return ResponseEntity.ok("Vehicle successfully repaired!");
    }

    // 4. Delete all the garage (DELETE http://localhost:8080/api/vehicles)
    @DeleteMapping
    public ResponseEntity<String> clearFleet() {
        if (!vehicleManager.getFleet().isEmpty()) {
            vehicleManager.getFleet().clear();
            return ResponseEntity.ok("The fleet was successfully emptied!");
        }
        return ResponseEntity.badRequest().body("There is nothing to empty out!");
    }

    // 5. Delete a particular vehicle (DELETE http://localhost:8080/api/vehicles/{index})
    @DeleteMapping("/{index}")
    public ResponseEntity<String> deleteVehicle(@PathVariable int index) {
        List<Vehicle> fleet = vehicleManager.getFleet();

        if (fleet.isEmpty() || index < 0 || index >= fleet.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid index or empty fleet!");
        }

        fleet.remove(index);
        return ResponseEntity.ok("Vehicle deleted successfully!");
    }

    // Внутрішній клас-DTO для прийняття JSON тіла у методі addVehicle
    static class VehicleRequest {
        private int type;
        private double mileage;

        public VehicleRequest() {}

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getMileage() {
            return mileage;
        }

        public void setMileage(double mileage) {
            this.mileage = mileage;
        }
    }
}