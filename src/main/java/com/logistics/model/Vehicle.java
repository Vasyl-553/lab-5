package com.logistics.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {
    private boolean isBusy;
    private boolean isBroken;
    private boolean isLoaded;
    private Double maxCapacity;
    private Double currentlyLoaded = 0d;
    private String type;
    private String ident;
    private Double mileage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Vehicle(Double maxCapacity, Double mileage, String type)
    {
        this.maxCapacity = maxCapacity;
        this.mileage = mileage;
        this.type = type;
        String random = UUID.randomUUID().toString().substring(0, 5);
        this.ident = type + '-' + random;
    }

    public Vehicle() {

    }

    public String[] getInfo() {
        String[] info =  new String[6];
        info[0] = type;
        info[1] = String.valueOf(isLoaded);
        info[2] = String.valueOf(isBroken);
        info[3] = String.valueOf(isBusy);
        info[4] = String.valueOf(mileage);
        info[5] = String.valueOf(spaceAvailable());
        return  info;
    }

    public String getId()
    {
        return ident;
    }

    protected Double getMileage()
    {
        return mileage;
    }

    public Double getCurrentlyLoaded() { return currentlyLoaded; }

    public boolean isBusy()
    {
        return isBusy;
    }

    public boolean isBroken()
    {
        return isBroken;
    }

    public boolean isLoaded() {return isLoaded; }

    public void markAsRepaired()
    {
        isBroken = false;
    }

    public void markAsBusy()
    {
        isBusy = true;
    }

    public void markAsNotBusy()
    {
        isBusy = false;
    }

    public void markAsLoaded() { isLoaded = true; }

    public void changeFreeSpace(Double volume)
    {
        if(volume == -1d) currentlyLoaded = 0d;
        else currentlyLoaded += volume;
    }


    public void changeMileage(Double distance)
    {
        mileage += distance;
    }

    public Double spaceAvailable()
    {
        return maxCapacity - currentlyLoaded;
    }

    public abstract boolean checkMileage(Double additionalDistance);

}
