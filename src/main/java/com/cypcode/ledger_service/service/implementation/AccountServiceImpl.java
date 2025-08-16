package com.cypcode.ledger_service.service.implementation;

import com.cypcode.ledger_service.entity.Account;
import com.cypcode.ledger_service.entity.dto.AccountDTO;
import com.cypcode.ledger_service.repository.IAccountRepository;
import com.cypcode.ledger_service.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public AccountDTO getAccountById(long id) {
        try {
            Optional<Account> dto = accountRepository.findById(id);
            if (dto.isPresent()) {
                return mapToAccountDTO(dto.get());
            }
            return null;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    @Override
    public Account getEntityAccountById(long id) {
        try {
           return accountRepository.findById(id).orElse(null);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public AccountDTO createAccount(AccountDTO account) {
        try {
            return mapToAccountDTO(accountRepository.save(mapToAccount(account)));
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    @Override
    public void updateAccount(Account account) {
        try {
            account.setVersion(account.getVersion() + 1);
            accountRepository.save(account);
            mapToAccountDTO(account);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public void deleteAccount(long id) {
        try {
            accountRepository.deleteById(id);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public AccountDTO mapToAccountDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .name(account.getName())
                .balance(account.getBalance())
                .type(account.getType())
                .version(account.getVersion())
                .build();
    }

    @Override
    public Account mapToAccount(AccountDTO accountDTO) {
        return Account.builder()
                .id(accountDTO.getId())
                .name(accountDTO.getName())
                .balance(accountDTO.getBalance())
                .type(accountDTO.getType())
                .balance(accountDTO.getBalance())
                .build();
    }
}
