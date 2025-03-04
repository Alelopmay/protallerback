package com.alejandroLopez.service;

import com.alejandroLopez.model.Car;
import com.alejandroLopez.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Obtener todos los autos
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Obtener autos de una empresa por ID de la empresa
    public List<Object[]> getCarsByEmployeeId(Long employeeId) {
        return carRepository.findAllCarsByEmployeeId(employeeId);
    }


    // Obtener auto por matrícula (licensePlate)
    public Optional<Car> getCarByLicensePlate(String licensePlate) {
        return carRepository.findById(licensePlate);
    }

    // Guardar o actualizar un auto
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    // Eliminar un auto por matrícula
    public void deleteCar(String licensePlate) {
        carRepository.deleteById(licensePlate);
    }

    // Verificar si un auto existe por su matrícula
    public boolean existsByLicensePlate(String licensePlate) {
        return carRepository.existsById(licensePlate);
    }
    public List<Object[]> getCarsByClientId(Long clientId) {
        return carRepository.findAllCarsByClientId(clientId);
    }
    public Optional<Car> findByLicensePlate(String licensePlate) {
        return carRepository.findById(licensePlate); // Asegúrate que tu repositorio tenga este método
    }
    // Obtener todos los formularios de un coche
    public List<Object[]> getFormsByCarLicensePlate(String licensePlate) {
        return carRepository.findAllFormsByCarLicensePlate(licensePlate);
    }

    // Obtener el nombre del cliente de un coche
// Obtener el cliente por matrícula (licensePlate)
    public List<Object[]> getClientByCarLicensePlate(String licensePlate) {
        return carRepository.findClientByCarLicensePlate(licensePlate);
    }


    // Obtener todas las facturas de un coche
    public List<Object[]> getInvoicesByCarLicensePlate(String licensePlate) {
        return carRepository.findInvoicesByCarLicensePlate(licensePlate);
    }
    public List<Object[]> getFormsByCarLicensePlateAndEmployeeId(String licensePlate, Long employeeId) {
        return carRepository.findAllFormsByCarLicensePlateAndCompany(licensePlate, Math.toIntExact(employeeId));
    }

    // Obtener facturas de un coche según el ID del empleado
    public List<Object[]> getInvoicesByCarLicensePlateAndEmployeeId(String licensePlate, Long employeeId) {
        return carRepository.findInvoicesByCarLicensePlateAndCompany(licensePlate, Math.toIntExact(employeeId));
    }


}
