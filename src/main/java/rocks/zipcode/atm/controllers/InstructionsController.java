package rocks.zipcode.atm.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructionsController {

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
