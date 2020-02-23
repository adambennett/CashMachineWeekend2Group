package rocks.zipcode.atm.models;

/**
 * @author ZipCodeWilmington
 */
public class PremiumAccount extends Account {

    private static final int OVERDRAFT_LIMIT = 100;

    public PremiumAccount(String name, String email, float balance, String pass) {
        super(name, email, balance, AccountType.PREMIUM, pass);
    }

    public PremiumAccount(String name, String email, float balance, String pass, boolean admin) {
        super(name, email, balance, AccountType.PREMIUM, pass, admin);
    }

    public static Integer getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }


    @Override
    public Boolean canWithdraw(float amount) {
        return getBalance() + OVERDRAFT_LIMIT >= amount;
    }
}
