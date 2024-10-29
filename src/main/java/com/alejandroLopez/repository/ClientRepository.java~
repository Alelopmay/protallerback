package com.alejandroLopez.repository;

import com.alejandroLopez.model.Car;
import com.alejandroLopez.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c FROM Client c JOIN CompanyClient cc ON c.id = cc.clientId " +
            "WHERE cc.companyId = (SELECT e.company.id FROM Employee e WHERE e.id = :employeeId)")
    List<Client> findAllClientsByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT car FROM Car car WHERE car.client_Id.phoneNumber = :phoneNumber AND car.client_Id.email = :email")
    List<Car> findAllCarsByClientPhoneNumberAndEmail(@Param("phoneNumber") String phoneNumber, @Param("email") String email);

    @Query("SELECT c FROM Car c WHERE c.client_Id.email = :email")
    List<Car> findAllCarsByClientEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c WHERE c.phoneNumber = :phoneNumber AND c.email = :email")
    Optional<Client> findClientByPhoneNumberAndEmail(@Param("phoneNumber") String phoneNumber, @Param("email") String email);



}
