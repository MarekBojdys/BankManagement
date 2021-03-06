package pl.marekbojdys.bankmanagement.enums;

public enum OperationType {

    ADD("+"),
    WITHDRAW("-");

    private String sign;

    OperationType(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
