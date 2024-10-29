package com.alejandroLopez.repository;

import com.alejandroLopez.model.Task;
import com.alejandroLopez.model.TaskId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, TaskId> {

    // Método para archivar el File basado en el file_id
    @Modifying
    @Transactional
    @Query("UPDATE File f SET f.archived = true WHERE f.id IN (SELECT t.fileId FROM Task t WHERE t.fileId = ?1)")
    void archiveFileByTaskId(Long fileId);
}
