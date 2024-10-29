package com.alejandroLopez.model;

import jakarta.persistence.*;

@Entity
@IdClass(TaskId.class)
public class Task {

    @Id
    @Column(name = "file_id")
    private Long fileId;

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    // Default constructor
    public Task() {}

    // Constructor with parameters
    public Task(Long fileId, Long employeeId) {
        this.fileId = fileId;
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
