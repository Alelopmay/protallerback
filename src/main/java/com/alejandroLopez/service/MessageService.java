package com.alejandroLopez.service;

import com.alejandroLopez.model.Message;
import com.alejandroLopez.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Guardar un nuevo mensaje
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    // Obtener todos los mensajes
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Obtener un mensaje por ID
    public Optional<Message> getMessageById(Long messageId) {
        return messageRepository.findById(messageId);
    }

    // Eliminar un mensaje por ID
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    // Actualizar un mensaje existente
    public Message updateMessage(Long messageId, Message updatedMessage) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message existingMessage = messageOptional.get();
            existingMessage.setContent(updatedMessage.getContent());
            existingMessage.setIsRead(updatedMessage.getIsRead());
            existingMessage.setSentAt(updatedMessage.getSentAt());
            return messageRepository.save(existingMessage);
        }
        return null;
    }

    public List<Message> getMessagesBySenderAndRecipient(Long senderId, Long recipientId) {
        return messageRepository.findMessagesBySenderAndRecipient(senderId, recipientId);
    }
}
