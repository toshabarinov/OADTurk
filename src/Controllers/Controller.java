package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public void testButton() {

    }

    @FXML
    Button homeButton;
    @FXML
    Button myContentButton;
    @FXML
    Button createButton;
    @FXML
    Button settingsButton;

    // function to add a new scene to active stage
    protected void newScene(ActionEvent event, String newFxml) {
        Parent root;
        //get reference to the events stage
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource("../resources/view/" + newFxml));
            //create a new scene with root and set the stage
            Scene scene = new Scene(root, 800, 600);
            window.setScene(scene);
            window.show();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    // button functions

    public void homeButtonClick(ActionEvent event) {

        newScene(event, "home.fxml");

    }

    public void settingsButtonClick(ActionEvent event) {

        newScene(event, "settings.fxml");

    }

}
