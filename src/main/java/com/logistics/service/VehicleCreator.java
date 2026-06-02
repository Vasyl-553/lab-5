package com.logistics.service;

import com.logistics.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleCreator {
    Map<Integer, IVehicle> vehicleMap = new HashMap<>();

    public VehicleCreator()
    {
        vehicleMap.put(1, (cap, mil, typ) -> new TankerTruck(cap, mil, typ));
        vehicleMap.put(2, (cap, mil, typ) -> new ContainerTruck(cap, mil, typ));
        vehicleMap.put(3, (cap, mil, typ) -> new Plane(cap, mil, typ));
        vehicleMap.put(4, (cap, mil, typ) -> new Train(cap, mil, typ));
    }

    public Vehicle create(int type, double mileage, String label)
    {
        double capacity = 0;
        switch(type)
        {
            case 1, 2 -> capacity = 100000;
            case 3 -> capacity = 300000;
            case 4 -> capacity = 1000000;
        }
        IVehicle factory = vehicleMap.get(type);

        return factory.create(capacity, mileage, label);
    }
}
