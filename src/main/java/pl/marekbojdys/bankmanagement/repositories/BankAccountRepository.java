package pl.marekbojdys.bankmanagement.repositories;

import pl.marekbojdys.bankmanagement.models.BankAccount;

import java.util.Optional;

public interface BankAccountRepository {

    BankAccount save(BankAccount bankAccount);

    Optional<BankAccount> findByUuid(String bankAccountUuid);

    void deleteAll();
}
