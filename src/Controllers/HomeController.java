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
public class HomeController {
    @FXML
    Button logOutbutton;
    @FXML
    Button homeButton;
    @FXML
    Button myContentButton;
    @FXML
    Button createButton;
    @FXML
    Button settingsButton;
    @FXML
    ChoiceBox<String> searchChoiceBox;
    @FXML
    TextField searchTextField;
    @FXML
    TreeView<LearningInstance> mainTree;
    @FXML
    TextFlow firstRecommendation;
    @FXML
    TextFlow secondRecommendation;
    @FXML
    TextFlow thirdRecommendation;
    @FXML
    TextFlow fourthRecommendation;

    ObservableList listFirstRec;
    ObservableList listSecondRec;
    ObservableList listThirdRec;
    ObservableList listFourthRec;

    Parent root;

    @FXML
    private void initialize() {
        homeButton.setDisable(true);
        treeInitializer();
        choiceBoxInitializer();
        recommendationsInitializer();
    }

    public void logOutButtonClicked(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource("../resources/view/login.fxml"));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        window.setTitle("OADTurk");
        window.setScene(new Scene(root, 800, 600));
    }

    public void searchOnAction() {
        //TODO: implement search functionality
        System.out.println(getChoice() + searchTextField.getText());
    }

    //Return the searching area from ChoiceBox
    private String getChoice() {
        return searchChoiceBox.getValue();

    }

    //This function make the new branch of treeView
    private TreeItem<LearningInstance> makeBranch(LearningInstance li, TreeItem<LearningInstance> parent) {
        TreeItem<LearningInstance> item = new TreeItem<>(li);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    //INITIALIZE FUNCTIONS :

    //Initialize choiceBox
    private void choiceBoxInitializer() {
        searchChoiceBox.getItems().addAll("Learning Applications", "Learning Categories",
                "Learning Units");
        searchChoiceBox.setValue("Learning Applications");

    }

    //Initialize the tree with LA and LC
    private void treeInitializer() {
        TreeItem<LearningInstance> rootTree = new TreeItem<>(new LearningInstance(0,"root","root"));
        rootTree.setExpanded(true);
        ArrayList<TreeItem<LearningInstance>> LAitems = new ArrayList<>();
        mainTree.setRoot(rootTree);
        mainTree.setShowRoot(false);
        int counter = 0;
        for(LearningApplication la : systemData.getInstance().getDataLA()) {
            LAitems.add(makeBranch(la, rootTree));
            for(LearningCategory lc : systemData.getInstance().getDataLC()) {
                if(lc.getLa_id() == la.getId()) {
                    makeBranch(lc, LAitems.get(counter));
                }
            }
            counter++;
        }

        mainTree.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    System.out.println(newValue.getValue().getDescription());
                    //TODO: open scenes of LA/LC depending on choice
                }));
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
