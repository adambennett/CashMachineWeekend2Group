package rocks.zipcode.atm.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rocks.zipcode.atm.models.Account;

public class ViewAccountsController {

    public static void setupListeners(Stage primaryStage, Scene oldScene, Button returnBtn) {
        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });
        returnBtn.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        returnBtn.fire();
                    }
                }
        );
    }
}
