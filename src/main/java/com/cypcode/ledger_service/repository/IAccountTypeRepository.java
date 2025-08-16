package com.cypcode.ledger_service.repository;

import com.cypcode.ledger_service.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountTypeRepository extends JpaRepository<AccountType, Long> {
    AccountType findByName(String name);
}
