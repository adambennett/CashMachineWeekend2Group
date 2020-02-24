package rocks.zipcode.atm.models;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void deposit() {
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        PremiumAccount newAccount2 = new PremiumAccount("John", "khalilcrumpler@hotmail.com", 2000, "abc123", bank);
        float depositAmount = 200;
        //when
        float expected = 1200;
        float premiumExpected = 2200;
        newAccount.deposit(depositAmount);
        newAccount2.deposit(depositAmount);
        float actual = newAccount.getBalance();
        float actual2 = newAccount2.getBalance();
        //then
        Assert.assertEquals(expected, actual, 0);
        Assert.assertEquals(premiumExpected,actual2, 0);
    }

    @Test
    public void withdraw() {
        //given
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        PremiumAccount newAccount2 = new PremiumAccount("John", "khalilcrumpler@hotmail.com", 2000, "abc123", bank);
        float withdrawAmount = 300;
        //when
        newAccount.withdraw(withdrawAmount);
        newAccount2.withdraw(withdrawAmount);
        float expected = 700;
        float premiumExpected = 1700;
        float actual = newAccount.getBalance();
        float premiumActual = newAccount2.getBalance();

        //then
        Assert.assertEquals(expected, actual, 0);
        Assert.assertEquals(premiumExpected, premiumActual, 0);
    }


    @Test
    public void testEquals() {
        //given
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        BasicAccount newAccount2 = new BasicAccount("Khalil", "khalilcrumpler@hotmail.com", 1000, "abc123", bank);
        BasicAccount newAccount3 = new BasicAccount("John", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        //when
        boolean actual = newAccount.equals(newAccount2);
        boolean actual2 = newAccount.equals(newAccount3);
        //then
        assertFalse(actual);
        assertTrue(actual2);
    }

    @Test
    public void canWithdraw() {
        //given
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        PremiumAccount newAccount2 = new PremiumAccount("John", "khalilcrumpler@hotmail.com", 2000, "abc123", bank);
        float withdrawAmount = 1100;

        //when
        boolean basicActual = newAccount.canWithdraw(withdrawAmount);
        boolean premiumActual = newAccount2.canWithdraw(withdrawAmount);

        //then
        // basic can not withdraw(account is overdrawn) but premium can
        assertFalse(basicActual);
        assertTrue(premiumActual);
    }

    @Test
    public void getBalance() {
        //given
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        PremiumAccount newAccount2 = new PremiumAccount("John", "khalilcrumpler@hotmail.com", 2000, "abc123", bank);
        float basicBalance = 1000;
        float premiumBalance = 2000;

        //when
        float basicActual = newAccount.getBalance();
        float premiumActual = newAccount2.getBalance();

        //then
        Assert.assertEquals(basicBalance, basicActual, 0);
        Assert.assertEquals(premiumBalance, premiumActual, 0);

    }

    @Test
    public void getName() {
        //given
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        PremiumAccount newAccount2 = new PremiumAccount("John", "khalilcrumpler@hotmail.com", 2000, "abc123", bank);

        //when
        String basicName = "Khalil";
        String premiumName = "John";
        String basicActual = newAccount.getName();
        String premiumActual = newAccount2.getName();
        //then
        Assert.assertEquals(basicName,basicActual);
        Assert.assertEquals(premiumName,premiumActual);
    }

    @Test
    public void getEmail() {
        //given
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", bank);
        PremiumAccount newAccount2 = new PremiumAccount("John", "khalilcrumpler@hotmail.com", 2000, "abc123", bank);
        String expectedEmail = "khalilcrumpler@gmail.com";
        String expectedEmail2 = "khalilcrumpler@hotmail.com";

        //when
        String actualEmail = newAccount.getEmail();
        String actualEmail2 = newAccount2.getEmail();

        //then
        Assert.assertEquals(expectedEmail, actualEmail);
        Assert.assertEquals(expectedEmail2, actualEmail2);
    }

    @Test
    public void isAdmin() {
        //given
        Bank bank = new Bank();
        BasicAccount newAccount = new BasicAccount("Khalil", "khalilcrumpler@gmail.com", 1000, "abc123", true, bank);
        PremiumAccount newAccount2 = new PremiumAccount("John", "khalilcrumpler@hotmail.com", 2000, "abc123", false, bank);

        //when
        boolean actualBasic = newAccount.isAdmin();
        boolean actualPremium = newAccount2.isAdmin();

        //then
        assertTrue(actualBasic);
        assertFalse(actualPremium);

    }
}