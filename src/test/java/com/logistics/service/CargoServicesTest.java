package com.logistics.service;

import com.logistics.model.TankerTruck;
import com.logistics.model.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CargoServicesTest {

    @Test
    void cargoLoadingService_ShouldLoadAndCheckFit() {
        CargoLoadingService loadingService = new CargoLoadingService();
        Vehicle truck = new TankerTruck(1000, 0, "Tanker");

        // Тест canFit
        assertTrue(loadingService.canFit(truck, 500d));
        assertFalse(loadingService.canFit(truck, 2000d));

        // Тест load
        loadingService.load(truck, 500d);
        assertTrue(truck.isLoaded());
        assertEquals(500, truck.spaceAvailable());
    }

    @Test
    void cargoDeliveringService_ShouldManageDeliveryState() {
        CargoDeliveringService deliveringService = new CargoDeliveringService();
        Vehicle truck = new TankerTruck(1000, 0, "Tanker"); // Ліміт 1 000 000

        // Тест canMakeDelivery
        assertTrue(deliveringService.canMakeDelivery(truck, 5000d));
        assertFalse(deliveringService.canMakeDelivery(truck, 2000000d));

        // Тест makeDelivery
        deliveringService.makeDelivery(truck, 5000d);
        assertTrue(truck.isBusy());
        assertEquals("5000.0", truck.getInfo()[4]); // Перевірка пробігу

        // Тест finishDelivery
        deliveringService.finishDelivery(truck);
        assertFalse(truck.isBusy());
        // currentlyLoaded має скинутися (в тебе там changeFreeSpace(-1), що означає currentlyLoaded = 0)
        assertEquals(0, truck.getCurrentlyLoaded());
    }
}