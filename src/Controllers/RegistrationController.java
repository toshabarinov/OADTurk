package Controllers;

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
import java.sql.Date;
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
    TextField username;
    @FXML
    DatePicker dateOfBirthField;
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    ChoiceBox genderField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    ListView<LearningApplication> chooseLAListView;
    @FXML
    CheckListView<LearningCategory> chooseCategoryListView;
    Parent root;
    ObservableList list;

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
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        if(checkInputData()) {
            User user = new User(systemData.getInstance().getLastUserId()+1, nameTextField.getText(),
                    surnameTextField.getText(), emailTextField.getText(), Date.valueOf(dateOfBirthField.getValue()),
                    genderField.getValue().toString());
            systemData.getInstance().addUser(user, username.getText(), passwordField.getText());
            try {
                root = FXMLLoader.load(getClass().getResource("../resources/view/home.fxml"));
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            window.setTitle("Home");
            window.setScene(new Scene(root, 800, 600));
        }
    }

    @FXML
    private void initialize() {
        list = errorLogTextFlow.getChildren();
        Text inputText = new Text("All fields - marked with \'*\' must to be completed");
        list.add(inputText);
        initializeLAListView();
        initializeLCCheckedListView();
        initializeGender();
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

    private void initializeGender() {
        genderField.getItems().addAll("Male", "Female");
        genderField.setValue("Female");

    }

    private boolean checkInputData () {
        boolean returnFlag = true;
        ObservableList list = errorLogTextFlow.getChildren();
        list.clear();
        if(nameTextField.getText().equals("")) {
            returnFlag = false;
            list.add(new Text("Please write your name\n"));
        }
        if(surnameTextField.getText().equals("")) {
            returnFlag = false;
            list.add(new Text("Please write your surname\n"));
        }
        if(passwordField.getText().equals("")) {
            returnFlag = false;
            list.add(new Text("Please write your password\n"));
        }
        if(username.getText().equals("")) {
            returnFlag = false;
            list.add(new Text("Please write your username\n"));
        }
        if(emailTextField.getText().equals("")) {
            returnFlag = false;
            list.add(new Text("Please write your email\n"));
        }
        System.out.println(passwordField.getText() + confirmPasswordField.getText());
        if(!passwordField.getText().equals(confirmPasswordField.getText())) {
            returnFlag = false;
            list.add(new Text("Passwords are not equal\n"));
        }
        if(systemData.getInstance().isUsernameExist(username.getText())) {
            returnFlag = false;
            list.add(new Text("This username is already exist. Choose another one\n"));
        }
        if(systemData.getInstance().isEmailExist(emailTextField.getText())) {
            returnFlag = false;
            list.add(new Text("This email is already exist. Choose another one\n"));
        }
        return returnFlag;
    }

}
