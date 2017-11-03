package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import service.LearningInstance;

public class SettingsController extends Controller {

    @FXML
    TreeView<LearningInstance> settingsTree;

    @FXML
    private void initialize() {
        settingsButton.setDisable(true);
        // get tree root from singleton class
        settingsTree.setRoot(TreeController.getInstance().mainTree.getRoot());
        settingsTree.setShowRoot(false);
        // start actionHandler with tree from this scene
        TreeController.getInstance().actionHandler(settingsTree);

    }
}
