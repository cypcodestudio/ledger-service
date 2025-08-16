package com.cypcode.ledger_service.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LedgerEntryDTO implements Serializable {
    private Long id;
    private Long transferId;
    private Long accountId;
    private BigDecimal amount;
    private String type;
    private Date createdAt;
}
