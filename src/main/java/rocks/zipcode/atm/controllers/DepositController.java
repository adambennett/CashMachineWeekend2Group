package rocks.zipcode.atm.controllers;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class DepositController {

    public static void setupListeners(Stage primaryStage, Scene oldScene,  Button btnDeposit, Button returnBtn, TextField field, TextArea machineInfo, Label badInput, Label negInput) {
        btnDeposit.setOnAction(e -> {
            try {
                Float amount = Float.parseFloat(field.getText());
                if (amount > 0) {
                    CashMachineApp.getCashMachine().deposit(amount, CashMachineApp.getCashMachine().getCurrentUser());
                    machineInfo.setText(CashMachineApp.print());
                    field.setText("");
                    badInput.setVisible(false);
                    negInput.setVisible(false);
                } else {
                    field.setText("");
                    negInput.setVisible(true);
                    badInput.setVisible(false);
                }
            } catch(NumberFormatException ex) { if (!field.getText().equals("")) {  field.setText(""); negInput.setVisible(false); badInput.setVisible(true); }}
        });

        btnDeposit.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        btnDeposit.fire();
                    }
                }
        );


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
