package Controllers;


import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import service.LearningApplication;
import service.LearningCategory;
import service.LearningInstance;
import service.systemData;
import java.util.ArrayList;

/** singleton class with Tree functionality
 *
 */
class TreeController extends Controller {

    // member variables
    TreeView<LearningInstance> mainTree;
    private boolean newTreeItemFlag;
    private TreeItem<LearningInstance> rootTree;

    // singleton instance and constructor
    private static TreeController instance;
    private TreeController() {
        newTreeItemFlag = true;
    }

    static TreeController getInstance() {
        if (TreeController.instance == null)
            TreeController.instance = new TreeController();

        return TreeController.instance;
    }

    /** This function makes the new branch of treeView
     *
     * @param li LearningInstance object
     * @param parent corresponding tree root
     */
    private TreeItem<LearningInstance> makeBranch(LearningInstance li, TreeItem<LearningInstance> parent) {
        TreeItem<LearningInstance> item = new TreeItem<>(li);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    /** handles click action on tree item
     *
     */
    private void actionHandler(){
        mainTree.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                        // System.out.println(newValue.getValue().getDescription());
                        //TODO: make the screens of LA/LC dynamic corresponding to their application or category
                        //TODO: enable double click for collapsing/expanding instead of opening new screen
                        if (newValue.getValue() instanceof LearningApplication)
                            newScene((Stage)mainTree.getParent().getScene().getWindow(), "LA.fxml");
                        if (newValue.getValue() instanceof LearningCategory)
                            newScene((Stage)mainTree.getParent().getScene().getWindow(), "categories.fxml");
                }));

    }

    /** Initialize the tree with LA and LC and update it when more data is added
     *
     */
    void treeInitializer() {
        if (rootTree == null){
            rootTree = new TreeItem<>(new LearningInstance(0,"root","root"));
            rootTree.setExpanded(true);
        }
        else{
            TreeItem<LearningInstance> newRoot = new TreeItem<>
                    (new LearningInstance(0, "root", "root"));
            newRoot.setExpanded(true);
            for (TreeItem<LearningInstance> child : rootTree.getChildren()) {
                newRoot.getChildren().add(child);
            }
            rootTree = newRoot;

        }

        mainTree.setRoot(rootTree);
        mainTree.setShowRoot(false);
        if (newTreeItemFlag) {
            int LACounter = 0;
            ArrayList<TreeItem<LearningInstance>> LAItems = new ArrayList<>();
            for(LearningApplication la : systemData.getInstance().getDataLA()) {
                LAItems.add(makeBranch(la, rootTree));
                for(LearningCategory lc : systemData.getInstance().getDataLC()) {
                    if(lc.getLa_id() == la.getId()) {
                        makeBranch(lc, LAItems.get(LACounter));
                    }
                }
                LACounter++;
            }
            newTreeItemFlag = false;
        }
        actionHandler();
    }

    /** sets the newTreeItemFlag to true
     *  should be called when data for the tree is added
     */
    void updateTree(){
        newTreeItemFlag = true;
    }
}
