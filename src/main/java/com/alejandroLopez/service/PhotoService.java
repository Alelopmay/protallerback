package com.alejandroLopez.service;

import com.alejandroLopez.model.Photo;
import com.alejandroLopez.repository.PhotoRepository;
import com.alejandroLopez.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private FormRepository formRepository;

    // Ruta donde se almacenarán los archivos (puedes configurarla en application.properties)
    @Value("${app.upload.dir}")
    private String uploadDir;

    // Guardar una nueva foto
    public Photo savePhoto(MultipartFile file, Long formId) throws IOException {
        // Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            throw new IOException("No se ha subido un archivo.");
        }

        // Asegurarse de que la carpeta de almacenamiento exista
        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs(); // Crear las carpetas si no existen
        }

        // Obtener el nombre del archivo y la extensión
        String fileName = file.getOriginalFilename();
        Path targetPath = new File(uploadDir + File.separator + fileName).toPath();

        // Guardar el archivo en la carpeta destino
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // Crear una nueva entrada en la base de datos
        Photo newPhoto = new Photo();
        newPhoto.setUrl("/photos/" + fileName);
        newPhoto.setForm(formRepository.findById(formId).orElse(null));

        // Guardar la foto en la base de datos
        return photoRepository.save(newPhoto);
    }

    // Obtener todas las fotos
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    // Obtener una foto por ID
    public Optional<Photo> getPhotoById(Long id) {
        return photoRepository.findById(id);
    }

    // Eliminar foto por ID
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    // Actualizar foto (opcional)
    public Photo updatePhoto(Long id, Photo updatedPhoto) {
        if (photoRepository.existsById(id)) {
            updatedPhoto.setId(id);
            return photoRepository.save(updatedPhoto);
        }
        return null;
    }
    // Obtener fotos por formId
    public List<Photo> getPhotosByFormId(Long formId) {
        return photoRepository.findByFormId(formId);
    }


}
