package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import service.LearningInstance;

public class CategoriesController extends Controller {

    @FXML
    Button goToExamsButton;
    @FXML
    TreeView<LearningInstance> categoriesTree;
    @FXML
    private void initialize() {
        buildTree(categoriesTree);

    }

}
