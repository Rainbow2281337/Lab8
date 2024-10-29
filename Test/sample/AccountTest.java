package sample;

import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void testBankchargePremiumLessThanAWeek() {
        Account account = getPremiumAccount(5);
        assertThat(account.bankcharge(), is(14.5));
    }

    @Test
    public void testBankchargePremiumMoreThanAWeek() {
        Account account = getPremiumAccount(9);
        assertThat(account.bankcharge(), is(16.5));
    }

    @Test
    public void testOverdraftFeePremium() {
        Account account = getPremiumAccount(9);
        assertThat(account.overdraftFee(), is(0.10));
    }

    @Test
    public void testOverdraftFeeNotPremium() {
        Account account = getNormalAccount();
        assertThat(account.overdraftFee(), is(0.20));
    }

    @Test
    public void testPrintCustomer() {
        Account account = getNormalAccount();
        Customer customer = new Customer("xxx", "xxx", "xxx@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        assertThat(account.printCustomer(), is("xxx xxx@mail.com"));
    }

    private Account getNormalAccount() {
        // Використання enum для створення нормального рахунку
        AccountType normal = AccountType.NORMAL;

        // Створення об'єкта MoneyWithCurrency з нульовою сумою та валютою
        MoneyWithCurrency balance = new MoneyWithCurrency(0.0, "EUR");

        // Створення нормального рахунку
        return new Account(normal, 9, balance);
    }

    private Account getPremiumAccount(int daysOverdrawn) {
        // Використання enum для створення преміум рахунку
        AccountType premium = AccountType.PREMIUM;

        // Створення об'єкта MoneyWithCurrency з нульовою сумою та валютою
        MoneyWithCurrency balance = new MoneyWithCurrency(0.0, "EUR");

        // Створення преміум рахунку
        return new Account(premium, daysOverdrawn, balance);
    }
}