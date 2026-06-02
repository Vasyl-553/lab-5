package com.logistics.service;

import com.logistics.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargoManager {

    private List<Cargo> warehouse = new ArrayList<>();

    public void setWarehouse(List<Cargo> warehouse) {
        this.warehouse = warehouse;
    }

    public List<Cargo> getWarehouse() {
        return warehouse;
    }

    public void addCargo(Cargo cargo) {
        warehouse.add(cargo);
    }
}
