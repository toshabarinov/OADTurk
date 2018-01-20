package service;

import Controllers.Controller;
import Controllers.LUController;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static java.awt.Event.LOAD_FILE;

public class CreateLUBox extends Controller{

    public Stage parentStage;

    private String questionAnswerCombi;
    private TextField titleText;
    private TextField nameText;
    private TextField questionText;
    private TextField questionFigure;
    private TextField answerText1;
    private TextField answerText2;
    private TextField answerText3;
    private TextField answerText4;
    private ComboBox choseLA;
    private ComboBox choseCategory;
    private CheckBox correctCheckB1;
    private CheckBox correctCheckB2;
    private CheckBox correctCheckB3;
    private CheckBox correctCheckB4;

    private int stringToID(ArrayList<LearningApplication> LAList, String name){
        for (LearningApplication LA : LAList){

            if (LA.name.equals(name)){
                return LA.id;
            }
        }
        throw new NoSuchElementException();
    }
    private void availableCategoriesSet(javafx.event.Event e){
        try {
            boolean noItemsFlag = true;
            String chosenLA = choseLA.getValue().toString();
            int chosenLAid = stringToID(systemData.getInstance().dataLA, chosenLA);
            choseCategory.getItems().removeAll();
            choseCategory.getItems().clear();

            for (LearningCategory cat : systemData.getInstance().dataLC){
                if (cat.la_id == chosenLAid){
                    choseCategory.getItems().add(cat.getName());
                    noItemsFlag = false;
                }
            }
            if (noItemsFlag)
                choseCategory.getItems().add("No Categories in this LA");
        }
        catch (NoSuchElementException err){
            err.printStackTrace();
        }
    }

