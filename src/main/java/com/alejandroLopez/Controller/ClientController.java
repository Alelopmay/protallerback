package com.alejandroLopez.Controller;

import com.alejandroLopez.UTILS.EmailService;
import com.alejandroLopez.model.Car;
import com.alejandroLopez.model.Client;
import com.alejandroLopez.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private ClientService clientService;

    // Obtener todos los clientes
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo cliente
    // Crear un nuevo cliente o establecer la relación con la empresa si ya existe
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client, @RequestParam Long employeeId) {
        // Paso 1: Verificar si el cliente ya existe por los atributos únicos (email y teléfono)
        Optional<Client> existingClientOpt = clientService.findClientByPhoneNumberAndEmail(client.getPhoneNumber(), client.getEmail());

        if (existingClientOpt.isPresent()) {
            Client existingClient = existingClientOpt.get();

            // Paso 2: Verificar si el cliente ya tiene una relación con la empresa
            boolean relationExists = clientService.existsByClientIdAndCompanyId(existingClient.getId(), employeeId);

            if (relationExists) {
                // Cliente ya está registrado y ya tiene la relación con la empresa
                return new ResponseEntity<>(HttpStatus.CONFLICT);  // Retornar 409 Conflict
            } else {
                // Cliente ya existe pero no tiene la relación, crearla
                clientService.createClientCompanyRelation(existingClient.getId(), employeeId);
                return new ResponseEntity<>(existingClient, HttpStatus.OK);  // Retornar 200 OK
            }
        } else {
            // Cliente no existe, crear uno nuevo
            Client createdClient = clientService.createClient(client);

            // Crear la relación entre el cliente y la empresa
            clientService.createClientCompanyRelation(createdClient.getId(), employeeId);

            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);  // Retornar 201 Created
        }
    }


    // Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client updatedClient = clientService.updateClient(id, clientDetails);
        return ResponseEntity.ok(updatedClient);
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<Client>> getClientsByEmployeeId(@PathVariable Long employeeId) {
        List<Client> clients = clientService.getAllClientsByEmployeeId(employeeId);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCarsByClientPhoneNumberAndEmail(
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email) {

        List<Car> cars = clientService.getCarsByClientPhoneNumberAndEmail(phoneNumber, email);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(cars);
        }
    }



    // Endpoint para confirmar el acceso a los datos

}

