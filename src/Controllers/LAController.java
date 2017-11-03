package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import service.LearningInstance;


public class LAController extends Controller{

    @FXML
    Button goToExamsButton;
    @FXML
    TreeView<LearningInstance> LATree;
    @FXML
    private void initialize() {
        // get tree root from singleton class
        LATree.setRoot(TreeController.getInstance().mainTree.getRoot());
        LATree.setShowRoot(false);
        // start actionHandler with tree from this scene
        TreeController.getInstance().actionHandler(LATree);

    }

}
