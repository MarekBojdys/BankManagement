package pl.marekbojdys.bankmanagement.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {

    private String uuid;
    private String firstName;
    private String lastName;
    private AtomicInteger balance;
    private List<OperationHistory> operationHistory = new ArrayList<>();

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

    public AtomicInteger getBalance() {
        return balance;
    }

    public void setBalance(final AtomicInteger balance) {
        this.balance = balance;
    }

    public List<OperationHistory> getOperationHistory() {
        return operationHistory;
    }

    public void setOperationHistory(final List<OperationHistory> operationHistory) {
        this.operationHistory = operationHistory;
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
                Objects.equals(operationHistory, that.operationHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, firstName, lastName, balance, operationHistory);
    }
}
