package rocks.zipcode.atm.controllers;

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

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class WithdrawController {

    public static String getBadWithdrawText(Float amt) {
        DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        df.setMinimumFractionDigits(2);
        BigDecimal bal = new BigDecimal(CashMachineApp.getCashMachine().getBalance());
        BigDecimal amtt = new BigDecimal(amt);
        if (CashMachineApp.getCashMachine().getBalance() < 0) {
            String value = df.format(bal.multiply(BigDecimal.valueOf(-1.0)));
            String amtVal = df.format(amt);
            return "Withdraw failed!\nCannot withdraw $" + amtVal + ".\n" + CashMachineApp.getCashMachine().getCurrentUser().getName() + " only has -$" + value;
        } else {
            String value = df.format(bal);
            String amtVal = df.format(amt);
            return "Withdraw failed!\nCannot withdraw $" + amtVal + ".\n" + CashMachineApp.getCashMachine().getCurrentUser().getName() + " only has $" + value;
        }

    }

    public static void setupListeners(Stage primaryStage, Scene oldScene, Button withdrawBtn, Button returnBtn, TextField inputText, Label negInputLbl, Label badInputLbl, Label badWithdraw, TextArea machineInfo) {
        withdrawBtn.setOnAction(e -> {
            try {
                Float amount = Float.parseFloat(inputText.getText());
                if (amount < 0) {
                    negInputLbl.setVisible(true);
                    inputText.setText("");
                    badInputLbl.setVisible(false);
                    badWithdraw.setVisible(false);
                }
                else if (CashMachineApp.getCashMachine().isLoggedIn() && CashMachineApp.getCashMachine().getCurrentUser().canWithdraw(amount)) {
                    CashMachineApp.getCashMachine().withdraw(amount, CashMachineApp.getCashMachine().getCurrentUser());
                    machineInfo.setText(CashMachineApp.print());
                    inputText.setText("");
                    badInputLbl.setVisible(false);
                    badWithdraw.setVisible(false);
                    negInputLbl.setVisible(false);
                } else {
                    badWithdraw.setText(getBadWithdrawText(amount));
                    badInputLbl.setVisible(false);
                    badWithdraw.setVisible(true);
                    negInputLbl.setVisible(false);
                    inputText.setText("");
                }
            } catch(NumberFormatException ex) { if (!inputText.getText().equals("")) {  inputText.setText(""); badWithdraw.setVisible(false); negInputLbl.setVisible(false); badInputLbl.setVisible(true); }}
        });


        returnBtn.setOnAction(e -> {
            primaryStage.setScene(oldScene);
        });

        withdrawBtn.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        withdrawBtn.fire();
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
