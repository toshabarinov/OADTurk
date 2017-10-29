package service;

public class LearningApplication {
    int la_id;
    String la_name;
    String la_description;

    public LearningApplication(int la_id, String la_name, String la_description) {
        this.la_id = la_id;
        this.la_name = la_name;
        this.la_description = la_description;
    }

    public LearningApplication(int la_id, String la_name) {
        this.la_id = la_id;
        this.la_name = la_name;
    }

    public int getLa_id() {
        return la_id;
    }

    public void setLa_id(int la_id) {
        this.la_id = la_id;
    }

    public String getLa_name() {
        return la_name;
    }



    public void setLa_name(String la_name) {
        this.la_name = la_name;
    }

    public String getLa_description() {
        return la_description;
    }

    public void setLa_description(String la_description) {
        this.la_description = la_description;
    }

    @Override
    public String toString() {
        return this.la_name;
    }
}
