package com.alejandroLopez.model;

import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "license_plate", length = 50, nullable = false)
    private String licensePlate;

    @Column(name = "model", length = 100, nullable = false)
    private String model;

    @Column(name = "car_condition", length = 50, nullable = false)
    private String carCondition;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client_Id;

    // Constructor vacío
    public Car() {}

    // Constructor completo
    public Car(String licensePlate, String model, String carCondition, Client client) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.carCondition = carCondition;
        this.client_Id = client;
    }

    // Getters y Setters
    // ...

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }

    public Client getClient() {
        return client_Id;
    }

    public void setClient(Client client) {
        this.client_Id = client;
    }

    @Override
    public String toString() {
        return "Car{" +
                "licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                ", carCondition='" + carCondition + '\'' +
                ", client=" + client_Id +
                '}';
    }
}
