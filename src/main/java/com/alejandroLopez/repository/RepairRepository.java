package com.alejandroLopez.repository;

import com.alejandroLopez.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    Optional<Repair> findByCarLicensePlateAndEmployeeId(String carLicensePlate, Long employeeId);
}
