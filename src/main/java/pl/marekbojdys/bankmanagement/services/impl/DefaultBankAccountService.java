package pl.marekbojdys.bankmanagement.services.impl;

import org.springframework.stereotype.Service;
import pl.marekbojdys.bankmanagement.enums.OperationType;
import pl.marekbojdys.bankmanagement.exceptions.BadRequestException;
import pl.marekbojdys.bankmanagement.exceptions.NotFoundException;
import pl.marekbojdys.bankmanagement.models.BankAccount;
import pl.marekbojdys.bankmanagement.models.OperationHistory;
import pl.marekbojdys.bankmanagement.repositories.BankAccountRepository;
import pl.marekbojdys.bankmanagement.services.BankAccountService;
import pl.marekbojdys.bankmanagement.utils.CurrencyCalcUtil;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DefaultBankAccountService implements BankAccountService {

    private static final String BANK_ACCOUNT_NOT_FOUND_WITH_UUID = "Bank account not found with uuid: ";
    private static final String AMOUNT_CAN_T_BE_LESS_THAN_1 = "Amount can't be less than 1";
    private static final int MINIMAL_AMOUNT = 1;
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

    @Override
    public String changeBankAccountBalance(final String uuid, final Integer amount, final OperationType operationType) {
        if(amount < MINIMAL_AMOUNT){
            throw new BadRequestException(AMOUNT_CAN_T_BE_LESS_THAN_1);
        }
        final BankAccount bankAccount = getBankAccount(uuid);
        final AtomicInteger balance = bankAccount.getBalance();
        final int actualAmount = updateBankAccountBalance(amount, operationType, balance);
        bankAccount.getOperationHistory().add(createOperationHistory(amount, operationType));
        return CurrencyCalcUtil.getAmountDividedBy100(actualAmount);
    }

    private int updateBankAccountBalance(final Integer amount, final OperationType operationType, final AtomicInteger balance) {
        final int actualAmount;
        if (operationType == OperationType.ADD) {
            actualAmount = balance.addAndGet(amount);
        } else {
            actualAmount = balance.addAndGet(-amount);
        }
        return actualAmount;
    }

    private OperationHistory createOperationHistory(final Integer amount, final OperationType operationType) {
        final OperationHistory operationHistory = new OperationHistory();
        operationHistory.setOperationType(operationType.getSign());
        operationHistory.setAmount(amount);
        operationHistory.setOperationTime(OffsetDateTime.now());
        return operationHistory;
    }
}
