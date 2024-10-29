package com.alejandroLopez.Controller;

import com.alejandroLopez.DTO.FileDTO;
import com.alejandroLopez.model.File;
import com.alejandroLopez.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // Crear un nuevo archivo
    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody FileDTO fileDTO) {
        // Crear el archivo en la base de datos
        File createdFile = fileService.createFile(fileDTO);

        // Comprobar si se enviaron IDs de empleados
        if (fileDTO.getEmployeeIds() != null) {
            for (Long employeeId : fileDTO.getEmployeeIds()) {
                fileService.addEmployeeToFile(createdFile.getId(), employeeId);
            }
        }

        return new ResponseEntity<>(createdFile, HttpStatus.CREATED);
    }


    // Obtener todos los archivos
    @GetMapping
    public ResponseEntity<List<File>> getAllFiles() {
        List<File> files = fileService.getAllFiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    // Obtener un archivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<File> getFileById(@PathVariable Long id) {
        File file = fileService.getFileById(id);
        if (file != null) {
            return new ResponseEntity<>(file, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si no se encuentra el archivo
    }

    // Actualizar un archivo
    @PutMapping("/{id}")
    public ResponseEntity<File> updateFile(@PathVariable Long id, @RequestBody File fileDetails) {
        File updatedFile = fileService.updateFile(id, fileDetails);
        if (updatedFile != null) {
            return new ResponseEntity<>(updatedFile, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si no se encuentra el archivo
    }

    // Eliminar un archivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        if (fileService.deleteFile(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si se elimina correctamente
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si no se encuentra el archivo
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<File>> getFilesByEmployeeId(@PathVariable Long employeeId) {
        List<File> files = fileService.getFilesByEmployeeId(employeeId);
        if (files != null && !files.isEmpty()) {
            return new ResponseEntity<>(files, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<List<Object[]>> getFileWithCarAndClient(@PathVariable Long id) {
        List<Object[]> results = fileService.getFileWithCarAndClientByFileId(id);
        if (results != null && !results.isEmpty()) {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si no se encuentra el archivo
    }
    // Obtener las fechas de trabajo y los datos del empleado asociados a un archivo por ID
    @GetMapping("/{fileId}/work-data")
    public ResponseEntity<List<Object[]>> getWorkDataByFileId(@PathVariable Long fileId) {
        List<Object[]> workData = fileService.getWorkDataByFileId(fileId);
        if (workData != null && !workData.isEmpty()) {
            return new ResponseEntity<>(workData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si no se encuentran datos
    }
    @GetMapping("/employee/{employeeId}/active")
    public ResponseEntity<List<File>> getFilesByEmployeeIdFalse(@PathVariable Long employeeId) {
        List<File> files = fileService.getFilesByEmployeeIdfalse(employeeId); // Llama al método del servicio
        if (files != null && !files.isEmpty()) {
            return new ResponseEntity<>(files, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si no se encuentran archivos
    }

}
