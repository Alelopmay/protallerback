package com.alejandroLopez.model;

import java.io.Serializable;
import java.util.Objects;

public class TaskId implements Serializable {
    private Long fileId;
    private Long employeeId;

    // Default constructor
    public TaskId() {}

    // Getters, Setters, equals, and hashCode methods
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskId)) return false;
        TaskId that = (TaskId) o;
        return Objects.equals(fileId, that.fileId) &&
                Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, employeeId);
    }
}
