package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by a1 on 28.10.17.
 */
public class HomeController {
    @FXML
    Button logOutbutton;
    @FXML
    Button homeButton;
    @FXML
    Button myContentButton;
    @FXML
    Button createButton;
    @FXML
    Button settingsButton;
    @FXML
    ChoiceBox<String> searchChoiseBox;
    @FXML
    TextField searchTextField;


    Parent root;

    @FXML
    private void initialize() {
        homeButton.setDisable(true);
    }

    public void logOutButtonClicked(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource("../resources/view/login.fxml"));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        window.setTitle("OADTurk");
        window.setScene(new Scene(root, 800, 600));
    }

}
