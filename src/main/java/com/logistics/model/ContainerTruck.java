package com.logistics.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONTAINER_TRUCK")
public class ContainerTruck extends Truck{
    public ContainerTruck(Double maxCapacity, Double mileage, String type)
    {
        super(maxCapacity, mileage, type);
    }

    public ContainerTruck() {

    }
}
