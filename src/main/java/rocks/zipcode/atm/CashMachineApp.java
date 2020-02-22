package rocks.zipcode.atm;

import javafx.scene.control.*;
import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import rocks.zipcode.atm.bank.PremiumAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank());
    private Map<MenuType, MenuItem> menus = new HashMap<>();

    private TextArea areaInfo = new TextArea();

    public enum MenuType {
        LOGIN,
        REGISTER,
        WITHDRAW,
        DEPOSIT
    }

    private Parent createMainWindow() {
        //********************************************************
        TextField field = new TextField();
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
        menuBar.getMenus().add(help);
        //********************************************************

        vbox.setPrefSize(600, 600);

        Button btnSubmit = new Button("Set Account ID");
        btnSubmit.setOnAction(e -> {
            try {
                int id = Integer.parseInt(field.getText());
                cashMachine.login(id);

                areaInfo.setText(cashMachine.toString());
            } catch(NumberFormatException ex) { areaInfo.setText("Invalid input format!"); }
        });

        Button btnExit = new Button("Logout");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            areaInfo.setText(cashMachine.toString());
        });

        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(btnSubmit);
        flowpane.getChildren().add(btnExit);
        vbox.getChildren().addAll(field, flowpane);
        return vbox;
    }

    private Parent createLogin(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });
        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(returnBtn);
        vbox.getChildren().addAll(flowpane);
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
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });
        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(returnBtn);
        vbox.getChildren().addAll(flowpane);
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
        FlowPane flowpane = new FlowPane();
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(returnBtn);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }

    private Scene getMainScene() {
        return new Scene(createMainWindow());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene mainScene = new Scene(createMainWindow());
        stage.setScene(mainScene);

        MenuItem login = menus.get(MenuType.LOGIN);
        MenuItem register = menus.get(MenuType.REGISTER);
        MenuItem withdraw = menus.get(MenuType.WITHDRAW);
        MenuItem deposit = menus.get(MenuType.DEPOSIT);

        login.setOnAction(e -> {
            // change to proper scene for login
            Scene scene = new Scene(createLogin(stage, mainScene));
            stage.setScene(scene);
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
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
