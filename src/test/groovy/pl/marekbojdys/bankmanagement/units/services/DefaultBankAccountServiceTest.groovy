package pl.marekbojdys.bankmanagement.units.services


import pl.marekbojdys.bankmanagement.exceptions.NotFoundException
import pl.marekbojdys.bankmanagement.fixtures.BankAccountHelper
import pl.marekbojdys.bankmanagement.models.BankAccount
import pl.marekbojdys.bankmanagement.repositories.BankAccountRepository
import pl.marekbojdys.bankmanagement.repositories.impl.InMemoryDatabaseRepository
import pl.marekbojdys.bankmanagement.services.BankAccountService
import pl.marekbojdys.bankmanagement.services.impl.DefaultBankAccountService
import spock.lang.Specification

class DefaultBankAccountServiceTest extends Specification {

    private static final String BANK_ACCOUNT_NOT_FOUND_WITH_UUID = "Bank account not found with uuid: ";
    private static final int UUID_LENGTH = 36
    private static final String NOT_EXISTED_UUID = "abcd"
    private BankAccountRepository bankAccountRepository
    private BankAccountService bankAccountService

    def setup() {
        bankAccountRepository = Mock(InMemoryDatabaseRepository.class)
        bankAccountService = new DefaultBankAccountService(bankAccountRepository)
    }

    def "Add user should return user with filled uuid"() {
        given: "BankAccount"
        def bankAccount = BankAccountHelper.getBankAccountFixture()

        when: "The service runs method 'addBankAccount'"
        bankAccountRepository.save(_ as BankAccount) >> { arg -> arg[0] }
        def savedBankAccount = bankAccountService.addBankAccount(bankAccount)

        then: "Returned bankAccount should have uuid"
        savedBankAccount.getUuid() != null
        savedBankAccount.getUuid().length() == UUID_LENGTH
    }

    def "Get BankAccount which doesn't exist should throw exception"() {
        given: "The BankAccount which doesn't exist"
        def uuid = NOT_EXISTED_UUID
        bankAccountRepository.findByUuid(uuid) >> Optional.empty()

        when: "The service runs method 'getBankAccount'"
        bankAccountService.getBankAccount(uuid)

        then: "NotFoundException should be thrown"
        def exception = thrown(NotFoundException)
        exception.message == BANK_ACCOUNT_NOT_FOUND_WITH_UUID + uuid
    }

}

