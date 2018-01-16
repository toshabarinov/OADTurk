package service;

public class LearningUnit {
    // TODO JO maybe it would be more elegant to have this class as interface; a lot of work though

    private int id;
    protected String name;
    private char question_type;
    private char answer_type;
    private int category_id;
    private String questionAnswerCombi;
    private boolean approvedFlag;
    public String correctAnswers;

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
}

