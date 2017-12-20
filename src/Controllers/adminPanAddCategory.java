package Controllers;


import javafx.fxml.FXML;

public class adminPanAddCategory extends  adminPanelController {

    @FXML
    private void initialize() {
        addCategoryButton.setDisable(true);
        setNoEffects();
    }

    public void confirmButtonPressed() {

    }
}
