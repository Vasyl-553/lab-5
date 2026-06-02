package com.logistics.service;

import com.logistics.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class VehicleManager {
    private List<Vehicle> fleet = new ArrayList<>();
    private final List<String> types = new ArrayList<>(List.of("TANKER_TRUCK",  "CONTAINER_TRUCK", "PLANE", "TRAIN"));

    public void setFleet(List<Vehicle> fleet) {
        this.fleet = fleet;
    }

    public List<Vehicle> getFleet() {
        return fleet;
    }

    public String getType(int index) { return types.get(index - 1); }

    public void sortFleet(Comparator<Vehicle> sorter) {
        fleet.sort(sorter);
    }

    public void addVehicle(Vehicle vehicle) {
        fleet.add(vehicle);
    }

    public List<Vehicle> findLoaded(List<Vehicle> vehicles) {
        List<Vehicle> loadedVehicles = new ArrayList<>();

        for(var vehicle : vehicles) {
            if(vehicle.isLoaded() && !vehicle.isBusy()) {
                loadedVehicles.add(vehicle);
            }
        }
        return loadedVehicles;
    }
}
