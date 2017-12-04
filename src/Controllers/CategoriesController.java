package Controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import service.LearningInstance;
import service.LearningUnit;
import service.systemData;

import java.awt.event.MouseEvent;
import java.util.*;

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
    ListView<String> lisViewLU;

    Map<Integer, List<LearningUnit>> learningUnitMap;
    List<LearningUnit> learningUnitList;
    List<String> listForListView;

    Map<String, LearningUnit> mapOFleaningUnit;

    @FXML
    private void initialize() {
        buildTree(categoriesTree);

        learningUnitMap = systemData.getInstance().getLearningUnitMap();
        createList(systemData.getInstance().getLastCategoryId());

        ListProperty<String> listProperty = new SimpleListProperty<>();
        ObservableList<String> list = FXCollections.observableArrayList(createList());
        listProperty.set(FXCollections.observableArrayList(list));
        lisViewLU.itemsProperty().bind(listProperty);
        lisViewLU.setVisible(true);

        createHashMap();

        lisViewLU.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                systemData.getInstance().setLastLUid(mapOFleaningUnit.get(newValue).getId());

                if (mapOFleaningUnit.get(newValue).getQuestion_type().equals(0)) {
                    newScene((Stage) lisViewLU.getParent().getScene().getWindow(), "LU.fxml");
                } else if (mapOFleaningUnit.get(newValue).getQuestion_type().equals(1)) {
                    if (mapOFleaningUnit.get(newValue).getAnswer_type() == 0) {
                        newScene((Stage) lisViewLU.getParent().getScene().getWindow(), "LU1.fxml");
                    } else if (mapOFleaningUnit.get(newValue).getAnswer_type() == 1) {
                        newScene((Stage) lisViewLU.getParent().getScene().getWindow(), "LU2.fxml");
                    } else if (mapOFleaningUnit.get(newValue).getAnswer_type() == 2) {
                        newScene((Stage) lisViewLU.getParent().getScene().getWindow(), "LU3.fxml");
                    }
                }
            }
        });
    }

    private void createList(int categoryId) {

        learningUnitList = learningUnitMap.get(categoryId);

    }

    private List<String> createList(){

        List<String> returnList = new ArrayList<>();
        for (int count = 0; count < learningUnitList.size(); count++){

            returnList.add(learningUnitList.get(count).getName());

        }
        listForListView = new ArrayList<>(returnList);
        return returnList;

    }

    private void createHashMap(){

        mapOFleaningUnit = new HashMap<String, LearningUnit>();

        for(int count = 0; count < learningUnitList.size(); count++){

            mapOFleaningUnit.put(learningUnitList.get(count).getName(), learningUnitList.get(count));
        }


    }



}


