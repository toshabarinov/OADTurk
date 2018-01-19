package service;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;

public class LuFigureText extends LearningUnit{

//    private int id;
//    private String text;
    private ResultSet resultSet;

    public String titleText;
    public String questionText;
    public Image questionFigure;       //< path to figure
    public String answerText1;
    public String answerText2;
    public String answerText3;
    public String answerText4;

    private void readImage(){
        String path = "images";
        String questionFigurePath = path + File.separator + name + ".jpg";
        File theDir = new File(path);
        boolean resDir = theDir.mkdir();
        try {
            // to write image to filesystem
            FileOutputStream fos = new FileOutputStream(questionFigurePath);
            Blob blob = resultSet.getBlob("question_figure");
            int len = (int) blob.length();
            byte[] buf = blob.getBytes(1, len);
            fos.write(buf, 0, len);
            fos.close();
            File imageFile = new File(questionFigurePath);
            questionFigure = new Image(imageFile.toURI().toString());
        }
        catch (Exception fe){
            fe.printStackTrace();
        }
    }

    public LuFigureText(ResultSet resultSet){
        try {
            this.resultSet = resultSet;
            setId(resultSet.getInt("id"));
            name = resultSet.getString("refName");
            titleText = resultSet.getString("title");
            questionText = resultSet.getString("question_text");
            readImage();
            answerText1 = resultSet.getString("answer1");
            answerText2 = resultSet.getString("answer2");
            answerText3 = resultSet.getString("answer3");
            answerText4 = resultSet.getString("answer4");
            correctAnswers = resultSet.getString("correctAnswers");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    
}
