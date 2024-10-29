package com.alejandroLopez.repository;

import com.alejandroLopez.model.CompanyClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyClientRepository extends JpaRepository<CompanyClient, Long> {
    @Query("SELECT COUNT(cc) > 0 FROM CompanyClient cc WHERE cc.clientId = :clientId AND cc.companyId = :companyId")
    boolean existsByClientIdAndCompanyId(@Param("clientId") Long clientId, @Param("companyId") Long companyId);

    // Aquí puedes agregar métodos adicionales si es necesario
}
