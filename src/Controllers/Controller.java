package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import service.LearningInstance;

import java.io.IOException;

/**class which contains the standard controller functionality
 *
 */
public class Controller {

    // TODO: logout button on every screen

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
            Scene scene = new Scene(root, 800, 600);
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

        newScene((Stage)((Node)event.getSource()).getScene().getWindow(), "settings.fxml");

    }

    public void logOutButtonClicked(ActionEvent event) {

        newScene((Stage)((Node)event.getSource()).getScene().getWindow(), "login.fxml");

    }

    public void myContentButtonClick(ActionEvent event) {

        newScene((Stage)((Node)event.getSource()).getScene().getWindow(), "myContent.fxml");

    }

}
