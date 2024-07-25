package com.example.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.*;

@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Account> newUser(@RequestBody Account account){
        if(accountService.addAccount(account) != null){
            return ResponseEntity.status(200).body(accountService.addAccount(account));
        }
        else{
            if(accountService.findAccountByUsername(account) != null){
                return ResponseEntity.status(409).body(null);
            }
            else{
                return ResponseEntity.status(400).body(null);
            }
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account){
        if(accountService.loginAccount(account) != null){
            return ResponseEntity.status(200).body(accountService.loginAccount(account));
        }
        else{
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message){
        if(messageService.addMessage(message) != null){
            return ResponseEntity.status(200).body(messageService.addMessage(message));
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> getAllMessage(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping(value = "/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.getMessageByID(messageId));
    }

    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        if(messageService.deleteMessage(messageId) == 1){
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(200).body(null);
        }
    }

    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message message){
        if(messageService.updateMessage(messageId, message) != null){
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(value = "/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByUser(@PathVariable int accountId){
        return ResponseEntity.status(200).body(messageService.getMessageByUser(accountId));
    }

}
