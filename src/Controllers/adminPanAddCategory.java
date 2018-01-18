package Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.AlertBox;
import service.LearningApplication;
import service.LearningCategory;
import service.systemData;

import java.util.ArrayList;

public class adminPanAddCategory extends  adminPanelController {

    @FXML
    ChoiceBox<LearningApplication> choiceBoxId;
    @FXML
    TextArea descriptionId;
    @FXML
    Button  confirmButton;
    @FXML
    TextField nameId;



    @FXML
    private void initialize() {
        addCategoryButton.setDisable(true);
        initChoiceBox();
        setNoEffects();
    }

    public void confirmButtonPressed() {
        if(systemData.getInstance().getLCByName(getChoice().getName(), nameId.getText()) != null) {
            AlertBox.display("LC already exists.",
                    "Learning category with those name is already exists. Please, choose other name.");
        } else {
            systemData.getInstance().addLC(nameId.getText(), descriptionId.getText(), getChoice().getName());
            systemData.getInstance().reInit();
            TreeController.getInstance().updateTree();
            TreeController.getInstance().treeInitializer();
        }
    }

    private void initChoiceBox() {
        ArrayList<LearningApplication> LAs = systemData.getInstance().getDataLA();
        for(LearningApplication la : LAs) {
            choiceBoxId.getItems().add(la);
        }
        choiceBoxId.setValue(LAs.get(0));
    }

    private LearningApplication getChoice() {
        return choiceBoxId.getValue();
    }

}
