package com.logistics.model;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Finances {
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Double> moneyToBeEarned = new HashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ident;

    private Double earnings = 0d;
    private final Double tariff = 1.4d;
    private Integer numberOfSuccessfulDeliveries = 0;

    public double getEarnings()
    {
        return earnings;
    }

    public Integer getNumberOfSuccessfulDeliveries()
    {
        return numberOfSuccessfulDeliveries;
    }

    public Double getMoneyToBeEarned(String id)
    {
        return moneyToBeEarned.get(id);
    }

    public void addToMoneyToBeEarned(Double distance, Double volume, String id)
    {
        moneyToBeEarned.put(id, calculateEarnings(distance, volume));
    }

    public void addToEarnings(String id)
    {
        earnings += moneyToBeEarned.get(id);
        moneyToBeEarned.remove(id);
    }

    public void addSuccessfulDelivery()
    {
        numberOfSuccessfulDeliveries++;
    }

    public Double calculateEarnings(Double distance, Double volume)
    {
        return ((distance + volume) * tariff);
    }
}
