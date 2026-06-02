package com.logistics.service;

import com.logistics.model.ContainerTruck;
import com.logistics.model.TankerTruck;
import com.logistics.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleManagerTest {

    @Test
    void addAndGetFleet_ShouldWorkCorrectly() {
        VehicleManager manager = new VehicleManager();
        Vehicle truck = new TankerTruck(1000d, 0d, "Tanker");

        manager.addVehicle(truck);
        assertEquals(1, manager.getFleet().size());

        List<Vehicle> newFleet = new ArrayList<>();
        manager.setFleet(newFleet);
        assertEquals(0, manager.getFleet().size(), "setFleet має замінювати список");
    }

    @Test
    void getType_ShouldReturnCorrectString() {
        VehicleManager manager = new VehicleManager();
        assertEquals("TANKER_TRUCK", manager.getType(1));
        assertEquals("CONTAINER_TRUCK", manager.getType(2));
        assertEquals("PLANE", manager.getType(3));
        assertEquals("TRAIN", manager.getType(4));
    }

    @Test
    void findLoaded_ShouldReturnOnlyLoadedAndNotBusyVehicles() {
        VehicleManager manager = new VehicleManager();

        Vehicle v1 = new TankerTruck(1000d, 0d, "Tanker");
        v1.markAsLoaded(); // Завантажений, не зайнятий (ПІДХОДИТЬ)

        Vehicle v2 = new ContainerTruck(1000d, 0d, "Container");
        v2.markAsLoaded();
        v2.markAsBusy(); // Завантажений, але зайнятий (НЕ ПІДХОДИТЬ)

        Vehicle v3 = new TankerTruck(1000d, 0d, "Tanker2");
        // Не завантажений (НЕ ПІДХОДИТЬ)

        manager.setFleet(List.of(v1, v2, v3));

        List<Vehicle> loaded = manager.findLoaded(manager.getFleet());

        assertEquals(1, loaded.size());
        assertEquals(v1.getId(), loaded.get(0).getId());
    }

    @Test
    void sortFleet_ShouldSortByAppConfigComparator() {
        VehicleManager manager = new VehicleManager();
        AppConfig config = new AppConfig();

        Vehicle broken = new TankerTruck(100d, 0d, "Broken");
        broken.markAsRepaired(); // Це не зламає, але симулюємо
        // Оскільки ми не маємо прямого setter'а для isBroken, ми перевіримо сортування по spaceAvailable

        Vehicle bigSpace = new TankerTruck(5000d, 0d, "Big");
        Vehicle smallSpace = new TankerTruck(1000d, 0d, "Small");

        manager.setFleet(new ArrayList<>(List.of(smallSpace, bigSpace)));
        manager.sortFleet(config.fleetSorter());

        // Машина з більшим місцем має бути першою (Double.compare(v2, v1))
        assertEquals(bigSpace.getId(), manager.getFleet().get(0).getId());
    }
}