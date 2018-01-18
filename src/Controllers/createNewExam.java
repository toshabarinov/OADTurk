package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckListView;
import service.LearningApplication;
import service.LearningCategory;
import service.LearningUnit;

public class createNewExam extends examPanelController {
    @FXML
    ChoiceBox<LearningApplication> choiceBoxLAId;
    @FXML
    ChoiceBox<LearningCategory> choiceBoxLCId;
    @FXML
    Button confirmButton;
    @FXML
    TextField nameId;
    @FXML
    CheckListView<LearningUnit> LUListViewId;

    @FXML
    private void initialize() {

    }

    public void confirmButtonPressed() {

    }
}
