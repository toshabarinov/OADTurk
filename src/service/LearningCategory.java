package service;

public class LearningCategory {
    int lc_id;
    String lc_name;
    String lc_description;
    int la_id;

    public LearningCategory(int lc_id, String lc_name, String lc_description, int la_id) {
        this.lc_id = lc_id;
        this.lc_name = lc_name;
        this.lc_description = lc_description;
        this.la_id = la_id;
    }

    public LearningCategory(int lc_id, String lc_name, int la_id) {
        this.lc_id = lc_id;
        this.lc_name = lc_name;
        this.la_id = la_id;
    }

    public int getLc_id() {

        return lc_id;
    }

    public void setLc_id(int lc_id) {
        this.lc_id = lc_id;
    }

    public String getLc_name() {
        return lc_name;
    }

    public void setLc_name(String lc_name) {
        this.lc_name = lc_name;
    }

    public String getLc_description() {
        return lc_description;
    }

    public void setLc_description(String lc_description) {
        this.lc_description = lc_description;
    }

    public int getLa_id() {
        return la_id;
    }

    public void setLa_id(int la_id) {
        this.la_id = la_id;
    }

    @Override
    public String toString() {
        return lc_name;
    }
}
