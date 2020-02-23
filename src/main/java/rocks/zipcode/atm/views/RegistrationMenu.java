package rocks.zipcode.atm.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.RegistrationController;

public class RegistrationMenu {

    public static Parent createRegister(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        //vbox.setPrefSize(450, 400);
        vbox.setPrefSize(700, 400);
        Button returnBtn = new Button("Return to Main Menu");
        Button register = new Button("Register");

        returnBtn.setWrapText(true);

        Text sceneTitle = new Text("                New Account Registration");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        PasswordField pwBox = new PasswordField();
        TextField userTextField = new TextField();
        TextField emailField = new TextField();
        ComboBox<String> accountBox = new ComboBox<>();
        CheckBox admin = new CheckBox("Administrator");
        ObservableList<String> options;
        options =
                FXCollections.observableArrayList(
                        "Basic Account",
                        "Premium Account"
                );
        accountBox.setItems(options);
        TextField balanceField = new TextField();


        Label pw = new Label("Password");
        Label email = new Label("Email");
        Label accountType = new Label("Account Type");
        Label balance = new Label("Balance");
        Label userName = new Label("Name");
        Label userExists = new Label("User already exists!");
        userExists.setTextFill(Color.web("red"));
        userExists.setVisible(false);

        Label notEnoughInfo = new Label("Please fill out the registration form.");
        notEnoughInfo.setTextFill(Color.web("blue"));
        notEnoughInfo.setVisible(false);
        notEnoughInfo.setWrapText(true);

        Label badPass = new Label("Password is not secure!");
        badPass.setTextFill(Color.web("red"));
        badPass.setVisible(false);
        badPass.setWrapText(true);

        Tooltip tooltip = new Tooltip("Passwords must be at least 8 characters.\nPasswords must contain uppercase and lowercase letters.\nPasswords must contain at least one special character.");
        pwBox.setTooltip(tooltip);
        tooltip.setWrapText(true);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(sceneTitle, 0, 0);
        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(pw, 0, 2);
        grid.add(pwBox, 1, 2);
        grid.add(email, 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(accountType, 0, 4);
        grid.add(accountBox, 1, 4);
        grid.add(admin, 1, 5);
        grid.add(balance, 0, 6);
        grid.add(balanceField, 1, 6);
        grid.add(badPass, 0, 7);
        grid.add(notEnoughInfo, 0, 8);
        grid.add(userExists, 0, 9);
        grid.add(register, 0, 10);
        grid.add(returnBtn, 1, 10);

        vbox.getChildren().addAll(grid);

        RegistrationController.setupListeners(primaryStage, oldScene, returnBtn, register, admin, emailField, userExists, notEnoughInfo, badPass, accountBox, userTextField, balanceField, pwBox);
        return vbox;
    }

}
