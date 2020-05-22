package pl.marekbojdys.bankmanagement.services;

import pl.marekbojdys.bankmanagement.enums.OperationType;
import pl.marekbojdys.bankmanagement.models.BankAccount;

public interface BankAccountService {

    BankAccount addBankAccount(BankAccount bankAccount);

    BankAccount getBankAccount(String uuid);

    String changeBankAccountBalance(String uuid,
                                        Integer amount,
                                        OperationType operationType);
}
