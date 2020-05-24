package pl.marekbojdys.bankmanagement.services.impl;

import org.springframework.stereotype.Service;
import pl.marekbojdys.bankmanagement.exceptions.NotFoundException;
import pl.marekbojdys.bankmanagement.models.BankAccount;
import pl.marekbojdys.bankmanagement.repositories.BankAccountRepository;
import pl.marekbojdys.bankmanagement.services.BankAccountService;

import java.util.UUID;

@Service
public class DefaultBankAccountService implements BankAccountService {

    private static final String BANK_ACCOUNT_NOT_FOUND_WITH_UUID = "Bank account not found with uuid: ";
    private final BankAccountRepository bankAccountRepository;

    public DefaultBankAccountService(final BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount addBankAccount(final BankAccount bankAccount) {
        bankAccount.setUuid(UUID.randomUUID().toString());
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount getBankAccount(final String uuid) {
        return bankAccountRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(BANK_ACCOUNT_NOT_FOUND_WITH_UUID + uuid));
    }
}
