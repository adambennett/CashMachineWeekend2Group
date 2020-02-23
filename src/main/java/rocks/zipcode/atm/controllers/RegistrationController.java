package rocks.zipcode.atm.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rocks.zipcode.atm.models.Account;
import rocks.zipcode.atm.models.BasicAccount;
import rocks.zipcode.atm.models.PremiumAccount;

import java.util.logging.Logger;

public class RegistrationController {

    private static Logger logger = Logger.getLogger(RegistrationController.class.getName());

    public static void setupListeners(Stage primaryStage, Scene oldScene, Button returnBtn, Button register, CheckBox admin, TextField emailField, Label userExists, Label notEnoughInfo, Label badPass, ComboBox accountBox, TextField userTextField, TextField balanceField, PasswordField pwBox) {
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        returnBtn.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        returnBtn.fire();
                    }
                }
        );

        admin.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        admin.setSelected(!admin.isSelected());
                    }
                }
        );

        register.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        register.fire();
                    }
                }
        );

        register.setOnAction(e -> {
            String newEmail = emailField.getText();

            boolean found = false;
            for (Account acc : CashMachineApp.getCashMachine().getAllAccounts()) {
                if (acc.getEmail().equals(newEmail)) {
                    found = true;
                    break;
                }
            }


            try {
                if (!found) {
                    userExists.setVisible(false);
                    notEnoughInfo.setVisible(false);
                    badPass.setVisible(false);
                    String newName = userTextField.getText();
                    String newAccount = accountBox.getValue().toString();
                    String startBal = balanceField.getText();
                    String pass = pwBox.getText();
                    Boolean adminBox = admin.isSelected();
                    Float newBal = 0.0f;
                    try {
                        newBal = Float.parseFloat(startBal);
                    } catch (NumberFormatException ex) {}

                    Boolean canRegister = MainMenuController.canRegister(newName, newEmail, pass);
                    Boolean passPasses = MainMenuController.passwordLogicCheck(pass);
                    if (canRegister) {
                        if (passPasses) {
                            Account.AccountType newType = Account.AccountType.BASIC;
                            if (newAccount.equals("Premium Account")) {
                                newType = Account.AccountType.PREMIUM;
                            }
                            if (adminBox) {
                                RegistrationController.handleNewUser(newName, newEmail, pass, newBal, newType, true);
                            } else {
                                RegistrationController.handleNewUser(newName, newEmail, pass, newBal, newType, false);
                            }
                            primaryStage.setScene(oldScene);
                            Logger.getGlobal().info("\n\nRegistration successful. Registered: " + newEmail);
                        } else {
                            badPass.setVisible(true);
                            Logger.getGlobal().info("\n\nRegistration failed. Bad password.");
                            userExists.setVisible(false);
                            notEnoughInfo.setVisible(false);
                        }
                    } else {
                        notEnoughInfo.setVisible(true);
                        Logger.getGlobal().info("\n\nRegistration failed. Not enough infomation filled out.");
                        userExists.setVisible(false);
                        badPass.setVisible(false);
                    }
                } else {
                    userExists.setVisible(true);
                    Logger.getGlobal().info("\n\nRegistration failed. User already exists.");
                    notEnoughInfo.setVisible(false);
                    badPass.setVisible(false);
                }
            } catch (NullPointerException ex) {
                notEnoughInfo.setVisible(true);
                Logger.getGlobal().info("\n\nRegistration failed. Found null pointer somewhere..");
                userExists.setVisible(false);
                badPass.setVisible(false);
            }
        });
    }

    public static void handleNewUser(String name, String email, String pass, Float startingBal, Account.AccountType type, boolean admin) {
        if (admin) {
            if (type.equals(Account.AccountType.PREMIUM)) {
                CashMachineApp.getCashMachine().addAccount(new PremiumAccount(name, email, startingBal, pass, true, CashMachineApp.getCashMachine().getBank()), pass);
            } else {
                CashMachineApp.getCashMachine().addAccount(new BasicAccount(name, email, startingBal, pass, true, CashMachineApp.getCashMachine().getBank()), pass);
            }
        } else {
            if (type.equals(Account.AccountType.PREMIUM)) {
                CashMachineApp.getCashMachine().addAccount(new PremiumAccount(name, email, startingBal, pass, false, CashMachineApp.getCashMachine().getBank()), pass);
            } else {
                CashMachineApp.getCashMachine().addAccount(new BasicAccount(name, email, startingBal, pass, false, CashMachineApp.getCashMachine().getBank()), pass);
            }
        }
    }

}
