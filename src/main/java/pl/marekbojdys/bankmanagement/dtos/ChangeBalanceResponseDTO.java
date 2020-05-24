package pl.marekbojdys.bankmanagement.dtos;

import pl.marekbojdys.bankmanagement.enums.OperationType;

import java.math.BigDecimal;
import java.util.Objects;

public class ChangeBalanceResponseDTO {

    private String uuid;
    private OperationType operationType;
    private BigDecimal currentBalance;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(final OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(final BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ChangeBalanceResponseDTO that = (ChangeBalanceResponseDTO) o;
        return Objects.equals(uuid, that.uuid) &&
                operationType == that.operationType &&
                Objects.equals(currentBalance, that.currentBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, operationType, currentBalance);
    }
}
