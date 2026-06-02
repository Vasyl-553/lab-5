package com.logistics.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PLANE")
public class Plane extends Vehicle{

    public Plane(Double maxCapacity, Double mileage, String type)
    {
        super(maxCapacity, mileage, type);
    }

    public Plane() {

    }

    public String getInfoForBroken()
    {
        return "Plane " + super.getMileage();

    }

    @Override
    public boolean checkMileage(Double additionalDistance)
    {
        return (getMileage() + additionalDistance) < 300000d;
    }
}
