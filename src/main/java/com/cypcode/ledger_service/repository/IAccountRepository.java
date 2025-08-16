package com.cypcode.ledger_service.repository;

import com.cypcode.ledger_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findFirstByName(String name);
    Account findFirstById(Long id);
}
