package com.alejandroLopez.Controller;

import com.alejandroLopez.model.Repair;
import com.alejandroLopez.model.Car;
import com.alejandroLopez.model.Employee;
import com.alejandroLopez.service.RepairService;
import com.alejandroLopez.service.CarService;
import com.alejandroLopez.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/repairs")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @Autowired
    private CarService carService;

    @Autowired
    private EmployeeService employeeService;

    // Crear un nuevo repair
    @PostMapping
    public ResponseEntity<Repair> createRepair(@RequestBody Repair repair) {
        // Imprimir los datos de la reparación recibida
        System.out.println("Datos de la reparación recibidos: " + repair);

        // Verificar las fechas antes de guardarlas
        System.out.println("Start Date: " + repair.getStartDate());
        System.out.println("End Date: " + repair.getEndDate());

        // (Continúa con el resto de la lógica de tu método)

        // Crear nueva reparación
        try {
            Repair newRepair = repairService.createRepair(repair);
            return new ResponseEntity<>(newRepair, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo de excepciones si la inserción falla
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Devuelve 500 en caso de error interno
        }
    }


    // Otros métodos del controlador...
}
