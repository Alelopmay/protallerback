package com.alejandroLopez.repository;

import com.alejandroLopez.model.File;
import com.alejandroLopez.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    // Consulta personalizada con JOIN entre File y Task usando JPQL
    @Query("SELECT f FROM File f JOIN Task t ON f.id = t.fileId WHERE t.employeeId = :employeeId AND f.archived = False")
    List<File> findArchivedFilesByEmployeeId(@Param("employeeId") Long employeeId);
    @Query("SELECT f FROM File f JOIN Task t ON f.id = t.fileId WHERE t.employeeId = :employeeId AND f.archived = true")
    List<File> findArchivedFilesByEmployeeIdFalse(@Param("employeeId") Long employeeId);


    // Consulta personalizada para obtener File junto con Car y Client usando JPQL
    // Consulta personalizada para obtener File junto con Car y Client usando JPQL
    @Query("SELECT f, c FROM File f " +
            "JOIN Car ca ON f.carLicensePlate = ca.licensePlate " +
            "JOIN ca.client_Id c WHERE f.id = :fileId")
    List<Object[]> findFileWithCarAndClientByFileId(@Param("fileId") Long fileId);
    // Nueva consulta para obtener las fechas de trabajo y los datos del empleado asociado a un fileId
    @Query("SELECT r.startDate, r.endDate, e.firstName, e.lastName " +
            "FROM Repair r " +
            "JOIN Task t ON t.employeeId = r.employee.id " +
            "JOIN Employee e ON e.id = t.employeeId " +
            "WHERE t.fileId = :fileId")
    List<Object[]> findWorkDatesAndEmployeeByFileId(@Param("fileId") Long fileId);

}