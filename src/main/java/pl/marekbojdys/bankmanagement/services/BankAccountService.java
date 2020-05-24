package pl.marekbojdys.bankmanagement.services;

import pl.marekbojdys.bankmanagement.models.BankAccount;

public interface BankAccountService {

    BankAccount addBankAccount(BankAccount bankAccount);

    BankAccount getBankAccount(String uuid);
}
