package rocks.zipcode.atm.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.*;

public class MainMenu {

    public static Parent createMainWindow() {
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
        ImageView image = new ImageView(new Image(CashMachineApp.getSplashImgURL()));

        if (!CashMachineApp.getAdminMenus().contains(viewAccounts)) { CashMachineApp.getAdminMenus().add(viewAccounts); }
        if (!CashMachineApp.getMenus().containsKey(CashMachineApp.MenuType.LOGIN)) {
            CashMachineApp.getMenus().put(CashMachineApp.MenuType.LOGIN, login);
            CashMachineApp.getMenus().put(CashMachineApp.MenuType.REGISTER, register);
            CashMachineApp.getMenus().put(CashMachineApp.MenuType.DEPOSIT, deposits);
            CashMachineApp.getMenus().put(CashMachineApp.MenuType.WITHDRAW, withdraw);
            CashMachineApp.getMenus().put(CashMachineApp.MenuType.INSTRUCTION, instruction);
            CashMachineApp.getMenus().put(CashMachineApp.MenuType.ACCOUNTS, viewAccounts);
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

        CashMachineApp.getTransactMenuBtns()[0] = deposits;
        CashMachineApp.getTransactMenuBtns()[1] = withdraw;

        CashMachineApp.updateMenus();

        flowpane.getChildren().add(image);
        vbox.setPrefSize(600, 450);
        vbox.getChildren().addAll(flowpane);
        return vbox;
    }

}
