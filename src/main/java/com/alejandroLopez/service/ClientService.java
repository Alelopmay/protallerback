package com.alejandroLopez.service;

import com.alejandroLopez.model.Car;
import com.alejandroLopez.model.Client;
import com.alejandroLopez.model.CompanyClient;
import com.alejandroLopez.repository.ClientRepository;
import com.alejandroLopez.repository.CompanyClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyClientRepository companyClientRepository;

    // Almacena tokens por email para la confirmación
    private Map<String, String> tokenStorage = new HashMap<>();

    // Obtener todos los clientes
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Obtener cliente por ID
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // Crear un nuevo cliente
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    // Actualizar cliente
    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        client.setName(clientDetails.getName());
        client.setPhoneNumber(clientDetails.getPhoneNumber());
        client.setEmail(clientDetails.getEmail());
        return clientRepository.save(client);
    }

    // Eliminar cliente
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // Obtener todos los clientes asociados a un empleado
    public List<Client> getAllClientsByEmployeeId(Long employeeId) {
        return clientRepository.findAllClientsByEmployeeId(employeeId);
    }

    // Crear o vincular un cliente a una empresa
    public Client createOrLinkClient(Client client, Long employeeId) {
        // Verificar si el cliente ya existe por su número de teléfono y correo electrónico
        Optional<Client> existingClientOpt = findClientByPhoneNumberAndEmail(client.getPhoneNumber(), client.getEmail());

        if (existingClientOpt.isPresent()) {
            Client existingClient = existingClientOpt.get();
            Optional<Long> companyIdOpt = companyService.getCompanyIdByEmployeeId(employeeId);

            if (companyIdOpt.isPresent()) {
                Long companyId = companyIdOpt.get();
                boolean alreadyRelated = existsByClientIdAndCompanyId(existingClient.getId(), companyId);

                if (alreadyRelated) {
                    throw new RuntimeException("El cliente ya está asociado a la empresa.");
                } else {
                    CompanyClient companyClient = new CompanyClient(existingClient.getId(), companyId);
                    companyClientRepository.save(companyClient);
                    System.out.println("Relación N:M creada entre Cliente ID: " + existingClient.getId() + " y Empresa ID: " + companyId);
                    return existingClient;
                }
            } else {
                throw new RuntimeException("No se encontró ninguna empresa asociada con el ID de empleado: " + employeeId);
            }

        } else {
            Client createdClient = clientRepository.save(client);
            createClientCompanyRelation(createdClient.getId(), employeeId);
            return createdClient;
        }
    }

    // Crear la relación N:M entre cliente y empresa
    public void createClientCompanyRelation(Long clientId, Long employeeId) {
        Optional<Long> companyIdOpt = companyService.getCompanyIdByEmployeeId(employeeId);

        if (companyIdOpt.isPresent()) {
            Long companyId = companyIdOpt.get();
            CompanyClient companyClient = new CompanyClient(clientId, companyId);
            companyClientRepository.save(companyClient);
            System.out.println("Relación N:M creada entre Cliente ID: " + clientId + " y Empresa ID: " + companyId);
        } else {
            throw new RuntimeException("No se encontró ninguna empresa asociada con el ID de empleado: " + employeeId);
        }
    }

    // 1. Buscar cliente por número de teléfono y correo electrónico
    public Optional<Client> findClientByPhoneNumberAndEmail(String phoneNumber, String email) {
        return clientRepository.findClientByPhoneNumberAndEmail(phoneNumber, email);
    }

    // 2. Verificar si un cliente está vinculado a una empresa
    public boolean existsByClientIdAndCompanyId(Long clientId, Long companyId) {
        return companyClientRepository.existsByClientIdAndCompanyId(clientId, companyId);
    }

    // Verificar el token de confirmación
    public boolean verifyConfirmationToken(String email, String token) {
        return tokenStorage.containsKey(email) && tokenStorage.get(email).equals(token);
    }

    // Obtener coches del cliente basado en su número de teléfono y correo electrónico
    public List<Car> getCarsByClientPhoneNumberAndEmail(String phoneNumber, String email) {
        return clientRepository.findAllCarsByClientPhoneNumberAndEmail(phoneNumber, email);
    }

    // Crear un token de confirmación y enviarlo por correo
    public String createAndSendConfirmationToken(String email) {
        String token = UUID.randomUUID().toString();
        tokenStorage.put(email, token);
        // emailService.sendConfirmationEmail(email, token);  // Método de envío de email
        return token;
    }

    // Eliminar un cliente basado en su ID
    public void deleteClientById(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
