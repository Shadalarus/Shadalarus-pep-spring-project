package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    AccountRepository accountRepository;

    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    
    public Message addMessage(Message message) {
        if(message.getMessageText().length() > 255 || message.getMessageText() == "" || accountRepository.findById(message.getPostedBy()).isPresent() == false){
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return (List<Message>) messageRepository.findAll();
    }

    public Message getMessageByID(int messageID){
        Optional<Message> optionalMessage = messageRepository.findById(messageID);
        if (optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            return message;
        }
        else{
            return null;
        }
    }

    public int deleteMessage(int messageID){
        if(getMessageByID(messageID) != null){
            messageRepository.deleteById(messageID);
            return 1;
        }
        else{
            return 0;
        }
    }

    public Message updateMessage(int messageID, Message message){
        if(message.getMessageText().length() > 255 || message.getMessageText() == ""){
            return null;
        }
        Optional<Message> optionalMessage = messageRepository.findById(messageID);
        if (optionalMessage.isPresent()){
            Message newMessage = optionalMessage.get();
            newMessage.setMessageText(message.getMessageText());
            return messageRepository.save(newMessage);
        }
        else{
            return null;
        }
    }

    public List<Message> getMessageByUser(int userID){
        return messageRepository.findByPostedBy(userID);
    }
}
