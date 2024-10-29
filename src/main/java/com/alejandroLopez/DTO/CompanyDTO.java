package com.alejandroLopez.DTO;

public class CompanyDTO {
    private Long id;
    private String name;
    private String password;
    private String address;
    private Double latitude;
    private Double longitude;
    private String schedule;

    // Constructor vacío
    public CompanyDTO() {}

    // Constructor completo
    public CompanyDTO(Long id, String name, String password, String address, Double latitude, Double longitude, String schedule) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.schedule = schedule;
    }

    public CompanyDTO(Long id, String name, String address, Double latitude, Double longitude, String schedule) {
        this.id = id;
        this.name = name;

        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.schedule = schedule;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
