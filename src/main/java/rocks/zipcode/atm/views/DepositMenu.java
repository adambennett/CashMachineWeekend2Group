package rocks.zipcode.atm.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.CashMachineApp;
import rocks.zipcode.atm.controllers.DepositController;

public class DepositMenu {

    public static Parent createDeposit(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        FlowPane flowpane = new FlowPane();
        TextArea machineInfo = new TextArea(CashMachineApp.print());
        Button btnDeposit = new Button("Deposit");
        Button returnBtn = new Button("Return to Main Menu");
        TextField field = new TextField();
        Label badInput = new Label("Bad input format!");
        Label spacer = new Label("-------");
        Label negInput = new Label("Cannot deposit a negative amount.");

        machineInfo.setWrapText(true);
        machineInfo.setEditable(false);
        spacer.setVisible(false);
        badInput.setTextFill(Color.web("red"));
        badInput.setVisible(false);
        negInput.setTextFill(Color.web("red"));
        negInput.setVisible(false);
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(returnBtn);
        flowpane.getChildren().add(spacer);
        flowpane.getChildren().add(badInput);
        flowpane.getChildren().add(negInput);
        vbox.setPrefSize(600, 300);
        vbox.getChildren().addAll(field, flowpane, machineInfo);

        if (!CashMachineApp.getCashMachine().isLoggedIn()) {
            btnDeposit.setDisable(true);
            machineInfo.setText("You must be logged in!");
        }

        DepositController.setupListeners(primaryStage, oldScene,  btnDeposit, returnBtn, field, machineInfo, badInput, negInput);
        return vbox;
    }

}
