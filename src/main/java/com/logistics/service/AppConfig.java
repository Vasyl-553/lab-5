package com.logistics.service;

import com.logistics.model.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Comparator;
import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public Comparator<Vehicle> fleetSorter() {
        return (v1, v2) -> {
            if (v1.isBroken() != v2.isBroken()) return Boolean.compare(v1.isBroken(), v2.isBroken());
            if (v1.isBusy() != v2.isBusy()) return Boolean.compare(v1.isBusy(), v2.isBusy());
            return Double.compare(v2.spaceAvailable(), v1.spaceAvailable());
        };
    }

    @Bean
    public Scanner Scanner()
    {
        return new Scanner(System.in);
    }
}