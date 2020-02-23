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
import rocks.zipcode.atm.controllers.WithdrawController;

public class WithdrawMenu {
    public static Parent createWithdraw(Stage primaryStage, Scene oldScene) {
        TextArea machineInfo = new TextArea(CashMachineApp.print());
        machineInfo.setWrapText(true);
        machineInfo.setEditable(false);
        VBox vbox = new VBox(10);
        FlowPane flowpane = new FlowPane();
        TextField inputText = new TextField();
        Label spacer = new Label("-------");
        Label spacerB = new Label("-------");
        Label badInputLbl = new Label("Bad input format!");
        Label negInputLbl = new Label("Cannot withdraw a negative amount.");
        Label badWithdraw = new Label(WithdrawController.getBadWithdrawText(0.0f));
        Button withdrawBtn = new Button("Withdraw");
        Button returnBtn = new Button("Return to Main Menu");

        badInputLbl.setTextFill(Color.web("red"));
        negInputLbl.setTextFill(Color.web("red"));
        badWithdraw.setTextFill(Color.web("red"));
        badInputLbl.setVisible(false);
        negInputLbl.setVisible(false);
        badWithdraw.setVisible(false);
        spacer.setVisible(false);
        spacerB.setVisible(false);
        flowpane.getChildren().add(withdrawBtn);
        flowpane.getChildren().add(returnBtn);
        flowpane.getChildren().add(spacer);
        flowpane.getChildren().add(badInputLbl);
        flowpane.getChildren().add(negInputLbl);
        flowpane.getChildren().add(spacerB);
        flowpane.getChildren().add(badWithdraw);
        vbox.setPrefSize(600, 300);
        vbox.getChildren().addAll(inputText, flowpane, machineInfo);

        if (!CashMachineApp.getCashMachine().isLoggedIn()) {
            withdrawBtn.setDisable(true);
            machineInfo.setText("You must be logged in!");
        }

        WithdrawController.setupListeners(primaryStage, oldScene, withdrawBtn, returnBtn, inputText, negInputLbl, badInputLbl, badWithdraw, machineInfo);
        return vbox;
    }

}
