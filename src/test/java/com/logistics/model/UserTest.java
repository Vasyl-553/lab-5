package com.logistics.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void user_GettersAndSetters_ShouldWorkCorrectly() {
        User user = new User(); // Використовуємо порожній конструктор

        user.setName("John");
        user.setSurname("Doe");

        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());

        Finances finances = new Finances();
        user.setFinances(finances);
        assertEquals(finances, user.getFinances());

        List<Vehicle> fleet = new ArrayList<>();
        fleet.add(new ContainerTruck(100d, 0d, "Truck"));
        user.setFleet(fleet);
        assertEquals(1, user.getFleet().size());

        List<Cargo> warehouse = new ArrayList<>();
        warehouse.add(new Cargo());
        user.setWarehouse(warehouse);
        assertEquals(1, user.getWarehouse().size());

        assertNull(user.getId(), "Id має бути null до збереження в базу");
    }

    @Test
    void user_ConstructorWithName_ShouldInitializeFields() {
        User user = new User("John", "Doe");
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertNotNull(user.getFleet(), "Список має ініціалізуватися автоматично");
        assertNotNull(user.getWarehouse(), "Список має ініціалізуватися автоматично");
    }
}