package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Controller {

    private AnimationTimer timer;

    @FXML
    private AnchorPane gamePane;
    @FXML
    private Circle ghostCirle;
    @FXML
    private Button pause;
    @FXML
    private CheckBox debug;
    @FXML
    private Label score;

    @FXML
    protected void pauseButtonCLicked() {
        gamePane.requestFocus();

        Obstacle obstacle = new Obstacle();
        obstacle.setY(FlappyGhost.GAME_HEIGHT / 2);
        gamePane.getChildren().add(obstacle.getImageView());
    }

    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();
    }

}