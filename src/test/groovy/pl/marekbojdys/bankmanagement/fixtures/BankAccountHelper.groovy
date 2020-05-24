package pl.marekbojdys.bankmanagement.fixtures

import pl.marekbojdys.bankmanagement.models.BankAccount

import java.util.concurrent.atomic.AtomicReference

class BankAccountHelper {

    private static final String TEST_FIRST_NAME = "testFirstName"
    private static final String TEST_LAST_NAME = "testLastName"
    private static final int INITIAL_AMOUNT = 0
    private static final String TEST_UUID = "123"

    def static getBankAccountFixture(){
        def bankAccount = new BankAccount()
        bankAccount.setUuid(TEST_UUID)
        bankAccount.setFirstName(TEST_FIRST_NAME)
        bankAccount.setLastName(TEST_LAST_NAME)
        bankAccount.setBalance(new AtomicReference<BigDecimal>(new BigDecimal(INITIAL_AMOUNT)))
        return bankAccount
    }
}
