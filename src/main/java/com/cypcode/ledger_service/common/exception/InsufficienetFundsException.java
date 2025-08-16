package com.cypcode.ledger_service.common.exception;

public class InsufficienetFundsException extends RuntimeException {
    public InsufficienetFundsException(String message) {
        super(message);
    }
}
