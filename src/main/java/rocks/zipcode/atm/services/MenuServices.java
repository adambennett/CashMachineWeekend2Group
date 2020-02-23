package rocks.zipcode.atm.services;

import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.CashMachineApp;

public class MenuServices {

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

}
