package rocks.zipcode.atm.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.LoginController;

public class LoginMenu {

    public static Parent createLogin(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        Button returnBtn = new Button("Return to Main Menu");
        Text sceneTitle = new Text("             Account Sign In");
        GridPane grid = new GridPane();
        Label emailLbl = new Label("Email: ");
        Label invalidLbl = new Label("Invalid credentials!");
        Label passwordLbl = new Label("Password: ");
        TextField emailField = new TextField();
        PasswordField pwBox = new PasswordField();
        Button signInBtn = new Button("Sign In");

        invalidLbl.setTextFill(Color.web("red"));
        invalidLbl.setVisible(false);
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        vbox.setPrefSize(400, 250);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(emailLbl, 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(passwordLbl, 0, 2);
        grid.add(pwBox, 1, 2);
        grid.add(invalidLbl, 0, 5);
        grid.add(signInBtn, 0, 6);
        grid.add(returnBtn, 1, 6);
        vbox.getChildren().addAll(sceneTitle, grid);
        LoginController.setupListeners(primaryStage, oldScene, signInBtn, returnBtn, emailField, pwBox, invalidLbl);
        return vbox;
    }

}
