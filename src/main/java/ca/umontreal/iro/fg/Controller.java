package ca.umontreal.iro.fg;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Background background;

    private Ghost ghost;
    private boolean pause;
    @FXML
    private Button pauseButton;
    @FXML
    private Pane gamePane;
    @FXML
    private CheckBox debugBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime;

            @Override
            public void start() {
                lastTime = System.nanoTime();
                super.start();
                load();
            }


            @Override
            public void handle(long now) {

                // If paused keep track of current time and skip calculating dt and updating pane
                if (pause) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                updatePane(deltaTime);

                lastTime = now;
            }
        };

        timer.start();
    }

    public void load() {
        pause = false;
        ghost = new Ghost();
        background = new Background();

        gamePane.getChildren().addAll(
                background.getImageView1(),
                background.getImageView2(),
                ghost.getShape(),
                ghost.getImageView()
        );

        background.move();
    }

    public void updatePane(double dt) {
        gamePane.getChildren().clear();
        ghost.update(dt);

        gamePane.getChildren().addAll(
                background.getImageView1(),
                background.getImageView2(),
                ghost.getShape(),
                ghost.getImageView()
        );
    }

    @FXML
    protected void pauseButtonClicked() {
        gamePane.requestFocus();

        if (pause) {
            pauseButton.setText("Pause");
            background.move();
            pause = false;
        } else {
            pauseButton.setText("Jouer");
            background.pause();
            pause = true;
        }
    }

    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();

        if (debugBox.isSelected()) {
            ghost.startDebug();
            updatePane(0);  // Force update the scene on action even when paused

        } else {
            ghost.stopDebug();
            updatePane(0);
        }
    }

    @FXML
    protected void spaceBarPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            ghost.jump();
        }
    }
}