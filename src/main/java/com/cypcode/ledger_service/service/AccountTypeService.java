package com.cypcode.ledger_service.service;

import com.cypcode.ledger_service.entity.AccountType;
import com.cypcode.ledger_service.entity.dto.AccountTypeListDTO;

import java.util.List;

public interface AccountTypeService {
    public AccountTypeListDTO getAllAccountType();
    public AccountType getAccountTypeById(Long id);
    public AccountType getAccountTypeByName(String name);
    public AccountType addAccountType(AccountType accountType);
    public AccountType updateAccountType(AccountType accountType);
    public void deleteAccountType(Long id);
}
