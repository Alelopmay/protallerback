package com.alejandroLopez.Controller;

import com.alejandroLopez.model.Company;
import com.alejandroLopez.model.Employee;
import com.alejandroLopez.service.CompanyService;
import com.alejandroLopez.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Value("${jwt.secret}") // Esta línea inyecta el secreto desde el archivo de configuración
    private String jwtSecret; // Asegúrate de que este campo esté definido

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final CompanyService companyService;  // Añadimos el servicio de empresa


    @Autowired
    public EmployeeController(EmployeeService employeeService, PasswordEncoder passwordEncoder, CompanyService companyService) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<Employee>> getAllEmployeesByCompanyId(@PathVariable Long id) {
        List<Employee> employees = employeeService.getAllEmployeesByCompanyId(id);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping // Acepta solicitudes POST en /employees
    public ResponseEntity<Map<String, String>> createEmployee(@RequestBody Employee employee) {
        try {
            // Verifica que el objeto empleado no sea nulo
            if (employee == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Employee is null."));
            }

            // Verificar que el companyId no sea nulo y que la compañía exista
            if (employee.getCompany() == null || employee.getCompany().getId() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Company ID is null."));
            }

            // Validar que la empresa exista
            Optional<Company> company = companyService.getCompanyById(employee.getCompany().getId());
            if (company.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Company with ID " + employee.getCompany().getId() + " does not exist."));
            }

            // Asignar la empresa al empleado
            employee.setCompany(company.get());

            // Manejo de la foto en Base64
            if (employee.getPhoto() != null && employee.getPhoto().length > 0) {
                try {
                    // Aquí no es necesario decodificar, ya que 'photo' ya es byte[]
                    System.out.println("Photo size: " + employee.getPhoto().length);
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid Base64 format for photo."));
                }
            }

            // Guardar el empleado
            employeeService.createEmployee(employee, employee.getCompany().getId());

            // Devolver una respuesta JSON con un mensaje de éxito
            return ResponseEntity.ok(Map.of("message", "Empleado creado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al crear el empleado: " + e.getMessage()));
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        return employee != null ? new ResponseEntity<>(employee, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/archive-file/{fileId}")
    public ResponseEntity<Map<String, Object>> archiveFile(@PathVariable Long fileId) {
        Map<String, Object> response = new HashMap<>();
        try {
            employeeService.archiveFileByTaskId(fileId);
            response.put("success", true);
            response.put("message", "Archivo archivado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al archivar el archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/admin")
    public ResponseEntity<Map<String, String>> createEmployeeAdmin(@RequestBody Map<String, Object> request) {
        try {
            // Extraer los datos del cuerpo de la solicitud
            Employee employee = new ObjectMapper().convertValue(request.get("employee"), Employee.class);
            String companyName = (String) request.get("companyName");
            String companyPassword = (String) request.get("companyPassword");

            // Validar que los campos requeridos no sean nulos
            if (employee == null ||
                    employee.getFirstName() == null ||
                    employee.getLastName() == null ||
                    employee.getUsername() == null ||
                    employee.getPassword() == null ||
                    companyName == null ||
                    companyPassword == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields."));
            }

            // Aquí se obtiene el ID de la compañía usando el nombre y la contraseña
            Optional<Long> companyIdOptional = companyService.getCompanyIdByNameAndPassword(companyName, companyPassword);
            if (companyIdOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Company not found or incorrect password."));
            }

            Long companyId = companyIdOptional.get();

            // Obtener la compañía y asignarla al empleado
            Optional<Company> companyOptional = companyService.getCompanyById(companyId);
            if (companyOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Company not found."));
            }

            employee.setCompany(companyOptional.get()); // Asigna el objeto Company al empleado

            // Crear el empleado administrador
            employeeService.createEmployeeAdmin(employee);

            // Devolver una respuesta JSON con un mensaje de éxito
            return ResponseEntity.ok(Map.of("message", "Admin employee created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error creating admin employee: " + e.getMessage()));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        Map<String, Object> response = new HashMap<>();

        // Buscar el empleado por nombre de usuario
        Optional<Employee> employeeOptional = employeeService.findByUsername(username);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            // Verificar la contraseña hasheada
            if (passwordEncoder.matches(password, employee.getPassword())) {
                // Generar un token (puedes usar JWT o cualquier otra forma de autenticación)
                String token = generateToken(employee); // Implementa este método para generar el token

                response.put("success", true);
                response.put("token", token); // Enviar el token en la respuesta
                return ResponseEntity.ok(response);
            }
        }

        response.put("success", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    private String generateToken(Employee employee) {
        // Suponiendo que Employee tiene un método getCompanyId() que devuelve el ID de la empresa
        Long companyId = employee.getCompany().getId(); // Obtén el ID de la empresa del empleado

        return Jwts.builder()
                .setSubject(employee.getId().toString())
                .claim("companyId", companyId) // Agrega el ID de la empresa como un claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día de expiración
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }






}
