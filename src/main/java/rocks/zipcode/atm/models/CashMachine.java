package rocks.zipcode.atm.models;

import rocks.zipcode.atm.controllers.CashMachineApp;

import java.util.ArrayList;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {

    private final Bank bank;
    private ArrayList<Account> allAccounts;
    private Account currentUser;
    private Boolean loggedIn = false;
    private Boolean isAdmin = false;

    public CashMachine(Bank bank) {
        this.allAccounts = new ArrayList<>();
        this.bank = bank;
        this.currentUser = null;
    }

    public Boolean login(String email) {
        for (Account acc : this.allAccounts) {
            if (acc.getEmail().equals(email)) {
                this.currentUser = acc;
                this.loggedIn = true;
                CashMachineApp.updateMenus();
            }
        }
        return false;
    }

    public Boolean deposit(float amt, Account acc) {
        if (this.currentUser != null && loggedIn) {
            this.currentUser.deposit(amt);
        }
        return false;
    }

    public Boolean withdraw(float amt, Account acc) {
        if (this.currentUser != null && loggedIn) {
            this.currentUser.withdraw(amt);
        }
        return false;
    }

    public void logout() {
        this.currentUser = null;
        this.loggedIn = false;
        CashMachineApp.updateMenus();
    }

    @Override
    public String toString() {
        if (this.currentUser == null) {
            return "You must be logged in!";
        } else {
            return this.currentUser.toString();
        }
    }

    public Bank getBank() {
        return bank;
    }

    public void addAccount(Account acc) {
        this.bank.addAccountToBank(acc);
        this.allAccounts.add(acc);
    }

    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public Float getBalance() {
        if (!this.loggedIn) {
            return 0.0f;
        }
        return this.currentUser.getBalance();
    }

    public Boolean premiumAccount() {
        if (this.currentUser != null && this.loggedIn) {
            return this.currentUser.getType().equals(Account.AccountType.PREMIUM);
        }
        return false;
    }

    public Boolean canWithdraw(float amt) {
        if (this.currentUser != null && this.loggedIn) {
            return this.currentUser.canWithdraw(amt);
        }
        return false;
    }

    public Account getCurrentUser() {
        return currentUser;
    }

    public Boolean isLoggedIn() {
        return loggedIn;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
