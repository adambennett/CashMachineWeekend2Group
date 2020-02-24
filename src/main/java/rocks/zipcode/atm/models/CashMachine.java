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
    private String loginPass = "";

    public CashMachine(Bank bank) {
        this.allAccounts = new ArrayList<>();
        this.bank = bank;
        this.currentUser = null;
        this.addAccount(new BasicAccount("Adam", "adam", 0, "pass", false, bank), "pass");
        this.addAccount(new BasicAccount("Zanetta", "zanetta", 0, "pass", false, bank), "pass");
        this.addAccount(new BasicAccount("Khalil", "khalil", 0, "pass", false, bank), "pass");
        this.addAccount(new BasicAccount("Ujjwal", "ujjwal", 0, "pass", false, bank), "pass");
        this.addAccount(new BasicAccount("Admin", "admin", 1000000, "root", true, bank), "root");
    }

    public Boolean login(String email, String password) {
        for (Account acc : this.allAccounts) {
            if (acc.getEmail().equals(email) && this.bank.validLogin(acc, password)) {
                this.currentUser = acc;
                this.loggedIn = true;
                this.loginPass = password;
                CashMachineApp.updateMenus();
                return true;
            }
        }
        return false;
    }

    public void deposit(float amt, Account acc) {
        acc.deposit(amt);
    }

    public Boolean withdraw(float amt, Account acc) {
        if (this.bank.validLogin(acc, loginPass)) {
            return acc.withdraw(amt);
        }
        return false;
    }

    public void logout() {
        this.currentUser = null;
        this.loggedIn = false;
        this.loginPass = "";
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

    public void addAccount(Account acc, String password) {
        this.bank.addAccountToBank(acc, password);
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

    public Account getCurrentUser() {
        return currentUser;
    }

    public Boolean isLoggedIn() {
        return loggedIn;
    }
}
