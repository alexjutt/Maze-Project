import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

public class MazeApp extends Application {
    @Override
    public void start(Stage stage) {
        Label message = new Label("Maze app is running");
        GridPane grid = new GridPane();
        grid.setHgap(10); 
        grid.setVgap(10); 
        grid.add(new Label("Item 1"), 0, 0);
        grid.add(new Label("Item 2"), 1, 0);
        grid.add(new Label("Item 3"), 0, 1);
        grid.add(new Label("Item 4"), 1, 1);

        
        for (int i = 0; i < finderMap.rows; i++){
            for (int j = 0; j < finderMap.cols; j++){
                //tile.setText("Hi");
            }
        }
        Scene scene = new Scene(grid, 400, 300);

        stage.setTitle("Maze Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
