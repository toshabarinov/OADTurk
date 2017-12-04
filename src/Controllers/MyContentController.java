package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import service.LearningInstance;

public class MyContentController extends Controller {

    @FXML
    TreeView<LearningInstance> myContentTree;

    @FXML
    private void initialize() {
        myContentButton.setDisable(true);

        buildTree(myContentTree);
    }
}
