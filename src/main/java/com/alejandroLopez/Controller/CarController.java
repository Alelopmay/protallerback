package com.alejandroLopez.Controller;

import com.alejandroLopez.model.Car;
import com.alejandroLopez.model.Client; // Correct import of your Client class
import com.alejandroLopez.service.CarService;
import com.alejandroLopez.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final ClientService clientService; // Correct variable name

    @Autowired
    public CarController(CarService carService, ClientService clientService) { // Correct constructor argument name
        this.carService = carService;
        this.clientService = clientService; // Correct assignment
    }

    // Obtener todos los autos
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // Obtener autos por ID de la empresa
    @GetMapping("/employee/{employeeId}/cars")
    public ResponseEntity<List<Object[]>> getCarsByEmployeeId(@PathVariable Long employeeId) {
        List<Object[]> cars = carService.getCarsByEmployeeId(employeeId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    // Obtener auto por matrícula (licensePlate)
    @GetMapping("/{licensePlate}")
    public ResponseEntity<Car> getCarByLicensePlate(@PathVariable String licensePlate) {
        return carService.getCarByLicensePlate(licensePlate)
                .map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Agregar o actualizar un auto
    // Agregar o actualizar un auto
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Map<String, Object> carData) {
        String licensePlate = (String) carData.get("licensePlate");

        // Verificar si el coche ya existe
        if (carService.existsByLicensePlate(licensePlate)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
        }

        String model = (String) carData.get("model");
        String carCondition = (String) carData.get("carCondition");
        Long clientId = ((Number) carData.get("clientId")).longValue();  // Convertir el clientId a Long

        // Buscar el cliente en la base de datos
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setModel(model);
        car.setCarCondition(carCondition);
        car.setClient(client); // Set the found client directly

        Car savedCar = carService.saveCar(car);
        return ResponseEntity.ok(savedCar);
    }



    // Eliminar un auto por matrícula
    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<Void> deleteCar(@PathVariable String licensePlate) {
        if (carService.existsByLicensePlate(licensePlate)) {
            carService.deleteCar(licensePlate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Object[]>> getCarsByClientId(@PathVariable Long clientId) {
        System.out.println(clientId);
        List<Object[]> cars = carService.getCarsByClientId(clientId);
        System.out.println(cars);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // Obtener todos los formularios de un coche
    @GetMapping("/{licensePlate}/forms")
    public ResponseEntity<List<Object[]>> getFormsByCarLicensePlate(@PathVariable String licensePlate) {
        List<Object[]> forms = carService.getFormsByCarLicensePlate(licensePlate);
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    // Obtener el nombre del cliente de un coche
// Obtener el cliente por matrícula (licensePlate)
    @GetMapping("/{licensePlate}/client")
    public ResponseEntity<List<Object[]>> getClientByCarLicensePlate(@PathVariable String licensePlate) {
        List<Object[]> clientData = carService.getClientByCarLicensePlate(licensePlate);
        if (clientData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientData, HttpStatus.OK);
    }


    // Obtener todas las facturas de un coche
    @GetMapping("/{licensePlate}/invoices")
    public ResponseEntity<List<Object[]>> getInvoicesByCarLicensePlate(@PathVariable String licensePlate) {
        List<Object[]> invoices = carService.getInvoicesByCarLicensePlate(licensePlate);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }
    // Obtener formularios de un coche según el ID del empleado
    @GetMapping("/{licensePlate}/forms/employee/{employeeId}")
    public ResponseEntity<List<Object[]>> getFormsByCarLicensePlateAndEmployeeId(@PathVariable String licensePlate, @PathVariable Long employeeId) {
        List<Object[]> forms = carService.getFormsByCarLicensePlateAndEmployeeId(licensePlate, employeeId);
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    // Obtener facturas de un coche según el ID del empleado
    @GetMapping("/{licensePlate}/invoices/employee/{employeeId}")
    public ResponseEntity<List<Object[]>> getInvoicesByCarLicensePlateAndEmployeeId(@PathVariable String licensePlate, @PathVariable Long employeeId) {
        List<Object[]> invoices = carService.getInvoicesByCarLicensePlateAndEmployeeId(licensePlate, employeeId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

}
