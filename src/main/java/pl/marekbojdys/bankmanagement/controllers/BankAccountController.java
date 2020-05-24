package pl.marekbojdys.bankmanagement.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marekbojdys.bankmanagement.converters.BankAccountRequestDTOToModelConverter;
import pl.marekbojdys.bankmanagement.dtos.BankAccountAddRequestDTO;
import pl.marekbojdys.bankmanagement.models.BankAccount;
import pl.marekbojdys.bankmanagement.services.BankAccountService;

import javax.validation.Valid;

@RestController
@Tag(name = "BankAccountController")
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountRequestDTOToModelConverter bankAccountRequestDTOToModelConverter;

    public BankAccountController(final BankAccountService bankAccountService,
                                 final BankAccountRequestDTOToModelConverter bankAccountRequestDTOToModelConverter) {
        this.bankAccountService = bankAccountService;
        this.bankAccountRequestDTOToModelConverter = bankAccountRequestDTOToModelConverter;
    }

    @GetMapping("/{bankAccountUuid}")
    @Operation(summary = "Get BankAccount By Uuid")
    public ResponseEntity<BankAccount> getBankAccountByUuid(@PathVariable final String bankAccountUuid) {
        return ResponseEntity.ok(bankAccountService.getBankAccount(bankAccountUuid));
    }

    @PostMapping("/")
    @Operation(summary = "Add BankAccount")
    public ResponseEntity<BankAccount> addBankAccount(@RequestBody @Valid final BankAccountAddRequestDTO bankAccountRequestDTO) {
        final BankAccount bankAccount = bankAccountService.addBankAccount(bankAccountRequestDTOToModelConverter.convert(bankAccountRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccount);
    }
}
