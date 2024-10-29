package com.alejandroLopez.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Form")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    private java.sql.Date date;

    @Column(columnDefinition = "TEXT")
    private String initialDiagnosis;

    @Column(columnDefinition = "TEXT")
    private String inspectionResults;

    @Column(columnDefinition = "TEXT")
    private String workPerformed;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "car_license_plate", referencedColumnName = "license_plate")
    private Car car;

    // Constructor vacío
    public Form() {}

    // Constructor completo
    public Form(String description, java.sql.Date date, String initialDiagnosis, String inspectionResults, String workPerformed, Employee employee, Car car) {
        this.description = description;
        this.date = date;
        this.initialDiagnosis = initialDiagnosis;
        this.inspectionResults = inspectionResults;
        this.workPerformed = workPerformed;
        this.employee = employee;
        this.car = car;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getInitialDiagnosis() {
        return initialDiagnosis;
    }

    public void setInitialDiagnosis(String initialDiagnosis) {
        this.initialDiagnosis = initialDiagnosis;
    }

    public String getInspectionResults() {
        return inspectionResults;
    }

    public void setInspectionResults(String inspectionResults) {
        this.inspectionResults = inspectionResults;
    }

    public String getWorkPerformed() {
        return workPerformed;
    }

    public void setWorkPerformed(String workPerformed) {
        this.workPerformed = workPerformed;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", initialDiagnosis='" + initialDiagnosis + '\'' +
                ", inspectionResults='" + inspectionResults + '\'' +
                ", workPerformed='" + workPerformed + '\'' +
                ", employee=" + employee +
                ", car=" + car +
                '}';
    }
}