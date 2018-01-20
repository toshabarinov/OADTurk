package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import service.*;

import java.util.List;
import java.util.Map;

public class LUController extends Controller {

    @FXML
    TreeView<LearningInstance> LUTree;
    @FXML
    public Text titleText;
    @FXML
    public Text questionText;
    @FXML
    public Text answerText1;
    @FXML
    public Text answerText2;
    @FXML
    public Text answerText3;
    @FXML
    public Text answerText4;
    @FXML
    public CheckBox checkBox1;
    @FXML
    public CheckBox checkBox2;
    @FXML
    public CheckBox checkBox3;
    @FXML
    public CheckBox checkBox4;
    @FXML
    public Button confirmButton;
    @FXML
    public Label textBox;

    public WrappedImageView questionPic;
    public WrappedImageView answerPic;
    @FXML
    VBox imageVBox;
    @FXML
    VBox qImageVBox;
    @FXML
    HBox aImageHBox1;
    @FXML
    HBox aImageHBox2;
    @FXML
    HBox aImageHBox3;

    LearningUnit learningUnit;
    private String correctAnswers;
//    private Map<Integer, LearningUnit> learningUnitMap;
//    private List<LearningUnit> learningUnitList;



    @FXML
    private void initialize() {
        viewInit();
        if(!currentUser.getInstance().isAdmin() && !currentUser.getInstance().isCreator()) {
            adminPanelButton.setVisible(false);
        }
        buildTree(LUTree);
        textBox.setText("");

//        learningUnitList = systemData.getInstance().getLearningUnitList();
//        createHashMap();
//        LearningUnit learningUnit = learningUnitMap.get(systemData.getInstance().getLastLUid());
    }


    public void confirmButtonClick(ActionEvent event) {
        correctAnswers = learningUnit.correctAnswers;
        String inputAnswers;
        if (!(learningUnit instanceof LuFigureFigure)){
            inputAnswers = toNumeralString(checkBox1.isSelected()) + toNumeralString(checkBox2.isSelected()) +
                    toNumeralString(checkBox3.isSelected()) + toNumeralString(checkBox4.isSelected());
        }
        else{
            inputAnswers = toNumeralString(checkBox1.isSelected()) + toNumeralString(checkBox2.isSelected()) +
                    toNumeralString(checkBox3.isSelected());
        }
        if (inputAnswers.equals(correctAnswers)){
            textBox.setTextFill(Color.web("#33cc33"));
            textBox.setText("Answer correct");
        }
        else{
            StringBuilder outputString = new StringBuilder("The correct answers are ");
            for (int i = 0; i < correctAnswers.length(); i++){
                if (correctAnswers.charAt(i) == '1'){
                    outputString.append(Integer.toString(i+1));
                    outputString.append(", ");
                }
            }
            if (outputString.toString().charAt(outputString.length()-2) == ','){
                outputString.setCharAt(outputString.length()-1, '\0');
                outputString.setCharAt(outputString.length()-2, '.');
            }
            else{
                outputString = new StringBuilder("No answer is correct.");
            }
            textBox.setTextFill(Color.web("#e60000"));
            textBox.setText("Wrong Answer; " + outputString.toString());
        }
    }

    public void setUp(){
        // move redundant stuff in super class -> LearningUnit
        if(learningUnit instanceof LuText){
            titleText.setText(((LuText) learningUnit).titleText);
            questionText.setText(((LuText) learningUnit).questionText);
            answerText1.setText(((LuText) learningUnit).answerText1);
            answerText2.setText(((LuText) learningUnit).answerText2);
            answerText3.setText(((LuText) learningUnit).answerText3);
            answerText4.setText(((LuText) learningUnit).answerText4);
        }
        else if (learningUnit instanceof LuFigureText){
            if (((LuFigureText) learningUnit).questionFigure != null){
                questionPic = new WrappedImageView();
                imageVBox.getChildren().add(questionPic);
                questionPic.setImage(((LuFigureText) learningUnit).questionFigure);
            }
            else
                VBox.setVgrow(imageVBox, Priority.NEVER);

            titleText.setText(((LuFigureText) learningUnit).titleText);
            questionText.setText(((LuFigureText) learningUnit).questionText);
            answerText1.setText(((LuFigureText) learningUnit).answerText1);
            answerText2.setText(((LuFigureText) learningUnit).answerText2);
            answerText3.setText(((LuFigureText) learningUnit).answerText3);
            answerText4.setText(((LuFigureText) learningUnit).answerText4);
        }
        else if (learningUnit instanceof LuFigureFigure){
            if (((LuFigureFigure) learningUnit).questionFigure != null){
                questionPic = new WrappedImageView();
                qImageVBox.getChildren().add(questionPic);
                questionPic.setImage(((LuFigureFigure) learningUnit).questionFigure);
            }
            else
                VBox.setVgrow(qImageVBox, Priority.NEVER);

            titleText.setText(((LuFigureFigure) learningUnit).titleText);
            questionText.setText(((LuFigureFigure) learningUnit).questionText);

            int index = 0;
            answerPic = new WrappedImageView();
            aImageHBox1.getChildren().add(index, answerPic);
            answerPic.setImage(((LuFigureFigure) learningUnit).answerFigure1);
            answerPic = new WrappedImageView();
            aImageHBox2.getChildren().add(index, answerPic);
            answerPic.setImage(((LuFigureFigure) learningUnit).answerFigure2);
            answerPic = new WrappedImageView();
            aImageHBox3.getChildren().add(index, answerPic);
            answerPic.setImage(((LuFigureFigure) learningUnit).answerFigure3);

        }

        //else if (learningUnit instanceof L)
        correctAnswers = learningUnit.correctAnswers;
        // TODO JO adapt for use with pictures
        if (!(learningUnit instanceof LuFigureFigure)){
            if(answerText1.getText().equals(""))
                checkBox1.setVisible(false);
            if(answerText2.getText().equals(""))
                checkBox2.setVisible(false);
            if(answerText3.getText().equals(""))
                checkBox3.setVisible(false);
            if(answerText4.getText().equals(""))
                checkBox4.setVisible(false);
        }
        else{
            //TODO JO maybe do something to disable checkboxes and on the right side
        }
    }




//    private void createHashMap() {
//        learningUnitMap = new HashMap<Integer, LearningUnit>();
//
//        for (LearningUnit aLearningUnitList : learningUnitList) {
//
//            learningUnitMap.put(aLearningUnitList.getId(), aLearningUnitList);
//        }
//    }


}
