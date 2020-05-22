package pl.marekbojdys.bankmanagement.dtos;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class BankAccountAddRequestDTO {

    @NotBlank(message = "First name can not be blank")
    private String firstName;
    @NotBlank(message = "Last name can not be blank")
    private String lastName;
    @Min(value = 0, message = "Initial balance can not be less than 0")
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
