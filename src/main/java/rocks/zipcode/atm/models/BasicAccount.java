package rocks.zipcode.atm.models;

/**
 * @author ZipCodeWilmington
 */
public class BasicAccount extends Account {

    public BasicAccount(String name, String email, float balance,String pass) {
        super(name, email, balance, pass);
    }

    public BasicAccount(String name, String email, float balance,String pass, boolean admin) {
        super(name, email, balance, AccountType.BASIC, pass, admin);
    }
}
