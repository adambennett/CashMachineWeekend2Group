package rocks.zipcode.atm.models;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {



    @Test
    public void getAccountByEmail() {

        BasicAccount newAccount = new BasicAccount("khalil", "abc@hotmail.com", 1000, "abc123" );
        Bank newBank = new Bank();
        newBank.addAccountToBank(newAccount);
        Account expected = newBank.getAccountByEmail(newAccount.getEmail());
        Assert.assertEquals(expected, newAccount);

    }

    @Test
    public void deposit() {

        BasicAccount newAccount = new BasicAccount("khalil", "abc@hotmail.com", 1000, "abc123" );
        BasicAccount newAccount1 = new BasicAccount("khalil", "efg@hotmail.com", 1000, "abc123" );

        Bank newBank = new Bank();
        newBank.addAccountToBank(newAccount);

        boolean actual = newBank.deposit(500, newAccount);
        boolean actual1 = newBank.deposit(500, newAccount1);

        assertTrue(actual);
        assertFalse(actual1);

    }

    @Test
    public void withdraw() {

        BasicAccount newAccount = new BasicAccount("khalil", "abc@hotmail.com", 1000, "abc123" );
        BasicAccount newAccount1 = new BasicAccount("khalil", "efg@hotmail.com", 1000, "abc123" );

        Bank newBank = new Bank();
        newBank.addAccountToBank(newAccount);

        boolean actual = newBank.withdraw(500, newAccount);
        boolean actual1 = newBank.withdraw(500, newAccount1);

        assertTrue(actual);
        assertFalse(actual1);

    }

    @Test
    public void getAccounts() {

    


    }

    @Test
    public void getPassWordMap() {



    }

    @Test
    public void addAccountToBank() {

    }
}