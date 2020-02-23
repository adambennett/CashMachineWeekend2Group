package rocks.zipcode.atm.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<String, Account> accounts;
    private Map<Account, String> passWordMap;

    public Bank() {
        this.accounts = new HashMap<>();
        this.passWordMap = new HashMap<>();
    }

    public Account getAccountByEmail(String email) {
        return (accounts.containsKey(email)) ? accounts.get(email) : null;
    }

    public Boolean deposit(float amount, Account acc) {
        if (accounts.containsKey(acc.getEmail())) {
            accounts.get(acc.getEmail()).deposit(amount);
            return true;
        }
        return false;
    }

    public Boolean withdraw(float amount, Account acc) {
        if (accounts.containsKey(acc.getEmail())) {
            Account account = accounts.get(acc.getEmail());
            return account.withdraw(amount);
        }
        return false;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public Map<Account, String> getPassWordMap() {
        return passWordMap;
    }

    public void addAccountToBank(Account account) {
        accounts.put(account.getEmail(), account);
        passWordMap.put(account, account.getPassword());
    }
}
