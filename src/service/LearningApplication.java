package service;

public class LearningApplication extends LearningInstance {

    public LearningApplication(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
