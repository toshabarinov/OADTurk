package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
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
    ComboBox questionType;
    @FXML
    ComboBox answerType;

    @FXML
    private void initialize() {
        createButton.setDisable(true);

        buildTree(createTree);
        questionType.getItems().removeAll(questionType.getItems());
        questionType.getItems().addAll("Text", "Figure");
        answerType.getItems().removeAll(answerType.getItems());
        answerType.getItems().addAll("Text", "Figure");
    }

    public void createLAButtonClick(ActionEvent event) {
        CreateLABox.display();
    }

    public void createLUButtonClick(ActionEvent event) {
        //TODO JO make dependent on ComboBoxes -> and catch if nothing is set
        String qType = questionType.getValue().toString();
        String aType = answerType.getValue().toString();
        CreateLUBox CLUBox = new CreateLUBox();
        CLUBox.parentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        CLUBox.display(qType, aType);
    }
}
