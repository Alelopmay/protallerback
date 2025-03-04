package com.alejandroLopez.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.PrecisionModel;

@Entity
@Table(name = "Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 255)
    private String address;

    // Campo para la localización usando Geometry
    @Column(name = "location", columnDefinition = "Geometry(Point,4326)", nullable = true)
    private Point location;

    // Campo para el horario de la semana como un String
    @Column(name = "schedule", length = 255)
    private String schedule; // Ejemplo: "Lunes: 9am-5pm, Martes: 9am-5pm, ..."

    // Constructor vacío
    public Company() {}

    // Constructor completo
// Constructor completo
    public Company(String name, String password, String address, Double lat, Double lng, String schedule) {
        this.name = name;
        this.password = password;
        this.address = address;

        if (lat != null && lng != null) {
            // Crear el Point usando las coordenadas si no son null
            GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
            this.location = geometryFactory.createPoint(new Coordinate(lng, lat));
        } else {
            this.location = null; // Dejar la ubicación como null si no se envían coordenadas
        }

        this.schedule = schedule;
    }


    public Company(Long companyId) {
        // Constructor adicional si se requiere
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", schedule='" + schedule + '\'' +
                '}';
    }
}
