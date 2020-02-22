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

import javax.swing.text.html.ImageView;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());

    private Parent createContent() {


        //VBox vbox = new VBox(10);

       //****************************
        MenuBar menuBar = new MenuBar();
        VBox vbox = new VBox(menuBar);

        Menu accounts = new Menu("Accounts");
        MenuItem regular = new MenuItem("Regular");
        MenuItem premium = new MenuItem("Premium");
        accounts.getItems().add(regular);
        accounts.getItems().add(premium);
        menuBar.getMenus().add(accounts);

        Menu transactions = new Menu("Transactions");
        MenuItem deposits = new MenuItem("Deposits");
        MenuItem withdraw = new MenuItem("Withdraw");
        transactions.getItems().add(deposits);
        transactions.getItems().add(withdraw);
        menuBar.getMenus().add(transactions);

        Menu help = new Menu("Help");


        menuBar.getMenus().add(help);






    //********************************

        vbox.setPrefSize(600, 600);

        TextArea areaInfo = new TextArea();

        Button btnSubmit = new Button("Set Account ID");
        btnSubmit.setOnAction(e -> {
            try {
                int id = Integer.parseInt(field.getText());
                cashMachine.login(id);

                areaInfo.setText(cashMachine.toString());
            } catch(NumberFormatException ex) { areaInfo.setText("Invalid input format!"); }
        });

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            try {
                Float amount = Float.parseFloat(field.getText());
                cashMachine.deposit(amount);

                areaInfo.setText(cashMachine.toString());
            } catch(NumberFormatException ex) { areaInfo.setText("Invalid input format!"); }
        });

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

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            areaInfo.setText(cashMachine.toString());
        });

        FlowPane flowpane = new FlowPane();

        flowpane.getChildren().add(btnSubmit);
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(btnWithdraw);
        flowpane.getChildren().add(btnExit);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return vbox;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
