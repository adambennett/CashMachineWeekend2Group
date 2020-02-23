package rocks.zipcode.atm.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rocks.zipcode.atm.controllers.CashMachineApp;
import rocks.zipcode.atm.controllers.InstructionsController;

public class InstructionsMenu {

    public static Parent createInstruction(Stage primaryStage, Scene oldScene) {
        VBox vbox = new VBox(10);
        TextArea machineInfo = new TextArea(CashMachineApp.print());
        machineInfo.setWrapText(true);
        machineInfo.setEditable(false);
        vbox.setPrefSize(600, 300);

        Button returnBtn = new Button("Return to Main Menu");


        machineInfo.setPrefSize(500, 250);
        machineInfo.setText(
                "                                                              Instructions\n" +
                        "-----------------------------------------------------------------------------------------------\n" +
                        "1. Login or Register a new account.\n\n" +
                        "2. To go back, you can always click on 'Return to Main Menu'.\n\n" +
                        "3. To make Deposits and Withdrawals, you must be logged in. Then, select the 'Transactions' menu.\n\n"+
                        "4. Email and password fields are case-sensitive - if your input does not match exactly what you typed when registering, you will not be logged in properly.");
        vbox.getChildren().addAll(machineInfo, returnBtn);
        InstructionsController.setupListeners(primaryStage, oldScene, returnBtn);
        return vbox;
    }

}
