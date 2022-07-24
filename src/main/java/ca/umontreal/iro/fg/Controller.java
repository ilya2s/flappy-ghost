package ca.umontreal.iro.fg;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Ghost ghost;
    @FXML
    private Pane gamePane;
    @FXML
    private Button pauseButton;
    @FXML
    private CheckBox debugBox;
    @FXML
    private Label scoreLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // deltaTime * speedX = deltaX
        //ghost.setY(ghost.getY() + deltaTime * ghost.getSpeed()); // deltaTime * speedY = deltaY
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime;

            @Override
            public void start() {
                lastTime = System.nanoTime();
                super.start();
            }

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) * 1e-9;

                ghost.setxSpeed(ghost.getxSpeed() + deltaTime * 0);
                ghost.setySpeed(ghost.getySpeed() + deltaTime * Ghost.AY);

                double nextX = ghost.getX() + deltaTime * ghost.getxSpeed(); // deltaTime * speedX = deltaX
                double nextY = ghost.getY() + deltaTime * ghost.getySpeed(); // deltaTime * speedY = deltaY

                if (nextX + Ghost.RADIUS > FlappyGhost.WIDTH
                        || nextX - Ghost.RADIUS < 0) {
                    ghost.setxSpeed(ghost.getxSpeed() * -0.9);
                } else {
                    ghost.setX(nextX);
                }

                if (nextY + Ghost.RADIUS > FlappyGhost.GAME_HEIGHT
                        || nextY - Ghost.RADIUS < 0) {
                    ghost.setySpeed(ghost.getySpeed() * -0.9);
                } else {
                    ghost.setY(nextY);
                }

                updatePane();

                lastTime = now;
            }
        };

        load();
        timer.start();
    }

    public void load() {
        ghost = new Ghost();
        gamePane.getChildren().add(ghost.getImageView());
    }

    public void updatePane() {
        gamePane.getChildren().clear();
        gamePane.getChildren().add(ghost.getShape());
        gamePane.getChildren().add(ghost.getImageView());
    }

    @FXML
    protected void pauseButtonCLicked() {
        gamePane.requestFocus();
    }

    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();

        if (debugBox.isSelected()) {
            ghost.startDebug();
        } else {
            ghost.stopDebug();
        }
    }

}