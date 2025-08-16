package com.cypcode.ledger_service.controller;

import com.cypcode.ledger_service.entity.dto.AccountDTO;
import com.cypcode.ledger_service.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final String url = "http://localhost:8081/accounts";

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateAccount() throws Exception {
        AccountDTO payload = AccountDTO.builder()
                .name("test")
                .type("Debit")
                .balance(BigDecimal.valueOf(500))
                .build();
        AccountDTO response = AccountDTO.builder()
                .id(12345L)
                .name(payload.getName())
                .type(payload.getType())
                .balance(payload.getBalance())
                .version(payload.getVersion())
                .build();
        lenient().when(accountService.createAccount(payload)).thenReturn(response);
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().is2xxSuccessful());

        verify(accountService, times(1)).createAccount(payload);
    }

    @Test
    public void testGetAccount() throws Exception {
        AccountDTO response = AccountDTO.builder()
                .id(12345L)
                .name("test")
                .type("Debit")
                .balance(BigDecimal.valueOf(500))
                .version(0)
                .build();
        lenient().when(accountService.getAccountById(12345L)).thenReturn(response);
        mockMvc.perform(get(url + "/{id}", 12345L))
                        .andExpect(status().is2xxSuccessful());

        verify(accountService, times(1)).getAccountById(12345L);
    }
}
