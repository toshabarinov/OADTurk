package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.DBConnector;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/home.fxml"));
        window.setTitle("OADTurk");
        window.setScene(new Scene(root, 800, 600));
        window.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
