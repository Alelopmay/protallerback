package com.alejandroLopez.service;

import com.alejandroLopez.model.Company;
import com.alejandroLopez.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectando el PasswordEncoder

    // Obtener todas las empresas
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Obtener empresa por ID
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    // Crear una nueva empresa
    public Company createCompany(Company company) {
        // Hashear la contraseña antes de guardarla
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        return companyRepository.save(company);
    }

    // Actualizar empresa
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setName(companyDetails.getName());

        // Solo actualizar la contraseña si se proporciona una nueva
        if (companyDetails.getPassword() != null && !companyDetails.getPassword().isEmpty()) {
            company.setPassword(passwordEncoder.encode(companyDetails.getPassword()));
        }

        company.setAddress(companyDetails.getAddress());
        return companyRepository.save(company);
    }

    // Eliminar empresa
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    // Método para obtener el ID de la empresa por nombre y contraseña
    public Optional<Long> getCompanyIdByNameAndPassword(String name, String password) {
        Optional<Company> company = companyRepository.findByName(name);
        return company.filter(c -> passwordEncoder.matches(password, c.getPassword()))
                .map(Company::getId);
    }

    // Método para obtener el ID de la empresa por ID de empleado

    public Optional<Long> getCompanyIdByEmployeeId(Long employeeId) {
        // Aquí va la lógica para obtener el companyId a partir del employeeId
        // Ejemplo:
        return companyRepository.findCompanyIdByEmployeeId(employeeId);
    }
    // Método para crear o actualizar una empresa
    public Company createOrUpdateCompany(Company company) {
        Company existingCompany = null;

        try {
            // Validar que la empresa no existe si es necesario
            if (company.getId() != null) {
                existingCompany = companyRepository.findById(company.getId()).orElse(null);
            }

            if (existingCompany != null) {
                // Actualizar la empresa existente
                existingCompany.setName(company.getName());
                existingCompany.setPassword(company.getPassword());
                existingCompany.setAddress(company.getAddress());
                existingCompany.setLocation(company.getLocation()); // Establecer la ubicación como Point
                existingCompany.setSchedule(company.getSchedule());
                return companyRepository.save(existingCompany);
            } else {
                // Crear una nueva empresa
                return companyRepository.save(company);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating/updating company", e);
        }
    }
    public List<Object[]> findCompaniesNearby(double latitude, double longitude, double radiusInMeters) {
        return companyRepository.getAllCompaniesByLocation(latitude, longitude, radiusInMeters);
    }

}
