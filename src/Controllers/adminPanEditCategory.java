package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import service.LearningApplication;
import service.LearningCategory;

public class adminPanEditCategory extends adminPanelController {
    @FXML
    ChoiceBox<LearningApplication> choiceBoxId;
    @FXML
    ChoiceBox<LearningCategory> choiceBoxCategoryId;

    @FXML
    private void initialize() {
        editCategoryButton.setDisable(true);
        setNoEffects();
    }

    public void saveButtonPressed() {

    }
}
