package com.alejandroLopez.Controller;

import com.alejandroLopez.model.Message;
import com.alejandroLopez.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Obtener todos los mensajes
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // Obtener un mensaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") Long messageId) {
        Optional<Message> messageOptional = messageService.getMessageById(messageId);
        if (messageOptional.isPresent()) {
            return ResponseEntity.ok(messageOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Crear un nuevo mensaje
    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    // Actualizar un mensaje existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") Long messageId, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(messageId, message);
        if (updatedMessage != null) {
            return ResponseEntity.ok(updatedMessage);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un mensaje por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/sender/{senderId}/recipient/{recipientId}")
    public List<Message> getMessagesBySenderAndRecipient(
            @PathVariable Long senderId,
            @PathVariable Long recipientId
    ) {
        return messageService.getMessagesBySenderAndRecipient(senderId, recipientId);
    }
}
