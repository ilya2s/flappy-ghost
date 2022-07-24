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

    private Ghost ghost1;
    private Ghost ghost2;

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
                double deltaTime = (now - lastTime) * 1e-9;

                updatePane(deltaTime);

                lastTime = now;
            }
        };
        timer.start();
    }

    public void load() {
        ghost1 = new Ghost();
        gamePane.getChildren().add(ghost1.getImageView());

        ghost2 = new Ghost();
        ghost2.setX(ghost1.getX() + Ghost.RADIUS * 4);
        gamePane.getChildren().add(ghost2.getImageView());
    }

    public void updatePane(double dt) {

        ghost1.update(dt);
        ghost2.update(dt);

        CollisionHandler.handle(ghost1, ghost2);

        gamePane.getChildren().clear();
        gamePane.getChildren().add(ghost1.getShape());
        gamePane.getChildren().add(ghost1.getImageView());
        gamePane.getChildren().add(ghost2.getShape());
        gamePane.getChildren().add(ghost2.getImageView());
    }

    @FXML
    protected void pauseButtonCLicked() {
        gamePane.requestFocus();
    }

    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();

        if (debugBox.isSelected()) {
            ghost1.startDebug();
            ghost2.startDebug();
        } else {
            ghost1.stopDebug();
            ghost2.stopDebug();
        }
    }

}