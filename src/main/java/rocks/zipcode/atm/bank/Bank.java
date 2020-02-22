package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<String, Account> accounts = new HashMap<>();
    private Map<Account, String> passWordMap = new HashMap<>();

    public Bank() {

    }


    public ActionResult<AccountData> getAccountByEmail(String email) {
        Account account = accounts.get(email);

        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("No account with email: " + email + "\nTry account 1000 or 2000");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, float amount) {
        Account account = accounts.get(accountData.getEmail());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, float amount) {
        Account account = accounts.get(accountData.getEmail());
        boolean ok = account.withdraw(amount);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + account.getBalance());
        }
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public Map<Account, String> getPassWordMap() {
        return passWordMap;
    }

    public void addAccountToBank(Account account) {
        accounts.put(account.getAccountData().getEmail(), account);
        passWordMap.put(account, account.getAccountData().getPassword());
    }
}
