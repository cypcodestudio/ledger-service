package com.cypcode.ledger_service.service;

import com.cypcode.ledger_service.entity.LedgerEntry;
import com.cypcode.ledger_service.entity.dto.LedgerEntryDTO;
import com.cypcode.ledger_service.entity.dto.TransferDTO;

import java.util.List;

public interface LedgerService {
    public LedgerEntryDTO createLedgerEntry(LedgerEntryDTO ledgerEntry);
    public LedgerEntryDTO getLedgerEntryById(Long id);
    public List<LedgerEntryDTO> getLedgerEntryByTransferId(Long id);
    public LedgerEntryDTO updateLedgerEntry(LedgerEntryDTO ledgerEntry);
    public String transfer(TransferDTO transfer);
    public void deleteLedgerEntryById(Long id);
    public LedgerEntryDTO mapToLedgerEntryDTO(LedgerEntry ledgerEntry);
    public LedgerEntry mapToLedgerEntry(LedgerEntryDTO ledgerEntry);
}
