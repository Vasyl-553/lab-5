package com.logistics.service;

import com.logistics.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class CargoDeliveringService implements CargoDelivering {


    @Override
    public void makeDelivery(Vehicle vehicle, Double distance) {
        vehicle.markAsBusy();
        vehicle.changeMileage(distance);
    }

    public boolean canMakeDelivery(Vehicle vehicle, Double distance) {
        return vehicle.checkMileage(distance);
    }

    @Override
    public void finishDelivery(Vehicle vehicle) {
            vehicle.markAsNotBusy();
            vehicle.changeFreeSpace(-1d);
            System.out.println("The delivery was successfully ended!");
    }
}
