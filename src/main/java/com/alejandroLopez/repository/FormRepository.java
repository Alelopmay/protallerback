package com.alejandroLopez.repository;

import com.alejandroLopez.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
