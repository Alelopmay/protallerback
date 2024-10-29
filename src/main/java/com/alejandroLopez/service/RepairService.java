package com.alejandroLopez.service;

import com.alejandroLopez.model.Repair;
import com.alejandroLopez.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RepairService {

    @Autowired
    private RepairRepository repairRepository;

    // Obtener todos los repairs
    public List<Repair> getAllRepairs() {
        return repairRepository.findAll();
    }

    // Obtener un repair por su ID
    public Optional<Repair> getRepairById(Long id) {
        return repairRepository.findById(id);
    }

    // Crear un nuevo repair
    public Repair createRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    // Actualizar un repair existente
    public Repair updateRepair(Long id, Repair updatedRepair) {
        Optional<Repair> existingRepair = repairRepository.findById(id);

        if (existingRepair.isPresent()) {
            Repair repair = existingRepair.get();
            repair.setCar(updatedRepair.getCar());
            repair.setEmployee(updatedRepair.getEmployee());
            repair.setStartDate(updatedRepair.getStartDate());
            repair.setEndDate(updatedRepair.getEndDate());
            return repairRepository.save(repair);
        } else {
            throw new RuntimeException("Repair no encontrado con id: " + id);
        }
    }

    // Eliminar un repair por su ID
    public void deleteRepair(Long id) {
        repairRepository.deleteById(id);
    }

}
