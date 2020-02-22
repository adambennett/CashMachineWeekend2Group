package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public final class AccountData {

    private final String name;
    private final String email;
    private AccountType type;
    private final Float balance;
    private final String password;

    public AccountData(String name, String email, float balance, String pass) {
        this(name, email, balance, AccountType.BASIC, pass);
    }

    public AccountData(String name, String email, float balance, AccountType type, String pass) {
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.type = type;
        this.password = pass;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Float getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Balance: " + balance;
    }

    public enum AccountType {
        BASIC,
        PREMIUM
    }
}
