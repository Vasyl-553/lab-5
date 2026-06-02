package com.logistics.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CargoTest {

    @Test
    void cargoBuilder_ShouldConvertCentimetersToMetersAndCalculateVolume() {
        // Arrange & Act: 200см = 2м, 100см = 1м, 300см = 3м
        Cargo cargo = new Cargo.Builder()
                .setLength(200d)
                .setWidth(100d)
                .setHeight(300d)
                .build();

        // Assert
        assertEquals(6.0, cargo.getVolume(), "Об'єм має розраховуватися у метрах");
    }

    @Test
    void cargo_EmptyConstructor_ShouldCreateObject() {
        Cargo cargo = new Cargo();
        assertNotNull(cargo, "Порожній конструктор має працювати для JPA");
    }
}