package Controllers;


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

    // member variables

    TreeView<LearningInstance> mainTree;
    private int LAcounter = 0;
    private boolean newTreeItemFlag = false;        //< should be set when tree gets updated
    private ArrayList<TreeItem<LearningInstance>> LAitems = new ArrayList<>();
    private TreeItem<LearningInstance> rootTree;

    // singleton instance and constructor
    private static TreeController instance;
    private TreeController() {}

    static TreeController getInstance() {
        if (TreeController.instance == null)
            TreeController.instance = new TreeController();

        return TreeController.instance;
    }

    // function gets called at first initialisation
    private void start(){
        rootTree = new TreeItem<>(new LearningInstance(0,"root","root"));
        rootTree.setExpanded(true);
    }

    //This function makes the new branch of treeView
    private TreeItem<LearningInstance> makeBranch(LearningInstance li, TreeItem<LearningInstance> parent) {
        TreeItem<LearningInstance> item = new TreeItem<>(li);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }


    public void actionHandler(){
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

    void actionHandler(TreeView<LearningInstance> tree){
        tree.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    // System.out.println(newValue.getValue().getDescription());
                    //TODO: make the screens of LA/LC dynamic corresponding to their application or category
                    //TODO: enable double click for colapsing/expanding instead of opening new screen
                    if (newValue.getValue() instanceof LearningApplication)
                        try {
                            newScene((Stage)tree.getParent().getScene().getWindow(), "LA.fxml");
                        }
                        catch (NullPointerException e){
                            // TODO: figure out why error when arrow in tree is clicked
                            System.out.println(e.getMessage());
                        }
                    if (newValue.getValue() instanceof LearningCategory)
                        newScene((Stage)tree.getParent().getScene().getWindow(), "categories.fxml");
                }));

    }

    //Initialize the tree with LA and LC
    void treeInitializer() {
        if (rootTree == null)
            start();
        mainTree.setRoot(rootTree);
        mainTree.setShowRoot(false);
        if ((LAitems.size() == 0) |  newTreeItemFlag) {
            for(LearningApplication la : systemData.getInstance().getDataLA()) {
                LAitems.add(makeBranch(la, rootTree));
                for(LearningCategory lc : systemData.getInstance().getDataLC()) {
                    if(lc.getLa_id() == la.getId()) {
                        makeBranch(lc, LAitems.get(LAcounter));
                    }
                }
                LAcounter++;
            }
        }
    }
}
