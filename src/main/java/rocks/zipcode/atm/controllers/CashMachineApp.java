package rocks.zipcode.atm.controllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rocks.zipcode.atm.models.CashMachine;
import rocks.zipcode.atm.models.Account;
import rocks.zipcode.atm.models.Bank;
import rocks.zipcode.atm.models.BasicAccount;
import rocks.zipcode.atm.models.PremiumAccount;
import rocks.zipcode.atm.services.MenuServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private static CashMachine cashMachine;
    private static Map<MenuType, MenuItem> menus;
    private static MenuItem[] transactMenuBtns;
    private static ArrayList<MenuItem> adminMenus;
    private static String splashImgURL;
    private static TextArea areaInfo;

    public enum MenuType {
        LOGIN,
        REGISTER,
        WITHDRAW,
        DEPOSIT,
        INSTRUCTION,
        ACCOUNTS
    }

    private static Parent createMainWindow() {
        FlowPane flowpane = new FlowPane();
        MenuBar menuBar = new MenuBar();
        VBox vbox = new VBox(menuBar);

        Menu accounts = new Menu("Accounts");
        Menu transactions = new Menu("Transactions");
        Menu help = new Menu("Help");
        MenuItem login = new MenuItem("Login");
        MenuItem register = new MenuItem("Register");
        MenuItem deposits = new MenuItem("Deposits");
        MenuItem withdraw = new MenuItem("Withdraw");
        MenuItem instruction = new MenuItem("Instruction");
        MenuItem viewAccounts = new MenuItem("View all Accounts");
        ImageView image = new ImageView(new Image(splashImgURL));

        if (!adminMenus.contains(viewAccounts)) { adminMenus.add(viewAccounts); }
        if (!menus.containsKey(MenuType.LOGIN)) {
            menus.put(MenuType.LOGIN, login);
            menus.put(MenuType.REGISTER, register);
            menus.put(MenuType.DEPOSIT, deposits);
            menus.put(MenuType.WITHDRAW, withdraw);
            menus.put(MenuType.INSTRUCTION, instruction);
            menus.put(MenuType.ACCOUNTS, viewAccounts);
        }

        help.getItems().add(instruction);

        accounts.getItems().add(login);
        accounts.getItems().add(register);
        accounts.getItems().add(viewAccounts);

        transactions.getItems().add(deposits);
        transactions.getItems().add(withdraw);

        menuBar.getMenus().add(accounts);
        menuBar.getMenus().add(transactions);
        menuBar.getMenus().add(help);

        transactMenuBtns[0] = deposits;
        transactMenuBtns[1] = withdraw;

        updateMenus();

        flowpane.getChildren().add(image);
        vbox.setPrefSize(600, 600);
        vbox.getChildren().addAll(flowpane);
        return vbox;
    }

    public static void updateMenus() {
        boolean on = cashMachine.isLoggedIn();
        for (MenuItem i : transactMenuBtns) {
            i.setDisable(!on);
        }

        boolean allowAdminMenus = cashMachine.isLoggedIn() && cashMachine.getCurrentUser().isAdmin();
        for (MenuItem i : adminMenus) {
            i.setVisible(allowAdminMenus);
        }
    }

    public static Parent createLogin(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });
        Text sceneTitle = new Text("Account Sign In");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label email = new Label("Email: ");
        Label notFoundUser = new Label("Invalid credentials!");
        notFoundUser.setTextFill(Color.web("red"));
        notFoundUser.setVisible(false);
        TextField emailField = new TextField();
        Label pw = new Label("Password: ");
        PasswordField pwBox = new PasswordField();
        Button btn = new Button("Sign In");
        FlowPane flowpane = new FlowPane();

        btn.setOnAction(e -> {
            String loginEmail = emailField.getText();
            boolean found = false;
            for (Map.Entry<Account, String> i  : cashMachine.getBank().getPassWordMap().entrySet()) {
                if (i.getKey().getEmail().equals(loginEmail) && i.getValue().equals(pwBox.getText())) {
                    cashMachine.login(loginEmail);
                    areaInfo.setText(cashMachine.toString());
                    updateLogin();
                    found = true;
                    break;
                }
            }
            if (found) {
                primaryStage.setScene(oldScene);
            } else {
                notFoundUser.setVisible(true);
            }
        });

        vbox.getChildren().addAll(flowpane, sceneTitle, email, emailField, pw, pwBox, notFoundUser, btn, returnBtn);
        return vbox;
    }

    public static Parent createDeposit(Stage primaryStage, Scene oldScene) {
        areaInfo.setText(cashMachine.toString());
        TextField field = new TextField();
        Label badInput = new Label("Bad input format!");
        Label spacer = new Label("-------");
        spacer.setVisible(false);
        badInput.setTextFill(Color.web("red"));
        badInput.setVisible(false);
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            try {
                if (cashMachine.isLoggedIn()) {
                    Float amount = Float.parseFloat(field.getText());
                    cashMachine.deposit(amount, cashMachine.getCurrentUser());
                    areaInfo.setText(cashMachine.toString());
                    field.setText("");
                    badInput.setVisible(false);
                } else {
                    areaInfo.setText("Not logged in!");
                }
            } catch(NumberFormatException ex) { if (!field.getText().equals("")) {  field.setText(""); badInput.setVisible(true); }}
        });

        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        if (!cashMachine.isLoggedIn()) {
            btnDeposit.setDisable(true);
            areaInfo.setText("You must be logged in!");
        }

        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(returnBtn);
        flowpane.getChildren().add(spacer);
        flowpane.getChildren().add(badInput);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }

    private static String getBadWithdrawText(Float amt) {
        if (cashMachine.getBalance() < 0) {
            return "Withdraw failed!\nCannot withdraw $" + amt + ".\n" + cashMachine.getCurrentUser().getName() + " only has -$" + -cashMachine.getBalance();
        } else {
            return "Withdraw failed!\nCannot withdraw $" + amt + ".\n" + cashMachine.getCurrentUser().getName() + " only has $" + cashMachine.getBalance();
        }

    }

    public static Parent createWithdraw(Stage primaryStage, Scene oldScene) {
        areaInfo.setText(cashMachine.toString());
        TextField field = new TextField();
        Label badInput = new Label("Bad input format!");
        Label badWithdraw = new Label(getBadWithdrawText(0.0f));
        Label spacer = new Label("-------");

        spacer.setVisible(false);
        badInput.setTextFill(Color.web("red"));
        badInput.setVisible(false);
        badWithdraw.setTextFill(Color.web("red"));
        badWithdraw.setVisible(false);

        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            try {
                Float amount = Float.parseFloat(field.getText());
                if (cashMachine.isLoggedIn() && cashMachine.getCurrentUser().canWithdraw(amount)) {
                    cashMachine.withdraw(amount, cashMachine.getCurrentUser());
                    areaInfo.setText(cashMachine.toString());
                    field.setText("");
                    badInput.setVisible(false);
                    badWithdraw.setVisible(false);
                } else {
                    badWithdraw.setText(getBadWithdrawText(amount));
                    badInput.setVisible(false);
                    badWithdraw.setVisible(true);
                    field.setText("");
                }
            } catch(NumberFormatException ex) { if (!field.getText().equals("")) {  field.setText(""); badWithdraw.setVisible(false); badInput.setVisible(true); }}
        });

        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        if (!cashMachine.isLoggedIn()) {
            btnWithdraw.setDisable(true);
            areaInfo.setText("You must be logged in!");
        }

        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(btnWithdraw);
        flowpane.getChildren().add(returnBtn);
        flowpane.getChildren().add(spacer);
        flowpane.getChildren().add(badInput);
        flowpane.getChildren().add(badWithdraw);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }

    public static Parent createRegister(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(400, 400);
        Button returnBtn = new Button("Return to Main Menu");
        Button register = new Button("Register");

        Text sceneTitle = new Text("New Account Registration");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        PasswordField pwBox = new PasswordField();
        TextField userTextField = new TextField();
        TextField emailField = new TextField();
        ComboBox<String> accountBox = new ComboBox<>();
        CheckBox admin = new CheckBox("Administrator");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Basic Account",
                        "Premium Account"
                );
        accountBox.setItems(options);
        TextField balanceField = new TextField();


        Label pw = new Label("Password");
        Label email = new Label("Email");
        Label accountType = new Label("Account Type");
        Label balance = new Label("Balance");
        Label userName = new Label("Name");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(pw, 0, 2);
        grid.add(pwBox, 1, 2);
        grid.add(email, 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(accountType, 0, 4);
        grid.add(accountBox, 1, 4);
        grid.add(admin, 1, 5);
        grid.add(balance, 0, 6);
        grid.add(balanceField, 1, 6);
        grid.add(register, 0, 10);
        grid.add(returnBtn, 1, 10);

        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        register.setOnAction(e -> {
            String newName = userTextField.getText();
            String newEmail = emailField.getText();
            String newAccount = accountBox.getValue().toString();
            String startBal = balanceField.getText();
            String pass = pwBox.getText();
            Boolean adminBox = admin.isSelected();
            Float newBal = 0.0f;
            try {
                newBal = Float.parseFloat(startBal);
            } catch (NumberFormatException ex) { }
            Account.AccountType newType = Account.AccountType.BASIC;
            if (newAccount.equals("Premium Account")) {
                newType = Account.AccountType.PREMIUM;
            }
            if (adminBox) {
                HandleNewUser(newName, newEmail, pass, newBal, newType, true);
            } else {
                HandleNewUser(newName, newEmail, pass, newBal, newType, false);
            }

            primaryStage.setScene(oldScene);
        });

       // vbox.getChildren().addAll(sceneTitle, userName, userTextField, pw, pwBox, email, emailField, accountType, accountBox, balance, balanceField,register, returnBtn);
        vbox.getChildren().addAll(sceneTitle, grid);
        return vbox;
    }

    public static Parent createViewAccounts(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });
        Text sceneTitle = new Text("Administrator Menu - Viewing All Accounts");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        ObservableList<Account> data = FXCollections.observableArrayList(cashMachine.getAllAccounts());
        ListView<Account> newList = new ListView<>(data);
        newList.setEditable(false);

        vbox.getChildren().addAll(sceneTitle,newList,returnBtn);
        return vbox;
    }

    public static void HandleNewUser(String name, String email, String pass, Float startingBal, Account.AccountType type, boolean admin) {
        if (admin) {
            if (type.equals(Account.AccountType.PREMIUM)) {
                cashMachine.addAccount(new PremiumAccount(name, email, startingBal, pass, true));
            } else {
                cashMachine.addAccount(new BasicAccount(name, email, startingBal, pass, true));
            }
        } else {
            if (type.equals(Account.AccountType.PREMIUM)) {
                cashMachine.addAccount(new PremiumAccount(name, email, startingBal, pass, false));
            } else {
                cashMachine.addAccount(new BasicAccount(name, email, startingBal, pass, false));
            }
        }
    }

    public static void updateLogin() {
       MenuItem ref = menus.get(MenuType.LOGIN);
       if (cashMachine.isLoggedIn()) {
           ref.setText("Logout");
       } else {
           ref.setText("Login");
       }
    }

    public static Parent createInstruction(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 300);

        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        TextArea areaInfo = new TextArea();
        areaInfo.setPrefSize(500, 250);
        areaInfo.setWrapText(true);
        areaInfo.setText(
                "                                                              Instructions\n" +
                "-----------------------------------------------------------------------------------------------\n" +
                "1. Login or Register a new account\n\n" +
                "2. To go back, you can always click on 'Return to Main Menu'\n\n" +
                "3. To make Deposits and Withdrawals, you must be logged in. Then, select the 'Transactions' menu.\n\n"+
                "4. Email and password fields are case-sensitive - if your input does not match exactly what you typed when registering, you will not be logged in properly.");

        areaInfo.setEditable(false);
        vbox.getChildren().addAll(areaInfo, returnBtn);
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);     // calls start(Stage s)
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene mainScene = new Scene(createMainWindow());
        stage.setScene(mainScene);
        stage.setTitle("ZipCloudBank");
        MenuServices.handleMenuListeners(stage, mainScene);
        stage.show();
    }

    public static CashMachine getCashMachine() {
        return cashMachine;
    }

    public static Map<MenuType, MenuItem> getMenus() {
        return menus;
    }

    public static String getSplashImgURL() {
        return splashImgURL;
    }

    public static TextArea getAreaInfo() {
        return areaInfo;
    }

    static {
        transactMenuBtns = new MenuItem[2];
        adminMenus = new ArrayList<>();
        cashMachine = new CashMachine(new Bank());
        menus = new HashMap<>();
        areaInfo = new TextArea();
        areaInfo.setEditable(false);
        splashImgURL = "https://uploads-ssl.webflow.com/5de2db6d3719a1e2f3e4454c/5de99cfc58e6cb305d54eff0_best-banks-logos-explained.png";
    }
}
