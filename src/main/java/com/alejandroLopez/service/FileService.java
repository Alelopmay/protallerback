package com.alejandroLopez.service;

import com.alejandroLopez.DTO.FileDTO;
import com.alejandroLopez.model.File;
import com.alejandroLopez.model.Task;
import com.alejandroLopez.repository.FileRepository;
import com.alejandroLopez.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;
private final TaskRepository taskRepository;
    @Autowired
    public FileService(FileRepository fileRepository, TaskRepository taskRepository) {
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
    }

    // Crear un nuevo archivo
    public File createFile(FileDTO fileDTO) {
        // Crear una nueva instancia de File a partir del FileDTO
        File file = new File();
        file.setDate(fileDTO.getDate());
        file.setReport(fileDTO.getReport());
        file.setClientName(fileDTO.getClientName());
        file.setCarLicensePlate(fileDTO.getCarLicensePlate());
        file.setDetails(fileDTO.getDetails());
        file.setArchived(fileDTO.getArchived());

        // Guardar el archivo en la base de datos
        return fileRepository.save(file);
    }


    // Obtener todos los archivos
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    // Obtener un archivo por ID
    public File getFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    // Actualizar un archivo
    public File updateFile(Long id, File fileDetails) {
        File file = getFileById(id);
        if (file != null) {
            file.setDate(fileDetails.getDate());
            file.setReport(fileDetails.getReport());
            file.setClientName(fileDetails.getClientName());
            file.setCarLicensePlate(fileDetails.getCarLicensePlate());
            file.setDetails(fileDetails.getDetails());
            file.setArchived(fileDetails.getArchived());
            return fileRepository.save(file);
        }
        return null;
    }

    // Eliminar un archivo
    public boolean deleteFile(Long id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
            return true;
        }
        return false;
    }
    // Obtener archivos asociados a un empleado
    public List<File> getFilesByEmployeeId(Long employeeId) {
        return fileRepository.findArchivedFilesByEmployeeId(employeeId);
    }
    public List<File> getFilesByEmployeeIdfalse(Long employeeId) {
        return fileRepository.findArchivedFilesByEmployeeIdFalse(employeeId);
    }
    public void addEmployeeToFile(Long fileId, Long employeeId) {
        Task task = new Task(fileId, employeeId);
        taskRepository.save(task); // Guardar la relación en la tabla Task
    }
    public List<Object[]> getFileWithCarAndClientByFileId(Long fileId) {
        return fileRepository.findFileWithCarAndClientByFileId(fileId);
    }
    // Obtener las fechas de trabajo y los datos del empleado asociados a un archivo por ID
    public List<Object[]> getWorkDataByFileId(Long fileId) {
        return fileRepository.findWorkDatesAndEmployeeByFileId(fileId);
    }


    // Aquí puedes agregar más métodos según tus necesidades.
}
