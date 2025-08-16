package com.cypcode.ledger_service.service;

import com.cypcode.ledger_service.entity.LedgerEntry;
import com.cypcode.ledger_service.entity.dto.LedgerEntryDTO;
import com.cypcode.ledger_service.entity.dto.TransferDTO;

public interface LedgerService {
    public LedgerEntryDTO createLedgerEntry(LedgerEntryDTO ledgerEntry);
    public LedgerEntryDTO getLedgerEntryById(Long id);
    public LedgerEntryDTO getLedgerEntryByTransferId(Long id);
    public LedgerEntryDTO updateLedgerEntry(LedgerEntryDTO ledgerEntry);
    public String transfer(TransferDTO transfer);
    public void deleteLedgerEntryById(Long id);
    public LedgerEntryDTO mapToLedgerEntryDTO(LedgerEntry ledgerEntry);
    public LedgerEntry mapToLedgerEntry(LedgerEntryDTO ledgerEntry);
}
