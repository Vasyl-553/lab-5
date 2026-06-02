package com.logistics.service;

import com.logistics.model.*;

public interface CargoLoading {
    boolean canFit(Vehicle vehicleToBeLoaded, Double boxSize);
    void load(Vehicle vehicleToBeLoaded, Double volume);
}
