package com.cypcode.ledger_service.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO implements Serializable {
    private Long id;
    @NotNull(message = "balance amount is mandatory")
    private BigDecimal balance;
    @NotEmpty(message = "Account name is mandatory")
    private String name;
    @NotEmpty(message = "Account type is mandatory")
    private String type;
    private int version;
}
