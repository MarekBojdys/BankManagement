package pl.marekbojdys.bankmanagement.units.services

import pl.marekbojdys.bankmanagement.enums.OperationType
import pl.marekbojdys.bankmanagement.exceptions.BadRequestException
import pl.marekbojdys.bankmanagement.fixtures.BankAccountHelper
import pl.marekbojdys.bankmanagement.services.BankAccountService
import pl.marekbojdys.bankmanagement.services.BankOperationService
import pl.marekbojdys.bankmanagement.services.impl.DefaultBankAccountService
import pl.marekbojdys.bankmanagement.services.impl.DefaultBankOperationService
import spock.lang.Specification

class DefaultBankOperationServiceTest extends Specification{

    private static final String AMOUNT_HAS_TO_BE_MORE_THAN_0 = "Amount has to be more than 0";
    private static final BigDecimal AMOUNT = new BigDecimal("10.00")
    private static final BigDecimal INCORRECT_AMOUNT = new BigDecimal("0")

    private BankAccountService bankAccountService
    private BankOperationService bankOperationService

    def setup() {
        bankAccountService = Mock(DefaultBankAccountService)
        bankOperationService = new DefaultBankOperationService(bankAccountService)
    }


    def "Change BankAccount balance should add amount to balance and add OperationHistory"() {
        given: "BankAccount"
        def bankAccount = BankAccountHelper.getBankAccountFixture()
        def uuid = bankAccount.getUuid()
        bankAccountService.getBankAccount(uuid) >> bankAccount

        and: "Operation type add"
        def operationType = OperationType.ADD

        when: "The service runs method 'changeBankAccountBalance'"
        def balance = bankOperationService.changeBankAccountBalance(uuid, AMOUNT, operationType)
        def updatedBankAccount = bankAccountService.getBankAccount(uuid)

        then: "Result balance should be 10.00"
        balance.getCurrentBalance() == new BigDecimal("10.00")

        and: "Operation history should be added"
        updatedBankAccount.getOperationsHistory().size() == 1
        updatedBankAccount.getOperationsHistory().get(0).getOperationType() == operationType.getSign()
        updatedBankAccount.getOperationsHistory().get(0).getOperationTime() != null
        updatedBankAccount.getOperationsHistory().get(0).getAmount() == AMOUNT
    }

    def "Change BankAccount balance should minus amount to balance and add OperationHistory"() {
        given: "BankAccount"
        def bankAccount = BankAccountHelper.getBankAccountFixture()
        def uuid = bankAccount.getUuid()
        bankAccountService.getBankAccount(uuid) >> bankAccount

        and: "Operation type minus"
        def operationType = OperationType.WITHDRAW

        when: "The service runs method 'changeBankAccountBalance'"
        def balance = bankOperationService.changeBankAccountBalance(uuid, AMOUNT, operationType)
        def updatedBankAccount = bankAccountService.getBankAccount(uuid)

        then: "Result balance should be -10.00"
        balance.getCurrentBalance() ==  new BigDecimal("-10.00")

        and: "Operation history should be added"
        updatedBankAccount.getOperationsHistory().size() == 1
        updatedBankAccount.getOperationsHistory().get(0).getOperationType() == operationType.getSign()
        updatedBankAccount.getOperationsHistory().get(0).getOperationTime() != null
        updatedBankAccount.getOperationsHistory().get(0).getAmount() == AMOUNT
    }

    def "Change BankAccount balance should throw exception when amount is less than 1"() {
        when: "The service runs method 'changeBankAccountBalance'"
        bankOperationService.changeBankAccountBalance(null, INCORRECT_AMOUNT, null)

        then: "BadRequestException should be thrown"
        def exception = thrown(BadRequestException)
        exception.message == AMOUNT_HAS_TO_BE_MORE_THAN_0
    }
}
