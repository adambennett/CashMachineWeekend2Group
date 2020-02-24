package rocks.zipcode.atm.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rocks.zipcode.atm.models.CashMachine;
import rocks.zipcode.atm.models.Bank;
import rocks.zipcode.atm.views.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private static CashMachine cashMachine;
    private static Map<MenuType, MenuItem> menus;
    private static MenuItem[] transactMenuBtns;
    private static ArrayList<MenuItem> adminMenus;
    private static String splashImgURL;
    private static String iconURL;

    public static void main(String[] args) {
        launch(args);     // calls start(Stage s)
    }

    @Override
    public void start(Stage stage) {
        Scene mainScene = new Scene(MainMenu.createMainWindow());
        stage.setScene(mainScene);
        stage.setTitle("ZipCloudBank");
        stage.getIcons().add(new Image(iconURL));
        MainMenuController.handleMenuListeners(stage, mainScene);
        stage.show();
    }

    public static void updateMenus() {
        boolean on = cashMachine.isLoggedIn();
        if (transactMenuBtns != null) {
            for (MenuItem i : transactMenuBtns) {
                if (i != null) {
                    i.setDisable(!on);
                }
            }
        }

        if (adminMenus != null) {
            boolean allowAdminMenus = cashMachine.isLoggedIn() && cashMachine.getCurrentUser().isAdmin();
            for (MenuItem i : adminMenus) {
                if (i != null) {
                    i.setVisible(allowAdminMenus);
                }
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

    public static CashMachine getCashMachine() {
        return cashMachine;
    }

    public static Map<MenuType, MenuItem> getMenus() {
        return menus;
    }

    public static String getSplashImgURL() {
        return splashImgURL;
    }

    public static MenuItem[] getTransactMenuBtns() {
        return transactMenuBtns;
    }

    public static ArrayList<MenuItem> getAdminMenus() {
        return adminMenus;
    }

    public static String print() {
        return cashMachine.toString();
    }

    static {
        transactMenuBtns = new MenuItem[2];
        adminMenus = new ArrayList<>();
        cashMachine = new CashMachine(new Bank());
        menus = new HashMap<>();
        splashImgURL = "https://i.imgur.com/9th90Rd.jpg";
        iconURL = "https://cdn0.iconfinder.com/data/icons/bank-starter-colored/48/JD-13-512.png";
    }

    public enum MenuType {
        LOGIN,
        REGISTER,
        WITHDRAW,
        DEPOSIT,
        INSTRUCTION,
        ACCOUNTS
    }
}
