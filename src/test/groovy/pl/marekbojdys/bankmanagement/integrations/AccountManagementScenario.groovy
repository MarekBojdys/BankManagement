package pl.marekbojdys.bankmanagement.integrations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pl.marekbojdys.bankmanagement.enums.OperationType
import pl.marekbojdys.bankmanagement.models.BankAccount
import pl.marekbojdys.bankmanagement.repositories.BankAccountRepository
import pl.marekbojdys.bankmanagement.services.BankAccountService
import pl.marekbojdys.bankmanagement.services.BankOperationService
import pl.marekbojdys.bankmanagement.services.impl.DefaultBankAccountService
import pl.marekbojdys.bankmanagement.services.impl.DefaultBankOperationService
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicReference

@SpringBootTest
@ActiveProfiles("integration-testing")
class AccountManagementScenario extends Specification{

    private static final String TEST_FIRST_NAME = "testFirstName"
    private static final String TEST_LAST_NAME = "testLastName"
    private static final int INITIAL_AMOUNT = 1000
    private static final int UUID_LENGTH = 36
    private static final BigDecimal AMOUNT_TO_ADD = new BigDecimal(1000)
    private static final BigDecimal AMOUNT_TO_MINUS = new BigDecimal(2000)
    private BankAccountService bankAccountService
    private BankOperationService bankOperationService

    @Autowired
    private BankAccountRepository bankAccountRepository

    @Shared
    private BankAccount bankAccountInDb

    def setup() {
        bankAccountService = new DefaultBankAccountService(bankAccountRepository)
        bankOperationService = new DefaultBankOperationService(bankAccountService)
    }

    def getBankAccount(){
        def newBankAccount = new BankAccount()
        newBankAccount.setFirstName(TEST_FIRST_NAME)
        newBankAccount.setLastName(TEST_LAST_NAME)
        newBankAccount.setBalance(new AtomicReference<BigDecimal>(new BigDecimal(INITIAL_AMOUNT)))
        return newBankAccount
    }

    def cleanupChanges(){
        bankAccountRepository.deleteAll()
    }

    def "Create BankAccount"() {
        given: "BankAccount"
        def bankAccountToCreate = getBankAccount()

        when: "addBankAccount"
        bankAccountInDb = bankAccountService.addBankAccount(bankAccountToCreate)

        then: "BankAccount should be added with uuid"
        bankAccountInDb.getUuid() != null
        bankAccountInDb.getUuid().length() == UUID_LENGTH
    }

    def "Get BankAccount from db"() {
        given: "uuid"
        def uuid = bankAccountInDb.getUuid()

        when: "addBankAccount"
        def bankAccountFromDb = bankAccountService.getBankAccount(uuid)

        then: "BankAccount should be added with uuid"
        bankAccountFromDb.getUuid() != null
        bankAccountFromDb.getFirstName() == TEST_FIRST_NAME
        bankAccountFromDb.getLastName() == TEST_LAST_NAME
    }

    def "Add amount to BankAccount balance"() {
        given: "uuid"
        def uuid = bankAccountInDb.getUuid()
        def startBalance = bankAccountInDb.getBalance().get()

        when: "add 1000 to account balance"
        def response = bankOperationService.changeBankAccountBalance(uuid, AMOUNT_TO_ADD, OperationType.ADD)

        then: "BankAccount balance should by increased by 1000 "
        startBalance + AMOUNT_TO_ADD == response.getCurrentBalance()

        and: "Operation history is added"
        def changedBankAccount = bankAccountService.getBankAccount(uuid)
        changedBankAccount.getOperationsHistory().size() == 1
        changedBankAccount.getOperationsHistory().get(0).getOperationType() == OperationType.ADD.getSign()
        changedBankAccount.getOperationsHistory().get(0).getAmount() == AMOUNT_TO_ADD
    }

    def "Minus amount to BankAccount balance"() {
        given: "uuid"
        def uuid = bankAccountInDb.getUuid()
        def startBalance = bankAccountInDb.getBalance().get()

        when: "add 1000 to account balance"
        def response = bankOperationService.changeBankAccountBalance(uuid, AMOUNT_TO_MINUS, OperationType.WITHDRAW)

        then: "BankAccount balance should by decreased by 2000 "
        startBalance - AMOUNT_TO_MINUS == response.getCurrentBalance()

        and: "Operation history is added"
        def changedBankAccount = bankAccountService.getBankAccount(uuid)
        changedBankAccount.getOperationsHistory().size() == 2
        changedBankAccount.getOperationsHistory().get(1).getOperationType() == OperationType.WITHDRAW.getSign()
        changedBankAccount.getOperationsHistory().get(1).getAmount() == AMOUNT_TO_MINUS
    }
}
