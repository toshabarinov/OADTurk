package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import service.LearningInstance;

import service.CreateLABox;
import service.CreateLUBox;

public class CreateController extends Controller {

    @FXML
    TreeView<LearningInstance> createTree;
    @FXML
    Button createLUButton;
    @FXML
    Button createLAButton;

    @FXML
    private void initialize() {
        createButton.setDisable(true);

        buildTree(createTree);
    }

    public void createLAButtonClick(ActionEvent event) {
        CreateLABox.display();
    }

    public void createLUButtonClick(ActionEvent event) {
        CreateLUBox.display();
    }
}
