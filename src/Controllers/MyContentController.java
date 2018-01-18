package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import service.LearningInstance;
import service.currentUser;

public class MyContentController extends Controller {

    @FXML
    TreeView<LearningInstance> myContentTree;

    @FXML
    private void initialize() {
        viewInit();
        myContentButton.setDisable(true);

        buildTree(myContentTree);
    }
}
