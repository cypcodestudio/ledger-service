package com.cypcode.ledger_service.repository;

import com.cypcode.ledger_service.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILedgerRepository extends JpaRepository<LedgerEntry, Long> {
    LedgerEntry findLedgerEntryById(Long id);
    LedgerEntry findLedgerEntryByTransferId(Long transactionId);
}
