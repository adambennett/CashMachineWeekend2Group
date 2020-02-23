package rocks.zipcode.atm.models;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CashMachineTest {

    @Test
    public void login() {
        Bank bank = new Bank();
        CashMachine cashMachine = new CashMachine(bank);
        Assert.assertTrue(cashMachine.login("admin", "root"));
    }


    @Test
    public void deposit() {
    }

    @Test
    public void withdraw() {
    }

    @Test
    public void logout() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void getBank() {
    }

    @Test
    public void addAccount() {
    }

    @Test
    public void getAllAccounts() {
    }

    @Test
    public void getBalance() {
    }

    @Test
    public void getCurrentUser() {
    }

    @Test
    public void isLoggedIn() {
    }

}