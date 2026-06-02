package com.logistics.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FinancesTest {

    @Test
    void calculateEarnings_ShouldUseTariffCorrectly() {
        Finances finances = new Finances();
        double result = finances.calculateEarnings(100d, 10d);
        assertEquals(154.0d, result);
    }

    @Test
    void addToMoneyToBeEarned_ShouldStoreValueInMap() {
        Finances finances = new Finances();
        String vehicleId = "TRUCK-123";

        finances.addToMoneyToBeEarned(100d, 10d, vehicleId);

        assertEquals(154.0, finances.getMoneyToBeEarned(vehicleId));
    }

    @Test
    void addToEarnings_ShouldTransferMoneyFromMapToTotalEarnings() {
        Finances finances = new Finances();
        String vehicleId = "TRUCK-123";

        finances.addToMoneyToBeEarned(100d, 10d, vehicleId);

        finances.addToEarnings(vehicleId);

        assertEquals(154.0d, finances.getEarnings(), "Загальний баланс має збільшитися");
    }

    @Test
    void addSuccessfulDelivery_ShouldIncrementCounter() {
        Finances finances = new Finances();
        assertEquals(0, finances.getNumberOfSuccessfulDeliveries());

        finances.addSuccessfulDelivery();
        finances.addSuccessfulDelivery();

        assertEquals(2, finances.getNumberOfSuccessfulDeliveries());
    }
}