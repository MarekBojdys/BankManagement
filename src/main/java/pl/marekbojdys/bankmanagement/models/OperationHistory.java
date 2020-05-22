package pl.marekbojdys.bankmanagement.models;

import java.time.OffsetDateTime;
import java.util.Objects;

public class OperationHistory {

    private String operationType;
    private Integer amount;
    private OffsetDateTime operationTime;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(final String operationType) {
        this.operationType = operationType;
    }

    public OffsetDateTime getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(final OffsetDateTime operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OperationHistory that = (OperationHistory) o;
        return Objects.equals(operationType, that.operationType) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(operationTime, that.operationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, amount, operationTime);
    }
}
