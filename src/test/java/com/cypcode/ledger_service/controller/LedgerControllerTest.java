package com.cypcode.ledger_service.controller;

import com.cypcode.ledger_service.entity.dto.TransferDTO;
import com.cypcode.ledger_service.service.LedgerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.net.URI;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LedgerControllerTest {

    @Mock
    private LedgerService ledgerService;

    @InjectMocks
    private LedgerController ledgerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final String url = "http://localhost:8081/ledger/transfer";

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(ledgerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testLedgerTransfer() throws Exception {
        TransferDTO payload = TransferDTO.builder()
                .transferId(123456789)
                .fromAccountId(12345)
                .toAccountId(56789)
                .amount(BigDecimal.valueOf(200))
                .build();
        lenient().when(ledgerService.transfer(payload)).thenReturn("SUCCESS");
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().is2xxSuccessful());

        verify(ledgerService, times(1)).transfer(payload);
    }




}
