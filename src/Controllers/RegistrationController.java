package Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import service.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import static javafx.scene.text.Font.font;

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
    ChoiceBox<String> genderField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    TextFlow beCreatorText;
    @FXML
    CheckBox beCreator;

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
                    false, beCreator.isSelected());
            user.setGender(genderField.getValue());
//            if(beCreator.isSelected()) {
//                AlertBox.display("Welcome", "We are glad, that now you a part of our team. Your creator " +
//                        "interface would be available after you next login to the system");
//            }
            systemData.getInstance().addUser(user, username.getText(), passwordField.getText());
            systemData.getInstance().setCurrentUser();
            try {
                root = FXMLLoader.load(getClass().getResource("../resources/view/home.fxml"));
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            window.setTitle("Home");
            window.setScene(new Scene(root, 800, 600));
            // KRJO added these for instant delete account functionality
            systemData.getInstance().reInit();
            systemData.getInstance().setCurrentUserID(systemData.getInstance().getLastUserId());

        }
    }

    @FXML
    private void initialize() {
        list = errorLogTextFlow.getChildren();
        Text inputText = new Text("All fields - marked with \'*\' must to be completed");
        list.add(inputText);
        initCreatorText();
        initializeGender();
    }

    private void initCreatorText() {
        Text text = new Text("You have a brilliant opportunity to be a creator in our team." +
                " Your task is to create new exams from existing Learning Units, handle user requests " +
                "on creating new Learning Units, accepting or declining. You would be a part of our team, " +
                "and could help us with development of our application." );
        text.setFont(Font.font("System", FontWeight.NORMAL, 18));
        beCreatorText.getChildren().add(text);
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
