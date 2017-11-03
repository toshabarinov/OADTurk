package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class LAController extends TreeController{

    @FXML
    Button goToExamsButton;
    @FXML
    private void initialize() {
        treeInitializer();
    }

}
