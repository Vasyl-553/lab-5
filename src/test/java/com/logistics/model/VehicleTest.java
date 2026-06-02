package com.logistics.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void plane_CheckMileage_LimitIs300000() {
        Plane plane = new Plane(1000d, 50000d, "Plane");

        // 50000 + 60000 = 110000 (< 300000)
        assertTrue(plane.checkMileage(60000d));

        // 50000 + 260000 = 3100000 (> 300000)
        assertFalse(plane.checkMileage(260000d));

        assertEquals("Plane 50000.0", plane.getInfoForBroken());
    }

    @Test
    void train_CheckMileage_LimitIs1000000() {
        Train train = new Train(5000d, 100000d, "Train");
        assertTrue(train.checkMileage(30000d));
        assertFalse(train.checkMileage(5000000d));
    }

    @Test
    void truck_CheckMileage_LimitIs100000() {
        ContainerTruck truck = new ContainerTruck(2000d, 5000d, "ContainerTruck");
        assertTrue(truck.checkMileage(40000d));
        assertFalse(truck.checkMileage(600000d));
    }

    @Test
    void vehicle_StateChanges_ShouldWorkCorrectly() {
        TankerTruck truck = new TankerTruck(10000d, 0d, "Tanker");

        // Тест статусу зайнятості
        assertFalse(truck.isBusy());
        truck.markAsBusy();
        assertTrue(truck.isBusy());
        truck.markAsNotBusy();
        assertFalse(truck.isBusy());

        // Тест завантаженості
        assertFalse(truck.isLoaded());
        truck.markAsLoaded();
        assertTrue(truck.isLoaded());

        // Тест ремонту
        assertFalse(truck.isBroken());
        truck.markAsRepaired(); // Просто перевіряємо, що метод не крашиться

        // Тест вільного місця
        assertEquals(10000d, truck.spaceAvailable());
        truck.changeFreeSpace(2000d);
        assertEquals(8000d, truck.spaceAvailable());
        assertEquals(2000d, truck.getCurrentlyLoaded());

        // Тест пробігу
        truck.changeMileage(500d);
        assertTrue(truck.getInfo()[4].equals("500.0")); // Індекс 4 - це mileage
    }

    @Test
    void vehicle_IdGeneration_ShouldContainType() {
        Train train = new Train(100d, 0d, "Train");
        assertNotNull(train.getId());
        assertTrue(train.getId().startsWith("Train-"));
    }
}