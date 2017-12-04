package Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import service.LearningInstance;

/**
 * Created by a1 on 28.10.17.
 */
public class HomeController extends Controller{
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
