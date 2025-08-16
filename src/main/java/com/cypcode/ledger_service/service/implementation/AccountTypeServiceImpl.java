package com.cypcode.ledger_service.service.implementation;

import com.cypcode.ledger_service.entity.AccountType;
import com.cypcode.ledger_service.entity.dto.AccountTypeListDTO;
import com.cypcode.ledger_service.repository.IAccountTypeRepository;
import com.cypcode.ledger_service.service.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

    @Autowired
    private IAccountTypeRepository accountTypeRepository;

    @Cacheable(cacheNames = "accountType")
    @Override
    public AccountTypeListDTO getAllAccountType() {
        AccountTypeListDTO accountTypeListDTO = new AccountTypeListDTO();
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        accountTypeListDTO.setAccountTypeList(accountTypeList);
        return accountTypeListDTO;
    }

    @Override
    public AccountType getAccountTypeById(Long id) {
        return accountTypeRepository.findById(id).get();
    }

    @Override
    public AccountType getAccountTypeByName(String name) {
        return accountTypeRepository.findByName(name);
    }

    @Override
    public AccountType addAccountType(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @Override
    public AccountType updateAccountType(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @Override
    public void deleteAccountType(Long id) {
        accountTypeRepository.deleteById(id);
    }
}
