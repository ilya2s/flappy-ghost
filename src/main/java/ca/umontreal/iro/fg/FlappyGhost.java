package ca.umontreal.iro.fg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FlappyGhost extends Application {

    public static final int WIDTH = 640;
    public static final int STAGE_HEIGHT = 440;
    public static final int GAME_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlappyGhost.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, STAGE_HEIGHT);
        scene.getRoot().requestFocus();
        stage.setTitle("Flappy Ghost");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(FlappyGhost.class.getResource("assets/ghost.png"))));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}