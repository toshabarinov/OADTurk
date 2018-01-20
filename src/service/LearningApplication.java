package service;

public class LearningApplication extends LearningInstance {

    private int approvedFlag;
    public LearningApplication(int id, String name, String description, int approvedFlag) {
        super(id, name, description);
        this.approvedFlag = approvedFlag;
    }

    public int getApprovedFlag() {
        return approvedFlag;
    }

    public void setApprovedFlag(int approvedFlag) {
        this.approvedFlag = approvedFlag;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
