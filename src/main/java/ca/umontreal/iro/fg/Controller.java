package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;
import ca.umontreal.iro.fg.obstacles.SimpleObstacle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private AnimationTimer timer;

    @FXML
    private AnchorPane gamePane;
    @FXML
    private Circle ghost;
    @FXML
    private Button pause;
    @FXML
    private CheckBox debug;
    @FXML
    private Label score;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ghost.setCenterX(ghost.getCenterX() + 0.07);
            }
        };
        timer.start();
    }

    @FXML
    protected void pauseButtonCLicked() {
        gamePane.requestFocus();

        Obstacle obstacle = new SimpleObstacle();
        gamePane.getChildren().add(obstacle.getImageView());

        timer.start();
    }

    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();
    }

}