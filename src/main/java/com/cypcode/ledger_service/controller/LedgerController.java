package com.cypcode.ledger_service.controller;

import com.cypcode.ledger_service.entity.dto.AccountDTO;
import com.cypcode.ledger_service.entity.dto.TransferDTO;
import com.cypcode.ledger_service.service.LedgerService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("ledger")
@Tag(name = "Ledger", description = "APIs for managing Ledger Entries")
public class LedgerController {
    @Autowired
    private LedgerService ledgerService;

    @Operation(summary = "Create a new transfer", description = "Add a new transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "transfer completed successfully",
                    content = @Content(schema = @Schema(implementation = TransferDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDTO payload) {
        try {
            String status = ledgerService.transfer(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(status);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
