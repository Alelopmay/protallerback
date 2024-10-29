package com.alejandroLopez.service;

import com.alejandroLopez.model.Employee;
import com.alejandroLopez.model.Company; // Asegúrate de importar la clase Company
import com.alejandroLopez.repository.EmployeeRepository;
import com.alejandroLopez.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Asegúrate de importar PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService; // Asegúrate de inyectar el servicio de Company
    private final PasswordEncoder passwordEncoder; // Inyectar PasswordEncoder
    private final TaskRepository taskRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CompanyService companyService, PasswordEncoder passwordEncoder, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Modificación del método para recibir el companyId como parámetro
    public Employee createEmployee(Employee employee, Long companyId) {
        Optional<Company> optionalCompany = companyService.getCompanyById(companyId);

        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            employee.setCompany(company);

            // Encriptar la contraseña del empleado antes de guardarlo
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));

            // Guardar el empleado en el repositorio
            return employeeRepository.save(employee);
        }

        throw new IllegalArgumentException("Company not found with id: " + companyId);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        if (employeeRepository.existsById(id)) {
            updatedEmployee.setId(id);
            return employeeRepository.save(updatedEmployee);
        }
        return null; // O lanzar una excepción si el empleado no existe
    }

    public List<Employee> getAllEmployeesByCompanyId(Long companyId) {
        return employeeRepository.findAllByCompanyIdOfEmployee(companyId);
    }
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id); // Asumiendo que tu repositorio tiene este método
    }
    public void archiveFileByTaskId(Long fileId) {
        taskRepository.archiveFileByTaskId(fileId);
    }
    public Employee createEmployeeAdmin(Employee employee) {
        // Validar que el empleado no sea nulo
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        // Asegúrate de que la compañía está asignada
        if (employee.getCompany() == null) { // Cambiamos esta línea
            throw new IllegalArgumentException("Company must be assigned to the employee");
        }

        // Verificar la existencia de la empresa usando el ID obtenido
        Optional<Company> optionalCompany = companyService.getCompanyById(employee.getCompany().getId());
        if (optionalCompany.isEmpty()) {
            throw new IllegalArgumentException("Company not found with id: " + employee.getCompany());
        }

        // Asignar la empresa al empleado
        employee.setCompany(optionalCompany.get());

        // Asignar rol de administrador
        employee.setRole("ADMIN");

        // Encriptar la contraseña del empleado antes de guardarlo
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        // Guardar el empleado en el repositorio
        return employeeRepository.save(employee);
    }
    public Optional<Employee> findByUsernameAndPassword(String username, String password) {
        return employeeRepository.findByUsernameAndPassword(username, password);
    }
    public Optional<Employee> findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

}
