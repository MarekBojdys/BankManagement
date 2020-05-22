package pl.marekbojdys.bankmanagement.dtos;


public class BankAccountAddRequestDTO {

    private String firstName;
    private String lastName;
    private Integer balance;

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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(final Integer balance) {
        this.balance = balance;
    }
}
