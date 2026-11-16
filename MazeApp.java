import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MazeApp extends Application {
    @Override
    public void start(Stage stage) {
        Label message = new Label("Maze app is running");
        Scene scene = new Scene(message, 400, 300);

        stage.setTitle("Maze Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
