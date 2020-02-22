package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public final class AccountData {

    private final int id;
    private final String name;
    private final String email;
    private AccountType type;
    private final Float balance;

    AccountData(int id, String name, String email, float balance) {
        this(id, name, email, balance, AccountType.BASIC);
    }

    AccountData(int id, String name, String email, float balance, AccountType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.type = type;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "Account id: " + id + '\n' +
                "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Balance: " + balance;
    }

    public enum AccountType {
        BASIC,
        PREMIUM
    }
}
