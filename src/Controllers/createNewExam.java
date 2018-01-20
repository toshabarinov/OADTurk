package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckListView;
import service.LearningApplication;
import service.LearningCategory;
import service.LearningUnit;
import service.systemData;

import java.util.ArrayList;
import java.util.List;

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
        List<LearningCategory> arrayListCategory = systemData.getInstance().getDataLC();

        for (int i = 0; i < arrayListCategory.size(); i++){
            choiceBoxLCId.getItems().add(arrayListCategory.get(i));
        }

        if (choiceBoxLCId.isPressed()){

            LearningCategory nekaj = choiceBoxLCId.getSelectionModel().getSelectedItem();
        }

        choiceBoxLCId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override

            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(choiceBoxLCId.getItems().get((Integer) number2));
            }
        });

        List<LearningUnit> learningUnitList = systemData.getInstance().getLearningUnitList();



    }

    public void confirmButtonPressed() {

    }




}
