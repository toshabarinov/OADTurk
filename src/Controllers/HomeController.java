package Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import service.LearningInstance;
import service.currentUser;
import service.systemData;
import java.util.ArrayList;

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
    ListView<LearningInstance> searchListView;
    @FXML
    TreeView<LearningInstance> homeTree;

    ObservableList listFirstRec;
    ObservableList listSecondRec;



    @FXML
    private void initialize() {
        if(!currentUser.getInstance().isAdmin() && !currentUser.getInstance().isCreator()) {
            adminPanelButton.setVisible(false);
        }
        statementLabel.setText("Welcome Mr. " + currentUser.getInstance().getUser_surname());
        homeButton.setDisable(true);
        buildTree(homeTree);
        choiceBoxInitializer();
        recommendationsInitializer();
        System.out.println("test");
    }

    public void searchOnAction() {
        String searchText = searchTextField.getText();
        ArrayList<LearningInstance> output = new ArrayList<>();
        switch (getChoice()) {
            case "Learning Categories" : {
                output = systemData.getInstance().search(2, searchText);
                break;
            }
            case "Learning Applications" : {
                output = systemData.getInstance().search(1, searchText);
                break;
            }
            case "Learning Units" : {
                //TODO: implement LU
            }
        }
        initializeCListView(output);
    }

    private void initializeCListView(ArrayList<LearningInstance> searchResult) {
        searchListView.getItems().clear();
        ObservableList<LearningInstance> observableList = searchListView.getItems();
        for(LearningInstance li : searchResult) {
            observableList.add(li);
        }
        searchListView.getSelectionModel().select(0);
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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


    public void secondRecClicked() {
        System.out.println("second");
    }

    public void firstRecClicked() {
        System.out.println("first");
    }

    //Initialize 4 recommendations blocks
    private void recommendationsInitializer() {
        //TODO: implement recommendation functionality depending on users preferences
        listFirstRec = firstRecommendation.getChildren();
        listSecondRec = secondRecommendation.getChildren();
        listFirstRec.add(new Text("First\nRecommendation"));
        listSecondRec.add(new Text("Second\nRecommendation"));
    }
}
