package vaccinationAppointments.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the application
 * @author Hugo Martínez
 * version 1.0
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VACCINATION APPOINTMENT MANAGER");
        primaryStage.setScene(new Scene(root, 500, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
