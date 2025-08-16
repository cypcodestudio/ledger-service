package com.cypcode.ledger_service.service.implementation;

import com.cypcode.ledger_service.common.EAccountType;
import com.cypcode.ledger_service.common.ETransactionStatus;
import com.cypcode.ledger_service.common.exception.AccountNotFoundException;
import com.cypcode.ledger_service.common.exception.InsufficienetFundsException;
import com.cypcode.ledger_service.entity.Account;
import com.cypcode.ledger_service.entity.LedgerEntry;
import com.cypcode.ledger_service.entity.dto.LedgerEntryDTO;
import com.cypcode.ledger_service.entity.dto.TransferDTO;
import com.cypcode.ledger_service.repository.ILedgerRepository;
import com.cypcode.ledger_service.service.AccountService;
import com.cypcode.ledger_service.service.LedgerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Slf4j
@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private ILedgerRepository ledgerRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    @Override
    public LedgerEntryDTO createLedgerEntry(LedgerEntryDTO ledgerEntry) {
        try {
            return mapToLedgerEntryDTO(ledgerRepository.save(mapToLedgerEntry(ledgerEntry)));
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    @Override
    public LedgerEntryDTO getLedgerEntryById(Long id) {
       try {
           LedgerEntry entry = ledgerRepository.findById(id).orElse(null);
           if (entry != null) {
               return mapToLedgerEntryDTO(entry);
           }
           return null;
       }catch (Exception e){
           throw e;
       }
    }

    @Override
    public LedgerEntryDTO getLedgerEntryByTransferId(Long id) {
        try{
            LedgerEntry entry = ledgerRepository.findLedgerEntryByTransferId(id);
            if (entry != null) {
                return mapToLedgerEntryDTO(entry);
            }
            return null;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    @Override
    public LedgerEntryDTO updateLedgerEntry(LedgerEntryDTO ledgerEntry) {
        try {
            return mapToLedgerEntryDTO(ledgerRepository.save(mapToLedgerEntry(ledgerEntry)));
        }catch (Exception e){
            throw e;
        }
    }

    @Retryable(value = {ObjectOptimisticLockingFailureException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    @Override
    public String transfer(TransferDTO transfer) {
        try {
            Account fromAccount = accountService.getEntityAccountById(transfer.getFromAccountId());
            if(fromAccount == null){
                throw new AccountNotFoundException(String.format("Account not found for account id: %s", transfer.getFromAccountId()));
            }
            if(fromAccount.getBalance().doubleValue() < transfer.getAmount().doubleValue()){
                throw new InsufficienetFundsException(String.format("Insufficient funds for account id: %s", transfer.getFromAccountId()));
            }

            Account toAccount = accountService.getEntityAccountById(transfer.getToAccountId());
            if(toAccount == null){
                throw new AccountNotFoundException(String.format("Account not found for account id: %s", transfer.getToAccountId()));
            }

            fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.getAmount()));
            accountService.updateAccount(fromAccount);
            toAccount.setBalance(toAccount.getBalance().add(transfer.getAmount()));
            toAccount.setVersion(toAccount.getVersion() + 1);
            accountService.updateAccount(toAccount);
            log.info("Transfer successful");
            return ETransactionStatus.SUCCESS.getStatus();
        }catch (Exception e){
            log.error("Failed transfer ", e);
            return  ETransactionStatus.FAILURE.getStatus();
        }finally {

            createLedgerEntry(LedgerEntryDTO.builder()
                    .type(EAccountType.DEBIT.getType())
                    .transferId(transfer.getTransferId())
                    .amount(transfer.getAmount())
                    .accountId(transfer.getFromAccountId())
                    .createdAt(Calendar.getInstance().getTime())
                    .build());

            createLedgerEntry(LedgerEntryDTO.builder()
                    .type(EAccountType.CREDIT.getType())
                    .transferId(transfer.getTransferId())
                    .amount(transfer.getAmount())
                    .accountId(transfer.getToAccountId())
                    .createdAt(Calendar.getInstance().getTime())
                    .build());
        }
    }

    @Transactional
    @Override
    public void deleteLedgerEntryById(Long id) {
        ledgerRepository.deleteById(id);
    }

    @Override
    public LedgerEntryDTO mapToLedgerEntryDTO(LedgerEntry ledgerEntry) {
        return LedgerEntryDTO.builder()
                .id(ledgerEntry.getId())
                .transferId(ledgerEntry.getTransferId())
                .accountId(ledgerEntry.getAccountId())
                .amount(ledgerEntry.getAmount())
                .type(ledgerEntry.getType())
                .createdAt(ledgerEntry.getCreatedAt())
                .build();
    }

    @Override
    public LedgerEntry mapToLedgerEntry(LedgerEntryDTO ledgerEntry) {
        return LedgerEntry.builder()
                .id(ledgerEntry.getId())
                .transferId(ledgerEntry.getTransferId())
                .accountId(ledgerEntry.getAccountId())
                .amount(ledgerEntry.getAmount())
                .type(ledgerEntry.getType())
                .createdAt(ledgerEntry.getCreatedAt())
                .build();
    }
}
