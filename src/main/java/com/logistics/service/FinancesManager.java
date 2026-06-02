package com.logistics.service;

import com.logistics.model.*;
import org.springframework.stereotype.Service;

@Service
public class FinancesManager {

    public Finances finances = new Finances();

    public void setFinances(Finances finances) {
        this.finances = finances;
    }

    public Finances getFinances() {
        return finances;
    }
}
