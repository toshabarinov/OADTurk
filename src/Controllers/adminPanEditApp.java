package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import service.LearningApplication;

public class adminPanEditApp extends adminPanelController{

    @FXML
    ChoiceBox<LearningApplication> choiceBoxId;

    @FXML
    private void initialize() {
        editAppButton.setDisable(true);
        setNoEffects();
    }


    public void saveButtonPressed() {

    }
}
