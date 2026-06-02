package com.logistics.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TRUCK")
public class Truck extends Vehicle {

    protected Truck(Double maxCapacity, Double mileage, String type)
    {
            super(maxCapacity, mileage, type);
    }

    @Override
    public boolean checkMileage(Double additionalDistance)
    {
        return (getMileage() + additionalDistance) < 100000d;
    };

    public Truck() {

    }
}
