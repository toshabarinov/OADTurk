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
import service.LearningApplication;
import service.LearningCategory;
import service.LearningInstance;
import service.systemData;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a1 on 28.10.17.
 */
public class HomeController extends Controller{
    @FXML
    Button logOutbutton;
    @FXML
    ChoiceBox<String> searchChoiceBox;
    @FXML
    TextField searchTextField;
    @FXML
    TextFlow firstRecommendation;
    @FXML
    TextFlow secondRecommendation;
    @FXML
    TextFlow thirdRecommendation;
    @FXML
    TextFlow fourthRecommendation;
    @FXML
    TreeView<LearningInstance> homeTree;

    ObservableList listFirstRec;
    ObservableList listSecondRec;
    ObservableList listThirdRec;
    ObservableList listFourthRec;

    @FXML
    private void initialize() {
        homeButton.setDisable(true);
        buildTree(homeTree);
        choiceBoxInitializer();
        recommendationsInitializer();
    }

    public void searchOnAction() {
        //TODO: implement search functionality
        System.out.println(getChoice() + searchTextField.getText());
    }

    //Return the searching area from ChoiceBox
    private String getChoice() {
        return searchChoiceBox.getValue();

    }


    //INITIALIZE FUNCTIONS :

    //Initialize choiceBox
    private void choiceBoxInitializer() {
        searchChoiceBox.getItems().addAll("Learning Applications", "Learning Categories",
                "Learning Units");
        searchChoiceBox.setValue("Learning Applications");

    }


    //Initialize 4 recommendations blocks
    private void recommendationsInitializer() {
        //TODO: implement recommendation functionality depending on users preferences
        listFirstRec = firstRecommendation.getChildren();
        listSecondRec = secondRecommendation.getChildren();
        listThirdRec = thirdRecommendation.getChildren();
        listFourthRec = fourthRecommendation.getChildren();
        listFirstRec.add(new Text("First\nRecommendation"));
        listSecondRec.add(new Text("Second\nRecommendation"));
        listThirdRec.add(new Text("Third\nRecommendation"));
        listFourthRec.add(new Text("Fourth\nRecommendation"));
    }
}
