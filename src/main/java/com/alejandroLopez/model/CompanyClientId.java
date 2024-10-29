package com.alejandroLopez.model;

import java.io.Serializable;
import java.util.Objects;

public class CompanyClientId implements Serializable {
    private Long clientId;
    private Long companyId;

    // Constructor vacío
    public CompanyClientId() {}

    // Constructor completo
    public CompanyClientId(Long clientId, Long companyId) {
        this.clientId = clientId;
        this.companyId = companyId;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyClientId)) return false;

        CompanyClientId that = (CompanyClientId) o;
        return clientId.equals(that.clientId) && companyId.equals(that.companyId);
    }

    @Override
    public int hashCode() {
        return 31 * clientId.hashCode() + companyId.hashCode();
    }
}