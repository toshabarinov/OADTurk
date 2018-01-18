package Controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.*;

import java.net.Inet4Address;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LUController extends Controller {

    public javafx.scene.text.Text object1_parameters;
    public javafx.scene.text.Text object1_num;
    public javafx.scene.text.Text relationship;
    public javafx.scene.text.Text object2_num;
    public javafx.scene.text.Text object1;
    public javafx.scene.text.Text object2_parameters;
    public javafx.scene.text.Text object2;

    @FXML
    public javafx.scene.text.Text title;
    @FXML
    public javafx.scene.text.Text question;

    public javafx.scene.text.Text text1;
    public javafx.scene.text.Text text2;
    public javafx.scene.text.Text text3;
    public javafx.scene.text.Text question1;
    public javafx.scene.text.Text object11;
    public javafx.scene.text.Text object1_parameters1;
    public javafx.scene.text.Text object1_num1;
    public javafx.scene.text.Text object2_num1;
    public javafx.scene.text.Text object2_parameters1;
    public javafx.scene.text.Text object2_name1;
    public javafx.scene.text.Text object12;
    public javafx.scene.text.Text object1_parameters2;
    public javafx.scene.text.Text object1_num2;
    public javafx.scene.text.Text relationship2;
    public javafx.scene.text.Text object2_num2;
    public javafx.scene.text.Text object2_parameters2;
    public javafx.scene.text.Text object2_name2;
    public javafx.scene.text.Text relationship1;
    public Text object2_name;
    public Text first_text;
    public Text second_text;
    public Text third_text;
    public Text fourth_text;
    public Text first_text1;
    public Text second_text1;
    public Text third_text1;
    public Text fourth_text1;
    public Text first_text2;
    public Text second_text2;
    public Text third_text2;
    public Text fourth_text2;


    private Map<Integer, LearningUnit> learningUnitMap;
    private List<LearningUnit> learningUnitList;

    @FXML
    TreeView<LearningInstance> settingsTree;

    @FXML
    private void initialize() {
        viewInit();
        buildTree(settingsTree);

        learningUnitList = systemData.getInstance().getLearningUnitList();
        createHashMap();

        LearningUnit learningUnit = learningUnitMap.get(systemData.getInstance().getLastLUid());

        if (learningUnit.getQuestion_type() == 0) {
            fillType0(learningUnit);
            setText(learningUnit);
        } else {
            fillType1(learningUnit);
            if (learningUnit.getAnswer_type() == 0) {
                setText(learningUnit);
            } else if (learningUnit.getAnswer_type() == 1) {
                fillDiagrams(learningUnit);
            } else if (learningUnit.getAnswer_type() == 2) {
                fillPictures(learningUnit);
            }

        }
    }

    private void fillPictures(LearningUnit learningUnit) {
        question.setText(learningUnit.getQuestion());
        Map<Integer, LuTextPicture> luTextPictureMap = systemData.getInstance().getLuTextPictureMap();
        LuTextPicture luTextPicture = luTextPictureMap.get(learningUnit.getAnswer_id1());

        first_text.setText(luTextPicture.getText1());
        second_text.setText(luTextPicture.getText2());
        third_text.setText(luTextPicture.getText3());
        fourth_text.setText(luTextPicture.getText4());

        luTextPicture = luTextPictureMap.get(learningUnit.getAnswer_id2());

        first_text1.setText(luTextPicture.getText1());
        second_text1.setText(luTextPicture.getText2());
        third_text1.setText(luTextPicture.getText3());
        fourth_text1.setText(luTextPicture.getText4());

        luTextPicture = luTextPictureMap.get(learningUnit.getAnswer_id3());

        first_text2.setText(luTextPicture.getText1());
        second_text2.setText(luTextPicture.getText2());
        third_text2.setText(luTextPicture.getText3());
        fourth_text2.setText(luTextPicture.getText4());
    }

    private void fillDiagrams(LearningUnit learningUnit) {

        Map<Integer, LuDiagram> luDiagramMap = systemData.getInstance().getLuDiagramMap();
        int id = learningUnit.getAnswer_id1();

        LuDiagram luDiagram = luDiagramMap.get(id);

        object1.setText(luDiagram.getObject1_name());

        object1_parameters.setText(luDiagram.getObject1_parameters());
        object1_num.setText("[" + luDiagram.getObject1_num() + "]");
        relationship.setText(luDiagram.getRelationship());
        object2_num.setText("[" + luDiagram.getObject2_num() + "]");
        object2_name.setText(luDiagram.getObject2_name());
        object2_parameters.setText(luDiagram.getObject2_parameters());


        id = learningUnit.getAnswer_id2();

        luDiagram = luDiagramMap.get(id);


        object11.setText(luDiagram.getObject1_name());
        object1_parameters1.setText(luDiagram.getObject1_parameters());
        object1_num1.setText("[" + luDiagram.getObject1_num() + "]");
        relationship1.setText(luDiagram.getRelationship());
        object2_num1.setText("[" + luDiagram.getObject2_num() + "]");
        object2_name1.setText(luDiagram.getObject2_name());
        object2_parameters1.setText(luDiagram.getObject2_parameters());

        id = learningUnit.getAnswer_id3();

        luDiagram = luDiagramMap.get(id);


        object12.setText(luDiagram.getObject1_name());
        object1_parameters2.setText(luDiagram.getObject1_parameters());
        object1_num2.setText("[" + luDiagram.getObject1_num() + "]");
        relationship2.setText(luDiagram.getRelationship());
        object2_num2.setText("[" + luDiagram.getObject2_num() + "]");
        object2_name2.setText(luDiagram.getObject2_name());
        object2_parameters2.setText(luDiagram.getObject2_parameters());

    }

    private void fillType1(LearningUnit learningUnit) {
        question.setText(learningUnit.getQuestion());
        Map<Integer, LuText> luTextMap = systemData.getInstance().getLuTextMap();
        LuText luText = luTextMap.get(learningUnit.getQuestion_id());
        question1.setText(luText.getText());


    }

    private void fillType0(LearningUnit learningUnit) {

        question.setText(learningUnit.getQuestion());

        LuDiagram luDiagram = systemData.getInstance().getLuDiagramMap().get(learningUnit.getCategory_id());
        object1.setText(luDiagram.getObject1_name());
        object1_parameters.setText(luDiagram.getObject1_parameters());
        object1_num.setText("[" + luDiagram.getObject1_num() + "]");
        relationship.setText(luDiagram.getRelationship());
        object2_num.setText("[" + luDiagram.getObject2_num() + "]");
        object2.setText(luDiagram.getObject2_name());
        object2_parameters.setText(luDiagram.getObject2_parameters());

    }

    private void setText(LearningUnit learningUnit) {
        Map<Integer, LuText> luTextMap = systemData.getInstance().getLuTextMap();
        LuText luText = luTextMap.get(learningUnit.getAnswer_id1());
        text1.setText(luText.getText());
        luText = luTextMap.get(learningUnit.getAnswer_id2());
        text2.setText(luText.getText());
        luText = luTextMap.get(learningUnit.getAnswer_id3());
        text3.setText(luText.getText());

    }

    private void createHashMap() {
        learningUnitMap = new HashMap<Integer, LearningUnit>();

        for (int i = 0; i < learningUnitList.size(); i++) {

            learningUnitMap.put(learningUnitList.get(i).getId(), learningUnitList.get(i));
        }
    }


}
