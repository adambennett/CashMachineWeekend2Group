package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rocks.zipcode.atm.bank.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank());
    private Map<MenuType, MenuItem> menus = new HashMap<>();
    private static String splashImgURL;
    private TextArea areaInfo = new TextArea();
    public enum MenuType {
        LOGIN,
        REGISTER,
        WITHDRAW,
        DEPOSIT,
        INSTRUCTION
    }


    private Parent createMainWindow() {
        //********************************************************
        TextField field = new TextField();
        ImageView image = new ImageView(new Image(splashImgURL));
        MenuBar menuBar = new MenuBar();
        VBox vbox = new VBox(menuBar);
        Menu accounts = new Menu("Accounts");
        MenuItem regular = new MenuItem("Login");
        MenuItem premium = new MenuItem("Register");
        accounts.getItems().add(regular);
        accounts.getItems().add(premium);
        menuBar.getMenus().add(accounts);
        Menu transactions = new Menu("Transactions");
        MenuItem deposits = new MenuItem("Deposits");
        MenuItem withdraw = new MenuItem("Withdraw");
        transactions.getItems().add(deposits);
        transactions.getItems().add(withdraw);
        menuBar.getMenus().add(transactions);
        menus.put(MenuType.LOGIN, regular);
        menus.put(MenuType.REGISTER, premium);
        menus.put(MenuType.DEPOSIT, deposits);
        menus.put(MenuType.WITHDRAW, withdraw);

        Menu help = new Menu("Help");
        MenuItem instruction = new MenuItem("Instruction");
        help.getItems().add(instruction);
        menuBar.getMenus().add(help);
        menus.put(MenuType.INSTRUCTION, instruction);
        //********************************************************


        vbox.setPrefSize(600, 600);
        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(image);
        vbox.getChildren().addAll(flowpane);
        return vbox;
    }

    private Parent createLogin(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });
        Text sceneTitle = new Text("Account Sign In");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label email = new Label("Email: ");
        TextField emailField = new TextField();
        Label pw = new Label("Password: ");
        PasswordField pwBox = new PasswordField();
        Button btn = new Button("Sign In");
        FlowPane flowpane = new FlowPane();

        btn.setOnAction(e -> {
            String loginEmail = emailField.getText();
            for (Map.Entry<Account, String> i  : this.cashMachine.getBank().getPassWordMap().entrySet()) {
                if (i.getKey().getAccountData().getEmail().equals(loginEmail) && i.getValue().equals(pwBox.getText())) {
                    cashMachine.login(loginEmail);
                    areaInfo.setText(cashMachine.toString());
                    updateLogin();
                }
            }
            primaryStage.setScene(oldScene);
        });

        vbox.getChildren().addAll(flowpane, sceneTitle, email, emailField, pw, pwBox, btn, returnBtn);
        return vbox;
    }

    private Parent createWithdraw(Stage primaryStage, Scene oldScene) {
        TextField field = new TextField();
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            try {
                Float amount = Float.parseFloat(field.getText());
                if (cashMachine.getAccountData().getBalance() >= amount) {
                    cashMachine.withdraw(amount);
                    areaInfo.setText(cashMachine.toString());
                } else if (cashMachine.getAccountData().getType().equals(AccountData.AccountType.PREMIUM) && cashMachine.getAccountData().getBalance() + PremiumAccount.getOverdraftLimit() >= amount){
                    cashMachine.withdraw(amount);
                    areaInfo.setText(cashMachine.toString());
                } else {
                    areaInfo.setText("Withdraw failed: " + amount + ". Account has: " + cashMachine.getAccountData().getBalance());
                }
            } catch(NumberFormatException ex) { areaInfo.setText("Invalid input format!"); }
        });

        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        if (this.cashMachine.getAccountData() == null) {
            btnWithdraw.setDisable(true);
            areaInfo.setText("You must be logged in!");
        }

        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(btnWithdraw);
        flowpane.getChildren().add(returnBtn);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }

    private Parent createRegister(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button returnBtn = new Button("Return to Main Menu");
        Button register = new Button("Register");

        Text sceneTitle = new Text("New Account Registration");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        PasswordField pwBox = new PasswordField();
        TextField userTextField = new TextField();
        TextField emailField = new TextField();
        TextField accountField = new TextField();
        TextField balanceField = new TextField();

        Label pw = new Label("Password:");
        Label email = new Label("Email:");
        Label accountType = new Label("Account Type:");
        Label balance = new Label("Balance:");
        Label userName = new Label("Name:");

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
        grid.add(accountField, 1, 4);
        grid.add(balance, 0, 5);
        grid.add(balanceField, 1, 5);

        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        register.setOnAction(e -> {
            String newName = userTextField.getText();
            String newEmail = emailField.getText();
            String newAccount = accountField.getText();
            String startBal = balanceField.getText();
            String pass = pwBox.getText();
            Integer newBal = 0;
            try {
                newBal = Integer.parseInt(startBal);
            } catch (NumberFormatException ex) { }
            AccountData.AccountType newType = AccountData.AccountType.BASIC;
            if (newAccount.toLowerCase().equals("premium")) {
                newType = AccountData.AccountType.PREMIUM;
            }
            HandleNewUser(newName, newEmail, pass, newBal, newType);
            primaryStage.setScene(oldScene);
        });

        vbox.getChildren().addAll(sceneTitle, userName, userTextField, pw, pwBox, email, emailField, accountType, accountField, balance, balanceField,register, returnBtn);
        return vbox;
    }

    private Parent createDeposit(Stage primaryStage, Scene oldScene) {
        TextField field = new TextField();
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            try {
                Float amount = Float.parseFloat(field.getText());
                cashMachine.deposit(amount);
                areaInfo.setText(cashMachine.toString());
            } catch(NumberFormatException ex) { areaInfo.setText("Invalid input format!"); }
        });


        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });


        if (this.cashMachine.getAccountData() == null) {
            btnDeposit.setDisable(true);
            areaInfo.setText("You must be logged in!");
        }


        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(returnBtn);


        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }

    private void HandleNewUser(String name, String email, String pass, Integer startingBal, AccountData.AccountType type) {
        if (type.equals(AccountData.AccountType.PREMIUM)) {
            this.cashMachine.getBank().addAccountToBank(new PremiumAccount(new AccountData(name, email, startingBal, type, pass)));
        } else {
            this.cashMachine.getBank().addAccountToBank(new BasicAccount(new AccountData(name, email, startingBal, pass)));
        }
    }

    private void updateLogin() {
       MenuItem ref = menus.get(MenuType.LOGIN);
       if (this.cashMachine.getAccountData() == null) {
           ref.setText("Login");
       } else {
           ref.setText("Logout");
       }
    }

    //******************

    private Parent createInstruction(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);

        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        TextArea areaInfo = new TextArea();
        areaInfo.setText(" Instruction Follows:" + "\n"+
                "1. Go to Log in and keep your username" + "\n" +
                "2. To go back always click on return to Main Menu" + "\n" +
                "3. To deposit click on deposit button and Withdraw for Withdraw");

        areaInfo.setEditable(false);

        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(returnBtn);
        vbox.getChildren().addAll(flowpane,areaInfo);
        return vbox;
    }

    //******************

    private Scene getMainScene() {
        return new Scene(createMainWindow());
    }

    @Override
    public void start(Stage stage) throws Exception {
        areaInfo.setEditable(false);

        Scene mainScene = new Scene(createMainWindow());
        stage.setScene(mainScene);

        MenuItem login = menus.get(MenuType.LOGIN);
        MenuItem register = menus.get(MenuType.REGISTER);
        MenuItem withdraw = menus.get(MenuType.WITHDRAW);
        MenuItem deposit = menus.get(MenuType.DEPOSIT);
        MenuItem instruction = menus.get(MenuType.INSTRUCTION);


        login.setOnAction(e -> {
            // change to proper scene for login
            if (this.cashMachine.getAccountData() == null) {
                Scene scene = new Scene(createLogin(stage, mainScene));
                stage.setScene(scene);
            } else {
                this.cashMachine.exit();
                updateLogin();
            }

        });

        register.setOnAction(e -> {
            // change to proper scene for register
            Scene scene = new Scene(createRegister(stage, mainScene));
            stage.setScene(scene);
        });

        withdraw.setOnAction(e -> {
            // change to proper scene for withdraw
            Scene scene = new Scene(createWithdraw(stage, mainScene));
            stage.setScene(scene);
        });

        deposit.setOnAction(e -> {
            // change to proper scene for deposit
            Scene scene = new Scene(createDeposit(stage, mainScene));
            stage.setScene(scene);
        });

        instruction.setOnAction(e -> {
            // change to proper scene for deposit
            Scene scene = new Scene(createInstruction(stage, mainScene));
            stage.setScene(scene);
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static {
        splashImgURL = "https://uploads-ssl.webflow.com/5de2db6d3719a1e2f3e4454c/5de99cfc58e6cb305d54eff0_best-banks-logos-explained.png";
    }
}
