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
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class CreateLUBox extends Controller{

    public Stage parentStage;

    private String questionAnswerCombi;
    private TextField titleText;
    private TextField nameText;
    private TextField questionText;
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


    private void okButtonClick(ActionEvent event)
    {
        try {
            String titleString = titleText.getText();
            String nameString = nameText.getText();
            String questionString = questionText.getText();
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
        okButton.setOnAction(e->okButtonClick(e));

        Label message = new Label("Define your Learning Unit");
        Label correctText = new Label("Correct");

        choseLA = new ComboBox();
        choseLA.setPromptText("Chose LA");
        choseLA.setPrefWidth(150);
        choseLA.getItems().removeAll();
        for (LearningApplication la : systemData.getInstance().dataLA){
            choseLA.getItems().add(la.getName());
        }

        choseCategory = new ComboBox();
        choseCategory.setPromptText("Chose category");
        choseCategory.setPrefWidth(150);
        choseCategory.getItems().removeAll();
        for (LearningCategory cat : systemData.getInstance().dataLC){
            choseCategory.getItems().add(cat.getName());
        }


        titleText = new TextField();
        titleText.setPromptText("Enter LU title");

        nameText = new TextField();
        nameText.setPromptText("Enter LU reference name");

        // question and answer fields
        questionText = new TextField();
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

        // switch between question and answer combinations
        switch (questionAnswerCombi){
            case "tt":

                questionText.setPromptText("Enter question text");
                answerText1.setPromptText("Enter answer text 1");
                answerText2.setPromptText("Enter answer text 2");
                answerText3.setPromptText("Enter answer text 3");
                answerText4.setPromptText("Enter answer text 4");

                layout.getChildren().addAll(questionText, layoutAnswers);
                answerCheck1.getChildren().addAll(answerText1, correctCheckB1);
                answerCheck1.setHgrow(answerText1, Priority.ALWAYS);
                answerCheck2.getChildren().addAll(answerText2, correctCheckB2);
                answerCheck2.setHgrow(answerText2, Priority.ALWAYS);
                answerCheck3.getChildren().addAll(answerText3, correctCheckB3);
                answerCheck3.setHgrow(answerText3, Priority.ALWAYS);
                answerCheck4.getChildren().addAll(answerText4, correctCheckB4);
                answerCheck4.setHgrow(answerText4, Priority.ALWAYS);
                layoutAnswers.getChildren().addAll(correctTextB, answerCheck1, answerCheck2, answerCheck3, answerCheck4);
        }
        layout.getChildren().add(okButton);

        Scene scene = new Scene(layout, 400, 520);
        window.setScene(scene);
        window.show();
        // so courser does not jump into first field
        Platform.runLater(()->layout.requestFocus());
    }
}
