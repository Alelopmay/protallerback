package com.alejandroLopez.service;

import com.alejandroLopez.model.Form;
import com.alejandroLopez.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    private final FormRepository formRepository;

    @Autowired
    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    // Crear o actualizar un formulario
    public Form saveForm(Form form) {
        return formRepository.save(form);
    }

    // Obtener todos los formularios
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    // Obtener un formulario por ID
    public Optional<Form> getFormById(Long id) {
        return formRepository.findById(id);
    }

    // Eliminar un formulario por ID
    public void deleteForm(Long id) {
        formRepository.deleteById(id);
    }
}
