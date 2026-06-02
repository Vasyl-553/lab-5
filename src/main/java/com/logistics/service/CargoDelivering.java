package com.logistics.service;

import com.logistics.model.Vehicle;

public interface CargoDelivering {
    void makeDelivery(Vehicle vehicleToBeDelivered, Double distance);
    void finishDelivery(Vehicle vehicleToBeDelivered);
}
