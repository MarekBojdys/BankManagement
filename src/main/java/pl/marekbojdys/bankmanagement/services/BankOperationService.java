package pl.marekbojdys.bankmanagement.services;

import pl.marekbojdys.bankmanagement.dtos.ChangeBalanceResponseDTO;
import pl.marekbojdys.bankmanagement.enums.OperationType;

import java.math.BigDecimal;

public interface BankOperationService {


    ChangeBalanceResponseDTO changeBankAccountBalance(String uuid,
                                                      BigDecimal amount,
                                                      OperationType operationType);
}
