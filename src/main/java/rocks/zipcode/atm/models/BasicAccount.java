package rocks.zipcode.atm.models;

/**
 * @author ZipCodeWilmington
 */
public class BasicAccount extends Account {


    public BasicAccount(String name, String email, float balance,String pass, Bank bank) {
        super(name, email, balance, pass, bank);
    }

    public BasicAccount(String name, String email, float balance,String pass, boolean admin, Bank bank) {
        super(name, email, balance, AccountType.BASIC, pass, admin, bank);
    }
}