    private void okButtonClick(ActionEvent event)
    {
        try {
            String titleString = titleText.getText();
            String nameString = nameText.getText();
            String questionString = questionText.getText();
            String questionFigure = this.questionFigure.getText();
            String answerString1 = answerText1.getText();
            String answerString2 = answerText2.getText();
            String answerString3 = answerText3.getText();
            String answerString4 = answerText4.getText();
            String correctString = toNumeralString(correctCheckB1.isSelected()) + toNumeralString(correctCheckB2.isSelected()) +
                    toNumeralString(correctCheckB3.isSelected()) + toNumeralString(correctCheckB4.isSelected());

            // database connenctor
            Connection conn = systemData.getInstance().getDBConnection();
            char questionType = questionAnswerCombi.charAt(0);
            char answerType = questionAnswerCombi.charAt(1);
            //TODO JO fix typo *learning_caterogies*
            // TODO JO handle null pointer exception when combo box not set
            int catID = systemData.getInstance().getCategoryID(choseCategory.getValue().toString(),
                    "learning_caterogies", "lc_id", "lc_name");

            Statement statement = conn.createStatement();
            String query = "INSERT INTO learning_units (refName, question_type, answer_type, category_id) VALUES (" +
                    "\"" + nameString + "\", \"" + questionType + "\", \"" + answerType + "\", \"" + Integer.toString(catID) + "\")";
            statement.executeUpdate(query);
            int LUID = systemData.getInstance().getCategoryID(nameString,
                    "learning_units", "id", "refName");

            switch (questionAnswerCombi){
                case "tt":

                    query = "INSERT INTO lu_text_text (id, refName, title, question, answer1, answer2, answer3, answer4, correctAnswers) VALUES (" +
                            "\"" + LUID + "\", \"" + nameString + "\", \"" + titleString + "\", \"" + questionString +
                            "\", \"" + answerString1 + "\", \"" + answerString2 + "\", \"" + answerString3 + "\", \"" +
                            answerString4 + "\", \"" + correctString + "\")";
                    statement.executeUpdate(query);
                    break;

                case "ft":
                    FileInputStream fis = null;
                    if (!questionFigure.isEmpty()){
                        File image = new File(questionFigure);
                        fis = new FileInputStream(image);
                    }

                    query = "INSERT INTO lu_figure_text (id, refName, title, question_text, question_figure, answer1, " +
                            "answer2, answer3, answer4, correctAnswers) VALUES (?, ? ,? ,?, ?, ?, ? , ?, ?, ?)";
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setInt(1, LUID);
                    pst.setString(2, nameString);
                    pst.setString(3, titleString);
                    pst.setString(4, questionString);
                    pst.setBlob(5, fis);
                    pst.setString(6, answerString1);
                    pst.setString(7, answerString2);
                    pst.setString(8, answerString3);
                    pst.setString(9, answerString4);
                    pst.setString(10, correctString);

                    pst.executeUpdate();
                    break;

                case "ff":
                    FileInputStream qfis = null;
                    FileInputStream afis1 = null;
                    FileInputStream afis2 = null;
                    FileInputStream afis3 = null;
                    if (!questionFigure.isEmpty()){
                        File qImage = new File(questionFigure);
                        qfis = new FileInputStream(qImage);
                    }
                    if (!answerString1.isEmpty()){
                        File aImage1 = new File(answerString1);
                        afis1 = new FileInputStream(aImage1);
                    }
                    if (!answerString2.isEmpty()){
                        File aImage2 = new File(answerString2);
                        afis2 = new FileInputStream(aImage2);
                    }
                    if (!answerString3.isEmpty()){
                        File aImage3 = new File(answerString3);
                        afis3 = new FileInputStream(aImage3);
                    }

                    query = "INSERT INTO lu_figure_figure (id, refName, title, question_text, question_figure, answer1," +
                            " answer2, answer3, correctAnswers) VALUES (?, ? ,? ,?, ?, ?, ? , ?, ?)";
                    PreparedStatement pst2 = conn.prepareStatement(query);
                    pst2.setInt(1, LUID);
                    pst2.setString(2, nameString);
                    pst2.setString(3, titleString);
                    pst2.setString(4, questionString);
                    pst2.setBlob(5, qfis);
                    pst2.setBlob(6, afis1);
                    pst2.setBlob(7, afis2);
                    pst2.setBlob(8, afis3);
                    pst2.setString(9, correctString);

                    pst2.executeUpdate();
                    break;

            }

            // close settings screen and open new LU
            systemData.getInstance().reInit();
            ( (Stage)((Node)event.getSource()).getScene().getWindow() ).close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void display(String questionType, String answerType) {
        // only use first letter in lower case
        questionAnswerCombi = questionType.toLowerCase().substring(0,1) +
                answerType.toLowerCase().substring(0,1);

        // New stage init
        Stage window = new Stage();
        window.setResizable(true);
        window.initModality(Modality.APPLICATION_MODAL); // block main stage during this stage is open
        window.setTitle("Create LU");

        // New elements init
        Button okButton = new Button("Ok");
        okButton.setMinWidth(100);
        //okButton.setOnAction(e-> window.close());
        okButton.setOnAction(this::okButtonClick);

        Label message = new Label("Define your Learning Unit");
        Label correctText = new Label("Correct");

        choseLA = new ComboBox();
        choseLA.setPromptText("Chose LA");
        choseLA.setPrefWidth(150);
        choseLA.getItems().removeAll();
        for (LearningApplication la : systemData.getInstance().dataLA){
            choseLA.getItems().add(la.getName());
        }
        choseLA.setOnAction(e->availableCategoriesSet(e));

        choseCategory = new ComboBox();
        choseCategory.setPromptText("Chose category");
        choseCategory.setPrefWidth(150);
        choseCategory.getItems().removeAll();
        choseCategory.getItems().add("chose LA first");


        titleText = new TextField();
        titleText.setPromptText("Enter LU title");

        nameText = new TextField();
        nameText.setPromptText("Enter LU reference name");

        // question and answer fields
        questionText = new TextField();
        questionFigure = new TextField();
        answerText1 = new TextField();
        answerText2 = new TextField();
        answerText3= new TextField();
        answerText4 = new TextField();
        correctCheckB1 = new CheckBox();
        correctCheckB2 = new CheckBox();
        correctCheckB3 = new CheckBox();
        correctCheckB4 = new CheckBox();

        //Layout
        VBox layout = new VBox(20);
        VBox layoutAnswers = new VBox(10);
        HBox correctTextB = new HBox(10);
        HBox answerCheck1 = new HBox(20);
        HBox answerCheck2 = new HBox(20);
        HBox answerCheck3 = new HBox(20);
        HBox answerCheck4 = new HBox(20);

        correctTextB.setAlignment(Pos.TOP_RIGHT);
        correctTextB.getChildren().add(correctText);
        answerCheck1.setAlignment(Pos.CENTER);
        answerCheck1.setPadding(new Insets(0, 10, 0, 0));
        answerCheck2.setAlignment(Pos.CENTER);
        answerCheck2.setPadding(new Insets(0, 10, 0, 0));
        answerCheck3.setAlignment(Pos.CENTER);
        answerCheck3.setPadding(new Insets(0, 10, 0, 0));
        answerCheck4.setAlignment(Pos.CENTER);
        answerCheck4.setPadding(new Insets(0, 10, 0, 0));
        layout.setAlignment(Pos.CENTER);
        layoutAnswers.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25, 25, 25, 25));
        layoutAnswers.setPadding(new Insets(0, 0, 0, 0));
        layout.getChildren().addAll(message, titleText, nameText, choseLA, choseCategory);


        answerCheck1.getChildren().addAll(answerText1, correctCheckB1);
        HBox.setHgrow(answerText1, Priority.ALWAYS);
        answerCheck2.getChildren().addAll(answerText2, correctCheckB2);
        HBox.setHgrow(answerText2, Priority.ALWAYS);
        answerCheck3.getChildren().addAll(answerText3, correctCheckB3);
        HBox.setHgrow(answerText3, Priority.ALWAYS);
        answerCheck4.getChildren().addAll(answerText4, correctCheckB4);
        HBox.setHgrow(answerText4, Priority.ALWAYS);
        // switch between question and answer combinations
        switch (questionAnswerCombi){
            case "tt":

                questionText.setPromptText("Enter question text");
                answerText1.setPromptText("Enter answer text 1");
                answerText2.setPromptText("Enter answer text 2");
                answerText3.setPromptText("Enter answer text 3");
                answerText4.setPromptText("Enter answer text 4");

                layout.getChildren().addAll(questionText, layoutAnswers);
                layoutAnswers.getChildren().addAll(correctTextB, answerCheck1, answerCheck2, answerCheck3, answerCheck4);
                break;

            case"ft":

                questionText.setPromptText("Enter question text (optional)");
                questionFigure.setPromptText("Enter full path to question figure");
                answerText1.setPromptText("Enter answer text 1");
                answerText2.setPromptText("Enter answer text 2");
                answerText3.setPromptText("Enter answer text 3");
                answerText4.setPromptText("Enter answer text 4");

                layout.getChildren().addAll(questionText, questionFigure, layoutAnswers);
                layoutAnswers.getChildren().addAll(correctTextB, answerCheck1, answerCheck2, answerCheck3, answerCheck4);
                break;

            case"ff":

                questionText.setPromptText("Enter question text (optional)");
                questionFigure.setPromptText("Enter full path to question figure");
                answerText1.setPromptText("Enter full path to answer figure 1");
                answerText2.setPromptText("Enter full path to answer figure 2");
                answerText3.setPromptText("Enter full path to answer figure 3");

                layout.getChildren().addAll(questionText, questionFigure, layoutAnswers);
                layoutAnswers.getChildren().addAll(correctTextB, answerCheck1, answerCheck2, answerCheck3);
                break;

        }
        layout.getChildren().add(okButton);

        Scene scene = new Scene(layout, 400, 550);
        window.setScene(scene);
        window.show();
        // so courser does not jump into first field
        Platform.runLater(()->layout.requestFocus());
    }
}
