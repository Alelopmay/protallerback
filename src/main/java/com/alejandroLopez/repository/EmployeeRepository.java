package com.alejandroLopez.repository;

import com.alejandroLopez.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.company.id = (SELECT e2.company.id FROM Employee e2 WHERE e2.id = :employeeId)")
    List<Employee> findAllByCompanyIdOfEmployee(@Param("employeeId") Long employeeId);
    @Query("SELECT e FROM Employee e WHERE e.username = :username AND e.password = :password")
    Optional<Employee> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    Optional<Employee> findByUsername(String username);

}
