package service;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResetPasswordBox {
    public static void display() {
        // New stage init
        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL); // block main stage during this stage is open
        window.setTitle("Reset password");

        // New elements init
        TextField loginEmailField = new TextField();
        loginEmailField.setPromptText("Your login or email");
        loginEmailField.setMinWidth(250);
        loginEmailField.setMinHeight(27);
        loginEmailField.setAlignment(Pos.BASELINE_LEFT);
        Button closeButton = new Button("Reset");
        closeButton.setMinWidth(100);
        closeButton.setOnAction(e-> window.close());
        Label helpMessage = new Label("Write your login or email");

        //Layout
        VBox layout = new VBox(25);
        layout.getChildren().addAll(helpMessage, loginEmailField,closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25, 25, 25, 25));

        //Scene
        Scene scene = new Scene(layout);

        //Start
        window.setScene(scene);
        window.show();
    }
}
