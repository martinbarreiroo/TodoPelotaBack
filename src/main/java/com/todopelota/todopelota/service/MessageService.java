package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Message;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.MessageRepository;
import com.todopelota.todopelota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message createMessage(Message message, Tournament tournament) {
        Optional<User> user = userRepository.findById(parseLong(message.getAuthorId()));
        message.setTournament(tournament);
        message.setAuthorUsername(user.get().getUsername());
        tournament.addMessage(message);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByTournament(Tournament tournament) {
        return messageRepository.findByTournament(tournament);
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }
}
