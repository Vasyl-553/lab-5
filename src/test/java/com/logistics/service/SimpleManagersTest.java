package com.logistics.service;

import com.logistics.model.Cargo;
import com.logistics.model.Finances;
import com.logistics.model.TankerTruck;
import com.logistics.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SimpleManagersTest {

    @Test
    void cargoManager_ShouldAddAndSetWarehouse() {
        CargoManager manager = new CargoManager();
        Cargo cargo = new Cargo();

        manager.addCargo(cargo);
        assertEquals(1, manager.getWarehouse().size());

        manager.setWarehouse(new ArrayList<>());
        assertEquals(0, manager.getWarehouse().size());
    }

    @Test
    void financesManager_ShouldSetAndGetFinances() {
        FinancesManager manager = new FinancesManager();
        Finances finances = new Finances();

        manager.setFinances(finances);
        assertEquals(finances, manager.getFinances());
    }

    @Test
    void repairing_ShouldMarkVehicleAsRepaired() {
        Vehicle truck = new TankerTruck(1000, 0, "Tanker");
        Repairing.repair(truck);
        assertFalse(truck.isBroken());
    }
}