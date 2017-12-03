package Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.LearningInstance;

import java.io.IOException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**class which contains the standard controller functionality
 *
 */
public class Controller {

    @FXML
    Button homeButton;
    @FXML
    Button myContentButton;
    @FXML
    Button createButton;
    @FXML
    Button settingsButton;
    @FXML
    Button logOutButton;

    /**function to add a new scene to active stage
     *
     * @param window    corresponding window
     * @param newFxml   new .fxml file (scene) to load
     */
    void newScene(Stage window, String newFxml) {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("../resources/view/" + newFxml));
            //create a new scene with root and set the stage
            double width = window.getScene().getWidth();
            double height = window.getScene().getHeight();
            Scene scene = new Scene(root, width, height);
            window.setScene(scene);
            window.show();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    /**function to set up tree on new scene
     *
     * @param tree  TreeView item of current screen
     */
    void buildTree(TreeView<LearningInstance> tree){
        TreeController.getInstance().mainTree = tree;
        TreeController.getInstance().treeInitializer();

    }

    // button functions

    public void homeButtonClick(ActionEvent event) {

        newScene((Stage)((Node)event.getSource()).getScene().getWindow(), "home.fxml");

    }

    public void settingsButtonClick(ActionEvent event) {

        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/view/settings.fxml"));
            root = fxmlLoader.load();
            //create a new scene with root and set the stage
            double width = 600;
            double height = 400;

            // New stage init
            Stage window = new Stage();
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL); // block main stage during this stage is open
            window.setTitle("Settings");

            SettingsController controller = fxmlLoader.getController();
            controller.parentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            //Scene
            Scene scene = new Scene(root, width, height);
            //Start
            window.setScene(scene);
            window.show();
            // with this call the curser does not jump automatically into the first text field
            Platform.runLater(()->root.requestFocus());

        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    public void logOutButtonClicked(ActionEvent event) {

        newScene((Stage)((Node)event.getSource()).getScene().getWindow(), "login.fxml");

    }

    public void myContentButtonClick(ActionEvent event) {

        newScene((Stage)((Node)event.getSource()).getScene().getWindow(), "myContent.fxml");

    }

}
