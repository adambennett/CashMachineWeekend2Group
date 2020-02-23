package rocks.zipcode.atm.controllers;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rocks.zipcode.atm.models.Account;

import java.util.Map;

public class LoginController {

    public static void setupListeners(Stage primaryStage, Scene oldScene, Button signInBtn, Button returnBtn, TextField emailField, PasswordField pwBox, Label invalidLbl) {
        signInBtn.setOnAction(e -> {
            String loginEmail = emailField.getText();
            String loginPass = pwBox.getText();
            boolean loggedIn = CashMachineApp.getCashMachine().login(loginEmail, loginPass);
            if (loggedIn) {
                //CashMachineApp.updateTextBox(CashMachineApp.print());
                CashMachineApp.updateLogin();
                primaryStage.setScene(oldScene);
            } else {
                invalidLbl.setVisible(true);
            }
        });

        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        signInBtn.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        signInBtn.fire();
                    }
                }
        );

        returnBtn.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        returnBtn.fire();
                    }
                }
        );
    }

}
