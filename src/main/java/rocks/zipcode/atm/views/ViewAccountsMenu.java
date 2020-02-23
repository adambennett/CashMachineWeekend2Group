package rocks.zipcode.atm.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.CashMachineApp;
import rocks.zipcode.atm.controllers.ViewAccountsController;
import rocks.zipcode.atm.models.Account;

public class ViewAccountsMenu {

    public static Parent createViewAccounts(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        Button returnBtn = new Button("Return to Main Menu");

        Text sceneTitle = new Text("                 Administrator Menu - Viewing All Accounts");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        ObservableList<Account> data = FXCollections.observableArrayList(CashMachineApp.getCashMachine().getAllAccounts());
        ListView<Account> newList = new ListView<>(data);
        newList.setEditable(false);

        vbox.getChildren().addAll(sceneTitle,newList,returnBtn);

        ViewAccountsController.setupListeners(primaryStage, oldScene, returnBtn);
        return vbox;
    }

}
