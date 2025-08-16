package com.cypcode.ledger_service.service;

import com.cypcode.ledger_service.entity.Account;
import com.cypcode.ledger_service.entity.dto.AccountDTO;

public interface AccountService {
    public AccountDTO getAccountById(long id);
    public AccountDTO createAccount(AccountDTO account);
    public AccountDTO updateAccount(AccountDTO account);
    public void deleteAccount(long id);
    public AccountDTO mapToAccountDTO(Account account);
    public Account mapToAccount(AccountDTO accountDTO);
}
