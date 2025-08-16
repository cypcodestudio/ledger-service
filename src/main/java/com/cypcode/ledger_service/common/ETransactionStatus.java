package com.cypcode.ledger_service.common;

public enum ETransactionStatus {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),;

    private String status;

    ETransactionStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

}
