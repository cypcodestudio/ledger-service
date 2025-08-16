package com.cypcode.ledger_service.controller;

import com.cypcode.ledger_service.entity.dto.AccountDTO;
import com.cypcode.ledger_service.entity.dto.TransferDTO;
import com.cypcode.ledger_service.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("accounts")
@Tag(name = "Accounts", description = "APIs for managing Accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Create a new account", description = "Add a new bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "account created successfully",
                    content = @Content(schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping()
    public ResponseEntity<?> addAccount(@RequestBody AccountDTO payload) {
        try{
            AccountDTO response = accountService.createAccount(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get account by id", description = "Get account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "account retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AccountDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getAccount(@PathVariable long id) {
        try {
            AccountDTO response = accountService.getAccountById(id);
            if(response != null) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
