package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import service.LearningInstance;
import service.systemData;

public class CategoriesController extends Controller {

    @FXML
    Button goToExamsButton;
    @FXML
    TextField searchTextField;
    @FXML
    Text LCName;
    @FXML
    Text LCDescription;
    @FXML
    TreeView<LearningInstance> categoriesTree;
    @FXML
    private void initialize() {
        buildTree(categoriesTree);
        LCName.setText(systemData.getInstance().getActiveLI().getName());
        LCDescription.setText(systemData.getInstance().getActiveLI().getDescription());
    }

    public void searchOnAction() {
        //TODO: implement search functionality
        System.out.println(searchTextField.getText());
    }
}
