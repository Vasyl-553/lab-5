package com.logistics.service;

import com.logistics.model.Vehicle;

public interface IVehicle {
    Vehicle create(Double capacity, Double mileage, String type);
}
