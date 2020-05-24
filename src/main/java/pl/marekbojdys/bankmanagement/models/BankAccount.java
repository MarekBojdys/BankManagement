package pl.marekbojdys.bankmanagement.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class BankAccount {

    private String uuid;
    private String firstName;
    private String lastName;
    private AtomicReference<BigDecimal> balance;
    private List<Operation> operationsHistory = Collections.synchronizedList(new ArrayList<>());

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public AtomicReference<BigDecimal> getBalance() {
        return balance;
    }

    public void setBalance(final AtomicReference<BigDecimal> balance) {
        this.balance = balance;
    }

    public List<Operation> getOperationsHistory() {
        return operationsHistory;
    }

    public void setOperationsHistory(final List<Operation> operationsHistory) {
        this.operationsHistory = operationsHistory;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BankAccount that = (BankAccount) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(operationsHistory, that.operationsHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, firstName, lastName, balance, operationsHistory);
    }
}
