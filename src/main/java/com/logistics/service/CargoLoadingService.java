package com.logistics.service;

import com.logistics.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class CargoLoadingService implements CargoLoading {


    @Override
    public void load(Vehicle vehicleToBeLoaded, Double volume)
    {
        vehicleToBeLoaded.changeFreeSpace(volume);
        vehicleToBeLoaded.markAsLoaded();
    }

    @Override
    public boolean canFit(Vehicle vehicleToBeLoaded, Double boxSize)
    {
        Double spaceAvailable = vehicleToBeLoaded.spaceAvailable();
        return (spaceAvailable >= boxSize);
    }
}
