package com.alejandroLopez.Controller;

import com.alejandroLopez.DTO.CompanyDTO;
import com.alejandroLopez.model.Company;
import com.alejandroLopez.service.CompanyService;
import com.alejandroLopez.UTILS.Encrypt; // Asegúrate de importar la clase Encrypt
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Obtener todas las empresas
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();

        // Convertir cada Company a CompanyDTO
        List<CompanyDTO> companyDTOs = companies.stream().map(company -> {
            Double latitude = null;
            Double longitude = null;

            if (company.getLocation() != null) {
                latitude = company.getLocation().getY();
                longitude = company.getLocation().getX();
            }

            return new CompanyDTO(
                    company.getId(),
                    company.getName(),
                    company.getAddress(),
                    latitude,
                    longitude,
                    company.getSchedule()
            );
        }).collect(Collectors.toList());
        System.out.println(companyDTOs);

        return ResponseEntity.ok(companyDTOs);
    }

    // Obtener empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva empresa
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        try {
            // Encriptar la contraseña antes de crear la empresa
            String encryptedPassword = Encrypt.Encrypt(companyDTO.getPassword());

            Company companyToCreate;

            // Validar si latitud y longitud están presentes
            if (companyDTO.getLatitude() != null && companyDTO.getLongitude() != null) {
                // Crear la empresa con latitud y longitud (ubicación)
                companyToCreate = new Company(
                        companyDTO.getName(),
                        encryptedPassword, // Usar contraseña encriptada
                        companyDTO.getAddress(),
                        companyDTO.getLatitude(),
                        companyDTO.getLongitude(),
                        companyDTO.getSchedule()
                );
            } else {
                // Crear la empresa sin ubicación
                companyToCreate = new Company(
                        companyDTO.getName(),
                        encryptedPassword, // Usar contraseña encriptada
                        companyDTO.getAddress(),
                        null, null, // Aquí las coordenadas son nulas si no se envían
                        companyDTO.getSchedule()
                );
            }

            // Guardar la empresa creada
            Company createdCompany = companyService.createOrUpdateCompany(companyToCreate);

            // Devolver el DTO con el ID de la empresa creada
            companyDTO.setId(createdCompany.getId());

            return ResponseEntity.ok(companyDTO);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Actualizar empresa
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Company updatedCompany = companyService.updateCompany(id, companyDetails);
        return ResponseEntity.ok(updatedCompany);
    }

    // Eliminar empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener empresa por nombre y contraseña
    @GetMapping("/getId")
    public ResponseEntity<Long> getCompanyByNameAndPassword(@RequestParam String name, @RequestParam String password) {
        // Encriptar la contraseña recibida para validarla
        try {
            String encryptedPassword = Encrypt.Encrypt(password);
            Optional<Long> companyId = companyService.getCompanyIdByNameAndPassword(name, encryptedPassword);

            if (companyId.isPresent()) {
                return ResponseEntity.ok(companyId.get());
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // Nueva función para obtener compañías cercanas por latitud, longitud y radio
    @GetMapping("/nearby")
    public ResponseEntity<List<Object[]>> getCompaniesNearby(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radiusInMeters) {

        try {
            // Llama al servicio para obtener las compañías cercanas
            List<Object[]> nearbyCompanies = companyService.findCompaniesNearby(latitude, longitude, radiusInMeters);

            // Verificar si se encontraron resultados
            if (nearbyCompanies.isEmpty()) {
                return ResponseEntity.noContent().build(); // Retorna 204 si no hay resultados
            }

            return ResponseEntity.ok(nearbyCompanies); // Retorna 200 OK con la lista de compañías
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
