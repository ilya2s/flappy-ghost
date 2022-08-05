/**
 *
 * Auteurs : Ilyass El Ouazzani && Trinh Ngo
 *
 */
package ca.umontreal.iro.fg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Model of Flappy Ghost
 */
public class FlappyGhost extends Application {

    public static final double WIDTH = 640;
    public static final double HEIGHT = 440;
    public static final double GAME_HEIGHT = 400;

    /**
     * To set up the 'View' window of Flappy Ghost
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
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

        // Close Timer running Task when window closes
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}