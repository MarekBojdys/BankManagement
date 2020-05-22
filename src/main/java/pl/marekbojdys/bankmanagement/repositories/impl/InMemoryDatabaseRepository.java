package pl.marekbojdys.bankmanagement.repositories.impl;

import org.springframework.stereotype.Repository;
import pl.marekbojdys.bankmanagement.models.BankAccount;
import pl.marekbojdys.bankmanagement.repositories.BankAccountRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryDatabaseRepository implements BankAccountRepository {

    private List<BankAccount> bankAccounts = Collections.synchronizedList(new ArrayList<>());

    @Override
    public BankAccount save(final BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
        return bankAccount;
    }

    @Override
    public Optional<BankAccount> findByUuid(final String bankAccountUuid) {
        return bankAccounts.stream()
                .filter(bankAccount -> bankAccount.getUuid().equals(bankAccountUuid))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        bankAccounts.clear();
    }
}
