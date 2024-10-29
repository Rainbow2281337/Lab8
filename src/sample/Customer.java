package sample;

public class Customer {

    private String name;
    private String surname;
    private String email;
    private CustomerType customerType;
    private Account account;
    private double companyOverdraftDiscount = 1;


    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    // use only to create companies
    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.COMPANY;
        this.account = account;
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public void withdraw(double sum, String currency) {
        // Validate the currency to ensure it matches the account's currency
        validateCurrency(currency);

        // Calculate the total amount to withdraw, including any fees if the account is overdrawn
        double amountToWithdraw = calculateAmountToWithdraw(sum);

        // Deduct the calculated amount from the account's balance
        account.setMoney(account.getMoney() - amountToWithdraw);
    }

    private void validateCurrency(String currency) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
    }

    private double calculateAmountToWithdraw(double sum) {
        boolean isOverdrawn = account.getMoney() < 0;
        double fee = account.overdraftFee();
        double discountFactor = getDiscountFactor();

        return sum + (isOverdrawn ? sum * fee * discountFactor : 0);
    }

    private double getDiscountFactor() {
        if (customerType == CustomerType.COMPANY && account.getType().isPremium()) {
            return 0.5; // 50% discount for company on premium account
        }
        return 1.0; // No discount
    }


    public String getFullName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String printCustomerDaysOverdrawn() {
        String fullName = name + " " + surname + " ";
        String accountDescription = "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
        return fullName + accountDescription;
    }

    public String printCustomerMoney() {
        String fullName = name + " " + surname + " ";
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + account.getIban() + ", Money: " + account.getMoney();
        return fullName + accountDescription;
    }

    public String printCustomerAccount() {
        return "Account: IBAN: " + account.getIban() + ", Money: "
                + account.getMoney() + ", Account type: " + account.getType();
    }
}
