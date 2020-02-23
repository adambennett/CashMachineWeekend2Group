package rocks.zipcode.atm.models;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {

    private String name;
    private String email;
    private AccountType type;
    private Float balance;
    private String password;
    private Boolean isAdmin;

    public enum AccountType {
        BASIC,
        PREMIUM
    }

   public Account(String name, String email, float balance, String pass) {
       this(name, email, balance, AccountType.BASIC, pass);
    }

    public Account(String name, String email, float balance, AccountType type, String pass) {
        this(name, email, balance, type, pass, false);
    }

    public Account(String name, String email, float balance, AccountType type, String pass, boolean admin) {
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.type = type;
        this.password = pass;
        this.isAdmin = admin;
    }

    public void deposit(float amount) {
        updateBalance(getBalance() + amount);
    }

    public Boolean withdraw(float amount) {
        if (canWithdraw(amount) || (this.isAdmin && amount > 0)) {
            updateBalance(getBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    private static DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");

    @Override
    public String toString() {
        df.setDecimalSeparatorAlwaysShown(true);
        df.setMinimumFractionDigits(2);
        BigDecimal bal = new BigDecimal(balance);
        if (balance == 0) {
            String value = df.format(0.0);
            return "Name: " + name + "\n"  +
                    "Email: " + email + "\n"  +
                    "Balance: $" + value;
        }
        else if (balance < 0) {
            //double rawPercent = ( (double)(balance) / (double)(balance) ) * 100.00;
            String value = df.format(bal.multiply(BigDecimal.valueOf(-1.0)));
            return "Name: " + name + "\n" +
                    "Email: " + email + "\n"  +
                    "Balance: -$" + value;


        } else {
            //double rawPercent = ( (double)(balance) / (double)(balance) ) * 100.00;
            String value = df.format(bal);
            return "Name: " + name + "\n"  +
                    "Email: " + email + "\n"  +
                    "Balance: $" + value;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Account) {
            if (((Account) o).getEmail().equals(this.email)) {
                return true;
            } else {
                return false;
            }
        } else {
            return super.equals(o);
        }
    }

    public Boolean canWithdraw(float amount) { return (amount < 0) ? false : getBalance() >= amount;
    }

    public Float getBalance() {
        return this.balance;
    }

    private void updateBalance(float newBalance) {
        this.balance = newBalance;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
