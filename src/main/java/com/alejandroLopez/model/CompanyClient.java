package com.alejandroLopez.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Company_Client")
@IdClass(CompanyClientId.class)  // Clase para la clave compuesta
public class CompanyClient {

    @Id
    private Long clientId;

    @Id
    private Long companyId;

    // Constructor vacío
    public CompanyClient() {}

    // Constructor completo
    public CompanyClient(Long clientId, Long companyId) {
        this.clientId = clientId;
        this.companyId = companyId;
    }

    // Getters y Setters
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyClient{" +
                "clientId=" + clientId +
                ", companyId=" + companyId +
                '}';
    }
}