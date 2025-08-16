package com.cypcode.ledger_service.entity.dto;


import com.cypcode.ledger_service.entity.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountTypeListDTO implements Serializable {
    private List<AccountType> accountTypeList;
}
