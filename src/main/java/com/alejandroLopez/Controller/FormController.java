package com.alejandroLopez.Controller;

import com.alejandroLopez.model.Form;
import com.alejandroLopez.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forms")
public class FormController {

    private final FormService formService;

    @Autowired
    public FormController(FormService formService) {
        this.formService = formService;
    }

    // Obtener todos los formularios
    @GetMapping
    public List<Form> getAllForms() {
        return formService.getAllForms();
    }

    // Obtener un formulario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable Long id) {
        Optional<Form> form = formService.getFormById(id);
        return form.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo formulario
    @PostMapping
    public Form createForm(@RequestBody Form form) {
        return formService.saveForm(form);
    }

    // Actualizar un formulario existente
    @PutMapping("/{id}")
    public ResponseEntity<Form> updateForm(@PathVariable Long id, @RequestBody Form formDetails) {
        Optional<Form> formOptional = formService.getFormById(id);
        if (formOptional.isPresent()) {
            Form form = formOptional.get();
            form.setDescription(formDetails.getDescription());
            form.setDate(formDetails.getDate());
            form.setInitialDiagnosis(formDetails.getInitialDiagnosis());
            form.setInspectionResults(formDetails.getInspectionResults());
            form.setWorkPerformed(formDetails.getWorkPerformed());
            form.setEmployee(formDetails.getEmployee());
            form.setCar(formDetails.getCar());

            Form updatedForm = formService.saveForm(form);
            return ResponseEntity.ok(updatedForm);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un formulario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        if (formService.getFormById(id).isPresent()) {
            formService.deleteForm(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
