package com.logistics.service;

import com.logistics.model.*;

public class Repairing {
    public static void repair(Vehicle brokenVehicle)
    {
        brokenVehicle.markAsRepaired();
    }
}

