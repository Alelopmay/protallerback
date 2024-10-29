package com.alejandroLopez.repository;

import com.alejandroLopez.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Método para obtener todos los mensajes entre dos empleados (emisor y receptor en ambos sentidos)
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :senderId AND m.recipient.id = :recipientId) " +
            "OR (m.sender.id = :recipientId AND m.recipient.id = :senderId) ORDER BY m.sentAt ASC")
    List<Message> findMessagesBySenderAndRecipient(@Param("senderId") Long senderId,
                                                   @Param("recipientId") Long recipientId);
}

