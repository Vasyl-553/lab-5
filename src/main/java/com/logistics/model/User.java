package com.logistics.model;

import jakarta.persistence.*;
import java.util.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    String surname;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "finances_id")
    private Finances finances;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Vehicle> fleet = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Cargo> warehouse = new ArrayList<>();

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public Finances getFinances() { return finances; }
    public void setFinances(Finances finances) { this.finances = finances; }

    public List<Vehicle> getFleet() { return fleet; }
    public void setFleet(List<Vehicle> fleet) { this.fleet = fleet; }

    public List<Cargo> getWarehouse() { return warehouse; }
    public void setWarehouse(List<Cargo> warehouse) { this.warehouse = warehouse; }

    public User() {}
}
