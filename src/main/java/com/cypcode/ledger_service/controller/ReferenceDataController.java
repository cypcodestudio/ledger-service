package com.cypcode.ledger_service.controller;

import com.cypcode.ledger_service.entity.dto.AccountTypeDTO;
import com.cypcode.ledger_service.entity.dto.AccountTypeListDTO;
import com.cypcode.ledger_service.entity.dto.TransferDTO;
import com.cypcode.ledger_service.service.implementation.AccountTypeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reference-data")
@Tag(name = "Reference Data", description = "APIs for managing Lookup Data")
public class ReferenceDataController {

    @Autowired
    private AccountTypeServiceImpl accountTypeService;

    @Operation(summary = "Get account type reference data", description = "Retrieve account type reference data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "account type list retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AccountTypeListDTO.class))),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping()
    public ResponseEntity<?> getAccountType(){
        try {
            return ResponseEntity.ok().body(accountTypeService.getAllAccountType());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
