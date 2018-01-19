package service;

public class LearningUnit extends LearningInstance{

    private String question;
    private Integer question_type;
    private int question_id;
    private int answer_type;
    private int answer_id1;
    private int answer_id2;
    private int answer_id3;
    private int category_id;

    //could send "" as description parameter

    public LearningUnit(int id, String name, String description) {
        super(id, name, description);
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_type() {
        return answer_type;
    }

    public void setAnswer_type(int answer_type) {
        this.answer_type = answer_type;
    }

    public int getAnswer_id1() {
        return answer_id1;
    }

    public void setAnswer_id1(int answer_id1) {
        this.answer_id1 = answer_id1;
    }

    public int getAnswer_id2() {
        return answer_id2;
    }

    public void setAnswer_id2(int answer_id2) {
        this.answer_id2 = answer_id2;
    }

    public int getAnswer_id3() {
        return answer_id3;
    }

    public void setAnswer_id3(int answer_id3) {
        this.answer_id3 = answer_id3;
    }

    @Override
    public String toString() {
        return name;
    }
}
