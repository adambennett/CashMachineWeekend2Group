package rocks.zipcode.atm.models;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {

    private String name;
    private String email;
    private AccountType type;
    private Float balance;
    private Boolean isAdmin;

    public enum AccountType {
        BASIC,
        PREMIUM
    }

   public Account(String name, String email, float balance, String pass, Bank bank) {
       this(name, email, balance, AccountType.BASIC, pass, bank);
    }

    public Account(String name, String email, float balance, AccountType type, String pass, Bank bank) {
        this(name, email, balance, type, pass, false, bank);
    }

    public Account(String name, String email, float balance, AccountType type, String pass, boolean admin, Bank bank) {
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.type = type;
        this.isAdmin = admin;
        bank.addAccountToBank(this, pass);
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

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
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

    public Boolean isAdmin() {
        return isAdmin;
    }
}
