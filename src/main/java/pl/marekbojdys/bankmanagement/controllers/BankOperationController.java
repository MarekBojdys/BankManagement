package pl.marekbojdys.bankmanagement.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.marekbojdys.bankmanagement.dtos.ChangeBalanceResponseDTO;
import pl.marekbojdys.bankmanagement.enums.OperationType;
import pl.marekbojdys.bankmanagement.services.BankOperationService;

import java.math.BigDecimal;

@RestController
@Tag(name = "BankOperationController")
@RequestMapping("/api/bankOperation")
public class BankOperationController {

    private final BankOperationService bankOperationService;

    public BankOperationController(final BankOperationService bankOperationService) {
        this.bankOperationService = bankOperationService;
    }

    @PostMapping("/{bankAccountUuid}")
    @Operation(summary = "Change BankAccount Balance")
    public ResponseEntity<ChangeBalanceResponseDTO> changeBankAccountBalance(@PathVariable final String bankAccountUuid,
                                                                             @RequestParam final OperationType operationType,
                                                                             @RequestParam final BigDecimal amount) {
        return ResponseEntity.ok(bankOperationService.changeBankAccountBalance(bankAccountUuid, amount, operationType));
    }

}
