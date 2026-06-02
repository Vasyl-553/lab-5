package com.logistics.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TRAIN")
public class Train extends Vehicle {

    public Train(Double maxCapacity, Double mileage, String type) {
        super(maxCapacity, mileage, type);
    }

    @Override
    public boolean checkMileage(Double additionalDistance)
    {
        return (getMileage() + additionalDistance) < 1000000d;
    }

    public Train() {

    }
}

