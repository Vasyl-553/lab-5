package com.logistics.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TANKER_TRUCK")
public class TankerTruck extends Truck{

    public TankerTruck(double maxCapacity, double mileage, String type) {
        super(maxCapacity, mileage, type);
    }

    public TankerTruck() {

    }
}
