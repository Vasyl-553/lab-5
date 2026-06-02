package com.logistics.controller;

import com.logistics.model.Cargo;
import com.logistics.model.Vehicle;
import com.logistics.service.CargoDeliveringService;
import com.logistics.service.CargoLoadingService;
import com.logistics.service.CargoManager;
import com.logistics.service.FinancesManager;
import com.logistics.service.VehicleManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final CargoLoadingService cargoLoadingService;
    private final CargoDeliveringService cargoDeliveringService;
    private final VehicleManager vehicleManager;
    private final CargoManager cargoManager;
    private final FinancesManager financesManager;

    public DeliveryController(CargoLoadingService cargoLoadingService,
                              CargoDeliveringService cargoDeliveringService,
                              VehicleManager vehicleManager,
                              CargoManager cargoManager,
                              FinancesManager financesManager) {
        this.cargoLoadingService = cargoLoadingService;
        this.cargoDeliveringService = cargoDeliveringService;
        this.vehicleManager = vehicleManager;
        this.cargoManager = cargoManager;
        this.financesManager = financesManager;
    }

    // POST http://localhost:8080/api/deliveries/{vehicleIndex}/load/{cargoIndex}
    @PostMapping("/{vehicleIndex}/load/{cargoIndex}")
    public ResponseEntity<String> loadVehicle(@PathVariable int vehicleIndex, @PathVariable int cargoIndex) {

        // 1. Перевірка: чи існують такі машина та вантаж
        if (vehicleIndex < 0 || vehicleIndex >= vehicleManager.getFleet().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found!");
        }
        if (cargoIndex < 0 || cargoIndex >= cargoManager.getWarehouse().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cargo not found in warehouse!");
        }

        Vehicle vehicle = vehicleManager.getFleet().get(vehicleIndex);
        Cargo cargo = cargoManager.getWarehouse().get(cargoIndex);

        // 2. Логіка завантаження
        if (cargoLoadingService.canFit(vehicle, cargo.getVolume())) {
            cargoLoadingService.load(vehicle, cargo.getVolume());
            // 3. Видаляємо вантаж зі складу після успішного завантаження
            cargoManager.getWarehouse().remove(cargoIndex);

            return ResponseEntity.ok("Cargo successfully loaded into the vehicle!");
        } else {
            return ResponseEntity.badRequest().body("Cargo is too large or vehicle is too small/broken!");
        }
    }

    // POST http://localhost:8080/api/deliveries/{vehicleIndex}/start
    // Тіло запиту (JSON): {"distance": 1500.0}
    @PostMapping("/{vehicleIndex}/start")
    public ResponseEntity<String> startDelivery(@PathVariable int vehicleIndex, @RequestBody DeliveryRequest request) {

        if (vehicleIndex < 0 || vehicleIndex >= vehicleManager.getFleet().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found!");
        }

        Vehicle vehicle = vehicleManager.getFleet().get(vehicleIndex);

        // Перевіряємо, чи машина завантажена і не поламана (логіка з canMakeDelivery)
        if (cargoDeliveringService.canMakeDelivery(vehicle, request.getDistance())) {

            // Зберігаємо поточний об'єм ДО відправки (знадобиться для розрахунку грошей)
            Double loadedVolume = vehicle.getCurrentlyLoaded();

            cargoDeliveringService.makeDelivery(vehicle, request.getDistance());

            // Фіксуємо майбутній прибуток (використовуємо String.valueOf(vehicle.getId()) як ключ мапи)
            financesManager.getFinances().addToMoneyToBeEarned(
                    request.getDistance(),
                    loadedVolume,
                    String.valueOf(vehicle.getId())
            );

            return ResponseEntity.ok("Delivery started successfully! Distance: " + request.getDistance());
        } else {
            return ResponseEntity.badRequest().body("Vehicle cannot make this delivery. Check if it's broken, empty, or needs repair (mileage).");
        }
    }

    // POST http://localhost:8080/api/deliveries/{vehicleIndex}/finish
    @PostMapping("/{vehicleIndex}/finish")
    public ResponseEntity<String> finishDelivery(@PathVariable int vehicleIndex) {

        if (vehicleIndex < 0 || vehicleIndex >= vehicleManager.getFleet().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found!");
        }

        Vehicle vehicle = vehicleManager.getFleet().get(vehicleIndex);

        if (vehicle.isBusy()) {
            cargoDeliveringService.finishDelivery(vehicle);

            // Зараховуємо зароблені гроші на баланс та збільшуємо лічильник доставлення
            financesManager.getFinances().addToEarnings(String.valueOf(vehicle.getId()));
            financesManager.getFinances().addSuccessfulDelivery();

            return ResponseEntity.ok("Delivery finished! Earnings added to your balance.");
        }

        return ResponseEntity.badRequest().body("This vehicle is not currently in an active delivery!");
    }

    // Внутрішній клас-DTO для прийняття JSON тіла у методі startDelivery
    static class DeliveryRequest {
        private Double distance;

        // Порожній конструктор потрібен для Jackson (десеріалізація JSON)
        public DeliveryRequest() {}

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }
    }
}