package com.cypcode.ledger_service.common;

public enum EAccountType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

     private String type;
    EAccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
