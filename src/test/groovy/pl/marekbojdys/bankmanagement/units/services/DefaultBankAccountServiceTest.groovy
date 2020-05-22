package pl.marekbojdys.bankmanagement.units.services

import pl.marekbojdys.bankmanagement.enums.OperationType
import pl.marekbojdys.bankmanagement.exceptions.BadRequestException
import pl.marekbojdys.bankmanagement.exceptions.NotFoundException
import pl.marekbojdys.bankmanagement.models.BankAccount
import pl.marekbojdys.bankmanagement.repositories.BankAccountRepository
import pl.marekbojdys.bankmanagement.repositories.impl.InMemoryDatabaseRepository
import pl.marekbojdys.bankmanagement.services.BankAccountService
import pl.marekbojdys.bankmanagement.services.impl.DefaultBankAccountService
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger

class DefaultBankAccountServiceTest extends Specification {

    private static final String BANK_ACCOUNT_NOT_FOUND_WITH_UUID = "Bank account not found with uuid: ";
    private static final String AMOUNT_CAN_T_BE_LESS_THAN_1 = "Amount can't be less than 1";
    private static final int UUID_LENGTH = 36
    private static final String TEST_FIRST_NAME = "testFirstName"
    private static final String TEST_LAST_NAME = "testLastName"
    private static final int AMOUNT = 1000
    private static final int INITIAL_AMOUNT = 0
    private static final int INCORRECT_AMOUNT = 0
    private BankAccountRepository bankAccountRepository
    private BankAccountService bankAccountService

    def setup() {
        bankAccountRepository = Mock(InMemoryDatabaseRepository.class)
        bankAccountService = new DefaultBankAccountService(bankAccountRepository)
    }

    def getBankAccount(){
        def bankAccount = new BankAccount()
        bankAccount.setFirstName(TEST_FIRST_NAME)
        bankAccount.setLastName(TEST_LAST_NAME)
        bankAccount.setBalance(new AtomicInteger(INITIAL_AMOUNT))
        return bankAccount
    }

    def "Add user should return user with filled uuid"() {
        given: "BankAccount"
        def bankAccount = getBankAccount()

        when: "The service runs method 'addBankAccount'"
        bankAccountRepository.save(_ as BankAccount) >> { arg -> arg[0] }
        def savedBankAccount = bankAccountService.addBankAccount(bankAccount)

        then: "Returned bankAccount should have uuid"
        savedBankAccount.getUuid() != null
        savedBankAccount.getUuid().length() == UUID_LENGTH
    }

    def "Get BankAccount which doesn't exist should throw exception"() {
        given: "The BankAccount which doesn't exist"
        def uuid = "123"
        bankAccountRepository.findByUuid(uuid) >> Optional.empty()

        when: "The service runs method 'getBankAccount'"
        bankAccountService.getBankAccount(uuid)

        then: "NotFoundException should be thrown"
        def exception = thrown(NotFoundException)
        exception.message == BANK_ACCOUNT_NOT_FOUND_WITH_UUID + uuid
    }

    def "Change BankAccount balance should add amount to balance and add OperationHistory"() {
        given: "BankAccount"
        def uuid = "123"
        def bankAccount = getBankAccount()
        bankAccount.setUuid(uuid)
        bankAccountRepository.findByUuid(uuid) >> Optional.of(bankAccount)

        and: "Operation type add"
        def operationType = OperationType.ADD

        when: "The service runs method 'changeBankAccountBalance'"
        def balance = bankAccountService.changeBankAccountBalance(uuid, AMOUNT, operationType)
        def updatedBankAccount = bankAccountService.getBankAccount(uuid)

        then: "Result balance should be 10.00"
        balance == "10.00"

        and: "Operation history should be added"
        updatedBankAccount.getOperationHistory().size() == 1
        updatedBankAccount.getOperationHistory().get(0).getOperationType() == operationType.getSign()
        updatedBankAccount.getOperationHistory().get(0).getOperationTime() != null
        updatedBankAccount.getOperationHistory().get(0).getAmount() == AMOUNT
    }

    def "Change BankAccount balance should minus amount to balance and add OperationHistory"() {
        given: "BankAccount"
        def uuid = "123"
        def bankAccount = getBankAccount()
        bankAccount.setUuid(uuid)
        bankAccountRepository.findByUuid(uuid) >> Optional.of(bankAccount)

        and: "Operation type minus"
        def operationType = OperationType.MINUS

        when: "The service runs method 'changeBankAccountBalance'"
        def balance = bankAccountService.changeBankAccountBalance(uuid, AMOUNT, operationType)
        def updatedBankAccount = bankAccountService.getBankAccount(uuid)

        then: "Result balance should be -10.00"
        balance == "-10.00"

        and: "Operation history should be added"
        updatedBankAccount.getOperationHistory().size() == 1
        updatedBankAccount.getOperationHistory().get(0).getOperationType() == operationType.getSign()
        updatedBankAccount.getOperationHistory().get(0).getOperationTime() != null
        updatedBankAccount.getOperationHistory().get(0).getAmount() == AMOUNT
    }

    def "Change BankAccount balance should throw exception when amount is less than 1"() {
        when: "The service runs method 'changeBankAccountBalance'"
        bankAccountService.changeBankAccountBalance(null, INCORRECT_AMOUNT, null)

        then: "BadRequestException should be thrown"
        def exception = thrown(BadRequestException)
        exception.message == AMOUNT_CAN_T_BE_LESS_THAN_1
    }
}

