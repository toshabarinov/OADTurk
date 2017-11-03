package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import service.LearningApplication;
import service.LearningCategory;
import service.LearningInstance;
import service.systemData;

import java.util.ArrayList;

// singleton class with Tree functionality
class TreeController extends Controller {

    private static TreeController instance;
    private TreeController() {}

    static TreeController getInstance() {
        if (TreeController.instance == null)
            TreeController.instance = new TreeController();

        return TreeController.instance;
    }

    TreeView<LearningInstance> mainTree;

    //This function make the new branch of treeView
    private TreeItem<LearningInstance> makeBranch(LearningInstance li, TreeItem<LearningInstance> parent) {
        TreeItem<LearningInstance> item = new TreeItem<>(li);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    public void actionHandler(TreeView<LearningInstance> tree){
        tree.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    // System.out.println(newValue.getValue().getDescription());
                    //TODO: make the screens of LA/LC dynamic corresponding to their application or category
                    //TODO: enable double click for colapsing/expanding instead of opening new screen
                    if (newValue.getValue() instanceof LearningApplication)
                        newScene((Stage)tree.getParent().getScene().getWindow(), "LA.fxml");
                    if (newValue.getValue() instanceof LearningCategory)
                        newScene((Stage)tree.getParent().getScene().getWindow(), "categories.fxml");
                }));

    }

    //Initialize the tree with LA and LC
    void treeInitializer() {
        TreeItem<LearningInstance> rootTree = new TreeItem<>(new LearningInstance(0,"root","root"));
        rootTree.setExpanded(true);
        ArrayList<TreeItem<LearningInstance>> LAitems = new ArrayList<>();
        mainTree.setRoot(rootTree);
        mainTree.setShowRoot(false);
        int counter = 0;
        for(LearningApplication la : systemData.getInstance().getDataLA()) {
            LAitems.add(makeBranch(la, rootTree));
            for(LearningCategory lc : systemData.getInstance().getDataLC()) {
                if(lc.getLa_id() == la.getId()) {
                    makeBranch(lc, LAitems.get(counter));
                }
            }
            counter++;
        }

        actionHandler(mainTree);

    }

}
