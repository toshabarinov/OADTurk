package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import service.LearningApplication;
import service.systemData;


public class createdExams extends examPanelController{

    //TODO: add class exam and change the parameter of ListView
    @FXML
    ListView<LearningApplication> examsListView;

    public static final ObservableList list =
            FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        createdExamsButton.setDisable(true);
        initListView();
    }

    public void createNewExamButtonClicked() {

    }

    public void createdExamsButtonClicked() {

    }

    private void initListView() {
        //TODO: change LA to Exams
        list.addAll(systemData.getInstance().getDataLA());
        examsListView.setItems(list);
    }
}
