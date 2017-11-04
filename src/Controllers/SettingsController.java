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

        buildTree(settingsTree);
    }
}
