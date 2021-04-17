import UI.ChessInterface;
import javafx.application.Application;
import javafx.stage.Stage;

public class Run extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ChessInterface(primaryStage);
    }
}
