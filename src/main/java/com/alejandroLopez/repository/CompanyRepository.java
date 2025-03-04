package com.alejandroLopez.repository;

import com.alejandroLopez.model.Company;
import com.alejandroLopez.model.Employee; // Asegúrate de que tienes el modelo Employee importado
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c.id FROM Company c JOIN Employee e ON c.id = e.company.id WHERE e.id = :employeeId")
    Optional<Long> findCompanyIdByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT c.id FROM Company c WHERE c.name = :name AND c.password = :password")
    Optional<Long> findIdByNameAndPassword(@Param("name") String name, @Param("password") String password);

    Optional<Company> findByName(String name);

    // Puedes agregar otros métodos personalizados si es necesario
    // Nueva consulta para obtener compañías por ubicación y radio
    @Query(value = "SELECT " +
            "c.id AS company_id, " +
            "c.name AS company_name, " +
            "c.address AS company_address, " +
            "c.schedule AS company_schedule, " +
            "ST_Distance(" +
            "    ST_SetSRID(c.location, 4326), " +
            "    ST_SetSRID(ST_GeogFromText('POINT(' || :longitude || ' ' || :latitude || ')'), 4326) " +
            ") AS distance " +
            "FROM " +
            "Company c " +
            "WHERE " +
            "ST_Distance(" +
            "    ST_SetSRID(c.location, 4326), " +
            "    ST_SetSRID(ST_GeogFromText('POINT(' || :longitude || ' ' || :latitude || ')'), 4326) " +
            ") <= :radius_in_meters " +
            "ORDER BY distance ASC", nativeQuery = true)
    List<Object[]> getAllCompaniesByLocation(@Param("latitude") double latitude,
                                             @Param("longitude") double longitude,
                                             @Param("radius_in_meters") double radiusInMeters);

    @Query("SELECT COUNT(cc) > 0 FROM CompanyClient cc WHERE cc.clientId = :clientId AND cc.companyId = :companyId")
    boolean existsByClientIdAndCompanyId(@Param("clientId") Long clientId, @Param("companyId") Long companyId);


}
