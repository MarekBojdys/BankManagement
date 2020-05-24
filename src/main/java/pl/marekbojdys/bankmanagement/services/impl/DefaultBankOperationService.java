package pl.marekbojdys.bankmanagement.services.impl;

import org.springframework.stereotype.Service;
import pl.marekbojdys.bankmanagement.dtos.ChangeBalanceResponseDTO;
import pl.marekbojdys.bankmanagement.enums.OperationType;
import pl.marekbojdys.bankmanagement.exceptions.BadRequestException;
import pl.marekbojdys.bankmanagement.models.BankAccount;
import pl.marekbojdys.bankmanagement.models.Operation;
import pl.marekbojdys.bankmanagement.services.BankAccountService;
import pl.marekbojdys.bankmanagement.services.BankOperationService;
import pl.marekbojdys.bankmanagement.utils.CurrencyCalcUtil;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DefaultBankOperationService implements BankOperationService {

    private static final String AMOUNT_HAS_TO_BE_MORE_THAN_0 = "Amount has to be more than 0";
    private static final String MINIMAL_AMOUNT = "0.01";
    private final BankAccountService bankAccountService;

    public DefaultBankOperationService(final BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public ChangeBalanceResponseDTO changeBankAccountBalance(final String uuid, final BigDecimal amount, final OperationType operationType) {
        final BigDecimal standardizedAmount = CurrencyCalcUtil.standardizeAmount(amount);
        if (standardizedAmount.compareTo(new BigDecimal(MINIMAL_AMOUNT)) < 0) {
            throw new BadRequestException(AMOUNT_HAS_TO_BE_MORE_THAN_0);
        }
        final BankAccount bankAccount = bankAccountService.getBankAccount(uuid);
        final AtomicReference<BigDecimal> balance = bankAccount.getBalance();
        final BigDecimal actualBalance = updateBankAccountBalance(standardizedAmount, operationType, balance);
        final Operation operation = createOperation(standardizedAmount, operationType);
        bankAccount.getOperationsHistory().add(operation);
        return createChangeBalanceResponseDTO(uuid, operationType, actualBalance);
    }

    private BigDecimal updateBankAccountBalance(final BigDecimal amount, final OperationType operationType,
                                                final AtomicReference<BigDecimal> balance) {
        final BigDecimal actualBalance;
        if (operationType == OperationType.ADD) {
            actualBalance = balance.accumulateAndGet(amount, BigDecimal::add);
        } else {
            actualBalance = balance.accumulateAndGet(amount, BigDecimal::subtract);
        }
        return actualBalance;
    }

    private Operation createOperation(final BigDecimal amount, final OperationType operationType) {
        final Operation operation = new Operation();
        operation.setOperationType(operationType.getSign());
        operation.setAmount(amount);
        operation.setOperationTime(OffsetDateTime.now());
        return operation;
    }

    private ChangeBalanceResponseDTO createChangeBalanceResponseDTO(final String uuid, final OperationType operationType, final BigDecimal actualBalance) {
        final ChangeBalanceResponseDTO changeBalanceResponseDTO = new ChangeBalanceResponseDTO();
        changeBalanceResponseDTO.setUuid(uuid);
        changeBalanceResponseDTO.setOperationType(operationType);
        changeBalanceResponseDTO.setCurrentBalance(actualBalance);
        return changeBalanceResponseDTO;
    }
}
