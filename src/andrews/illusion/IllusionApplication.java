package andrews.illusion;/**
 * @author STALKER_2010
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class IllusionApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IllusionGame.launch(new IllusionGame());
        IllusionGame.NAME = "Illusion";
    }
}
