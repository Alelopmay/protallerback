package com.alejandroLopez.repository;

import com.alejandroLopez.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    // Consulta nativa para obtener todos los coches de una empresa a través de los clientes
    @Query(value = "SELECT c.license_plate, c.model, c.car_condition " +
            "FROM Car c " +
            "JOIN Client cl ON c.client_id = cl.id " +
            "JOIN Company_Client cc ON cl.id = cc.client_id " +
            "JOIN Company com ON cc.company_id = com.id " +
            "JOIN Employee e ON e.company_id = com.id " +
            "WHERE e.id = :employeeId", nativeQuery = true)
    List<Object[]> findAllCarsByEmployeeId(@Param("employeeId") Long employeeId);

    // Nueva función para obtener todos los coches de un cliente específico
    @Query(value = "SELECT c.id, c.license_plate, c.model, c.car_condition " +
            "FROM Car c " +
            "WHERE c.client_id = :clientId", nativeQuery = true)
    List<Object[]> findAllCarsByClientId(@Param("clientId") Long clientId);

    // Nueva función para obtener todos los formularios de un coche
    @Query(value = "SELECT f.id, f.description, f.date, f.initial_diagnosis, f.inspection_results, f.work_performed " +
            "FROM Form f " +
            "JOIN Car c ON f.car_license_plate = c.license_plate " +
            "WHERE c.license_plate = :licensePlate", nativeQuery = true)
    List<Object[]> findAllFormsByCarLicensePlate(@Param("licensePlate") String licensePlate);

    // Nueva función para obtener el nombre del cliente de un coche
    // Nueva función para obtener el cliente de un coche
    @Query(value = "SELECT cl.name, cl.phone_number, cl.email " +
            "FROM Client cl " +
            "JOIN Car c ON cl.id = c.client_id " +
            "WHERE c.license_plate = :licensePlate", nativeQuery = true)
    List<Object[]> findClientByCarLicensePlate(@Param("licensePlate") String licensePlate);


    // Nueva función para obtener las facturas de un coche
    @Query(value = "SELECT i.id AS invoice_id, i.subtotal, i.issue_date, i.vat, i.total, i.payment_method, i.warranty " +
            "FROM Invoice i " +
            "JOIN Car c ON i.car_license_plate = c.license_plate " +
            "WHERE c.license_plate = :licensePlate", nativeQuery = true)
    List<Object[]> findInvoicesByCarLicensePlate(@Param("licensePlate") String licensePlate);

    // Consulta para obtener formularios del coche por empresa
    @Query(value = "SELECT f.id, f.description, f.date, f.initial_diagnosis, f.inspection_results, f.work_performed " +
            "FROM Form f " +
            "JOIN Car c ON f.car_license_plate = c.license_plate " +
            "JOIN Employee e ON f.employee_id = e.id " +
            "WHERE c.license_plate = :licensePlate " +
            "AND e.company_id = (SELECT company_id FROM Employee WHERE id = :employeeId)", nativeQuery = true)
    List<Object[]> findAllFormsByCarLicensePlateAndCompany(@Param("licensePlate") String licensePlate, @Param("employeeId") int employeeId);

    // Consulta para obtener facturas del coche por empresa
    @Query(value = "SELECT i.id AS invoice_id, i.subtotal, i.issue_date, i.vat, i.total, i.payment_method, i.warranty " +
            "FROM Invoice i " +
            "JOIN Car c ON i.car_license_plate = c.license_plate " +
            "JOIN Employee e ON i.car_license_plate = c.license_plate " +  // Esto no es correcto, se necesita un join correcto
            "WHERE c.license_plate = :licensePlate " +
            "AND e.company_id = (SELECT company_id FROM Employee WHERE id = :employeeId)", nativeQuery = true)
    List<Object[]> findInvoicesByCarLicensePlateAndCompany(@Param("licensePlate") String licensePlate, @Param("employeeId") int employeeId);

}