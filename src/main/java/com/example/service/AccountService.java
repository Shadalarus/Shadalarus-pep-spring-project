package com.example.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account) {
        if(account.getPassword().length() < 4 || account.getUsername() == "" || findAccountByUsername(account) != null){
            return null;
        }
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(optionalAccount.isPresent()){
            Account successAccount = optionalAccount.get();
            return successAccount;
        }
        else{
            return null;
        }
    }

    public Account findAccountByUsername(Account account){
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if(optionalAccount.isPresent()){
            Account successAccount = optionalAccount.get();
            return successAccount;
        }
        else{
            return null;
        }
    }

}
