package com.alejandroLopez.Controller;

import com.alejandroLopez.model.Photo;
import com.alejandroLopez.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // Obtener todas las fotos
    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    // Obtener una foto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable Long id) {
        Optional<Photo> photoOpt = photoService.getPhotoById(id);
        return photoOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva foto (Recibe archivo y formId)
    @PostMapping("/upload")
    public ResponseEntity<Photo> createPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("formId") Long formId) {
        try {
            Photo savedPhoto = photoService.savePhoto(file, formId);
            return ResponseEntity.ok(savedPhoto);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }



    // Actualizar una foto existente
    @PutMapping("/{id}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable Long id, @RequestBody Photo updatedPhoto) {
        try {
            Photo photo = photoService.updatePhoto(id, updatedPhoto);
            return ResponseEntity.ok(photo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una foto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        Optional<Photo> photoOpt = photoService.getPhotoById(id);
        if (photoOpt.isPresent()) {
            photoService.deletePhoto(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Obtener fotos por formId
    @GetMapping("/form/{formId}")
    public ResponseEntity<List<Photo>> getPhotosByFormId(@PathVariable Long formId) {
        List<Photo> photos = photoService.getPhotosByFormId(formId);
        if (photos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(photos);
    }


}
