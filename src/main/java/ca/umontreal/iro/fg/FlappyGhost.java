package ca.umontreal.iro.fg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class FlappyGhost extends Application {

    public static final double WIDTH = 640;
    public static final double HEIGHT = 440;
    public static final double GAME_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlappyGhost.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getRoot().requestFocus();
        stage.setTitle("Flappy Ghost");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(FlappyGhost.class.getResource("assets/ghost.png"))));
        stage.show();

        // Close Timer running Task when widow closes
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}