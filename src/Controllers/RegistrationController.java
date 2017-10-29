package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import service.LearningApplication;
import service.LearningCategory;
import service.User;
import service.systemData;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a1 on 27.10.17.
 */
public class RegistrationController {
    @FXML
    TextFlow errorLogTextFlow;
    @FXML
    Button goBackButton;
    @FXML
    Button confirmButton;
    @FXML
    TextField nameTextField;
    @FXML
    TextField surnameTextField;
    @FXML
    DatePicker dateOfBirthField;
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    ListView<LearningApplication> chooseLAListView;
    @FXML
    CheckListView<LearningCategory> chooseCategoryListView;



    Parent root;


    public void goBackButtonPressed(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource("../resources/view/login.fxml"));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        window.setTitle("OADTurk");
        window.setScene(new Scene(root, 800, 600));
    }

    public void confirmButtonPressed(ActionEvent event) {
        //TODO: check correctness of input data
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource("../resources/view/home.fxml"));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        window.setTitle("Home");
        window.setScene(new Scene(root, 800, 600));
    }

    @FXML
    private void initialize() {
        Text inputText = new Text("All fields - marked with \'*\' must to be completed");
        ObservableList list = errorLogTextFlow.getChildren();
        list.add(inputText);
        initializeLAListView();
        initializeLCCheckedListView();
    }

    private void initializeLAListView() {
        ObservableList<LearningApplication> observableList = chooseLAListView.getItems();
        ArrayList<LearningApplication> LAs = systemData.getInstance().getDataLA();

        for(LearningApplication la : LAs) {
            observableList.add(la);
        }
        chooseLAListView.getSelectionModel().select(0);
        chooseLAListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void initializeLCCheckedListView() {
        ObservableList<LearningCategory> observableList = chooseCategoryListView.getItems();
        ArrayList<LearningCategory> LCs = systemData.getInstance().getDataLC();

        for(LearningCategory lc : LCs) {
            observableList.add(lc);
        }
        chooseCategoryListView.getSelectionModel().select(0);
        chooseCategoryListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
