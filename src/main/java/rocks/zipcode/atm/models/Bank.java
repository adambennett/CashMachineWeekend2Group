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

    public Boolean validLogin(Account acc, String password) {
        if (passWordMap.containsKey(acc)) {
            if (passWordMap.get(acc).equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void addAccountToBank(Account account, String password) {
        accounts.put(account.getEmail(), account);
        passWordMap.put(account, password);
    }
}
