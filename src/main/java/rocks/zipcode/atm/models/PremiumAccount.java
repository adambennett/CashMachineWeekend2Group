package rocks.zipcode.atm.models;

/**
 * @author ZipCodeWilmington
 */
public class PremiumAccount extends Account {

    private static final int OVERDRAFT_LIMIT = 100;

    public PremiumAccount(String name, String email, float balance, String pass, Bank bank) {
        super(name, email, balance, AccountType.PREMIUM, pass, bank);
    }

    public PremiumAccount(String name, String email, float balance, String pass, boolean admin, Bank bank) {
        super(name, email, balance, AccountType.PREMIUM, pass, admin, bank);
    }

    @Override
    public Boolean canWithdraw(float amount) {
        return getBalance() + OVERDRAFT_LIMIT >= amount;
    }
}
