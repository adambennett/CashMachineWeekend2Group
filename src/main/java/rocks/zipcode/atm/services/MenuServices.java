package rocks.zipcode.atm.services;

import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.CashMachineApp;

import java.util.ArrayList;

public class MenuServices {

    private static ArrayList<Character> specialChars;

    public static void handleMenuListeners(Stage stage, Scene mainScene) {
        MenuItem login = CashMachineApp.getMenus().get(CashMachineApp.MenuType.LOGIN);
        MenuItem register = CashMachineApp.getMenus().get(CashMachineApp.MenuType.REGISTER);
        MenuItem withdraw = CashMachineApp.getMenus().get(CashMachineApp.MenuType.WITHDRAW);
        MenuItem deposit = CashMachineApp.getMenus().get(CashMachineApp.MenuType.DEPOSIT);
        MenuItem instruction = CashMachineApp.getMenus().get(CashMachineApp.MenuType.INSTRUCTION);
        MenuItem viewAccounts = CashMachineApp.getMenus().get(CashMachineApp.MenuType.ACCOUNTS);

        viewAccounts.setOnAction(e -> {
            Scene scene = new Scene(CashMachineApp.createViewAccounts(stage, mainScene));
            stage.setScene(scene);
        });

        login.setOnAction(e -> {
            if (!CashMachineApp.getCashMachine().isLoggedIn()) {
                Scene scene = new Scene(CashMachineApp.createLogin(stage, mainScene));
                stage.setScene(scene);
            } else {
                CashMachineApp.getCashMachine().logout();
                CashMachineApp.updateLogin();
            }
        });

        register.setOnAction(e -> {
            Scene scene = new Scene(CashMachineApp.createRegister(stage, mainScene));
            stage.setScene(scene);
        });

        withdraw.setOnAction(e -> {
            Scene scene = new Scene(CashMachineApp.createWithdraw(stage, mainScene));
            stage.setScene(scene);
        });

        deposit.setOnAction(e -> {
            Scene scene = new Scene(CashMachineApp.createDeposit(stage, mainScene));
            stage.setScene(scene);
        });

        instruction.setOnAction(e -> {
            Scene scene = new Scene(CashMachineApp.createInstruction(stage, mainScene));
            stage.setScene(scene);
        });
    }


    public static Boolean canRegister(String name, String email, String pass) {
        if (name.equals("")) {
            return false;
        } else if (email.equals("")) {
            return false;
        } else if (pass.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static Boolean passwordLogicCheck(String pass) {
        boolean foundSpecial = false;
        boolean foundUppercase = false;
        boolean foundLowercase = false;
        ArrayList<Character> chars = new ArrayList<>();
        for (char c : pass.toCharArray()) {
            chars.add(c);
            if (specialChars.contains(c)) {
                foundSpecial = true;
            }
            if (Character.isUpperCase(c)) {
                foundUppercase = true;
            } else {
                foundLowercase = true;
            }
        }
        return (!foundUppercase || !foundSpecial || pass.length() < 8 || !foundLowercase) ? false : true;
    }

    static {
        specialChars = new ArrayList<>();
        specialChars.add('!');
        specialChars.add('#');
        specialChars.add('$');
        specialChars.add('%');
        specialChars.add('&');
        specialChars.add(' ');
        specialChars.add('(');
        specialChars.add(')');
        specialChars.add('*');
        specialChars.add('+');
        specialChars.add(',');
        specialChars.add('-');
        specialChars.add('.');
        specialChars.add('/');
        specialChars.add(':');
        specialChars.add(';');
        specialChars.add('<');
        specialChars.add('=');
        specialChars.add('>');
        specialChars.add('?');
        specialChars.add('@');
        specialChars.add('[');
        specialChars.add(']');
        specialChars.add('^');
        specialChars.add('_');
        specialChars.add('`');
        specialChars.add('{');
        specialChars.add('|');
        specialChars.add('}');
        specialChars.add('~');
    }

}
