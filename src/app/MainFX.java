package app;

import fx.util.ScreenManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ScreenManager.setStage(stage);

        Scene scene = new Scene(
                FXMLLoader.load(
                        getClass().getResource("/view/tela-inicial.fxml")
                )
        );

        stage.setTitle("Clínica Médica");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
