package service;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;

public class LearningUnit {
    // TODO JO put some members up in super class

    private int id;
    protected String name;
    private char question_type;
    private char answer_type;
    private int category_id;
    private String questionAnswerCombi;

    private int la_id;
    private boolean approvedFlag;
    public String correctAnswers;

    ResultSet resultSet;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    Image readImage(String databaseFigure){
        String path = "images";
        String questionFigurePath = path + File.separator + name + ".jpg";
        File theDir = new File(path);
        theDir.mkdir();
        Image returnImage = null;
        try {
            // to write image to filesystem
            FileOutputStream fos = new FileOutputStream(questionFigurePath);
            Blob blob = resultSet.getBlob(databaseFigure);
            if (blob != null){
                int len = (int) blob.length();
                byte[] buf = blob.getBytes(1, len);
                fos.write(buf, 0, len);
                fos.close();
                File imageFile = new File(questionFigurePath);
                returnImage = new Image(imageFile.toURI().toString());
                imageFile.delete();
            }
        }
        catch (Exception fe){
            fe.printStackTrace();
        }
        return returnImage;
    }

    public boolean isApprovedFlag() {
        return approvedFlag;
    }

    public void setApprovedFlag(boolean approvedFlag) {
        this.approvedFlag = approvedFlag;
    }

    public String getQuestionAnswerCombi() {
        return questionAnswerCombi;
    }

    public int getCategory_id() {

        return category_id;
    }
    public void setCategory_id(int category_id) {

        this.category_id = category_id;
    }
    public int getId() {

        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }
    public void setName(String name) {

        this.name = name;
    }
    public char getQuestion_type() {

        return question_type;
    }
    public void setQuestion_type(char question_type) {

        this.question_type = question_type;
        questionAnswerCombi = Character.toString(question_type) + Character.toString(answer_type);
    }
    public char getAnswer_type() {

        return answer_type;
    }
    public void setAnswer_type(char answer_type) {

        this.answer_type = answer_type;
        questionAnswerCombi = Character.toString(question_type) + Character.toString(answer_type);
    }

    public void setQuestionAnswerCombi(String questionAnswerCombi) {
        this.questionAnswerCombi = questionAnswerCombi;
    }

    public int getLa_id() {
        return la_id;
    }

    public void setLa_id(int la_id) {
        this.la_id = la_id;
    }
}

