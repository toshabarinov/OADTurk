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
        // get tree root from singleton class
        categoriesTree.setRoot(TreeController.getInstance().mainTree.getRoot());
        categoriesTree.setShowRoot(false);
        // start actionHandler with tree from this scene
        TreeController.getInstance().actionHandler(categoriesTree);

    }

}
