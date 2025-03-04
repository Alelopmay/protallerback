package com.alejandroLopez.repository;

import com.alejandroLopez.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    // Consulta personalizada para obtener las fotos por el ID del formulario
    @Query("SELECT p FROM Photo p WHERE p.form.id = :formId")
    List<Photo> findPhotosByFormId(@Param("formId") Long formId);
    List<Photo> findByFormId(Long formId);

}
