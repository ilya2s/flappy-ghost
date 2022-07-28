package ca.umontreal.iro.fg;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Background background1;
    private Background background2;

    private TranslateTransition trans1;
    private TranslateTransition trans2;

    private ParallelTransition parTrans;

    private Ghost ghost;

    private AnimationTimer timer;

    private boolean pause;
    @FXML
    private Button pauseButton;
    @FXML
    private Pane gamePane;
    @FXML
    private CheckBox debugBox;
    @FXML
    private Label scoreLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new AnimationTimer()  {
            private long lastTime;
            private long animationStart;

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
        parTrans.play();
    }

    public void load() {
        pause = false;
        ghost = new Ghost();
        background1 = new Background();
        background2 = new Background();

        trans1 = new TranslateTransition(Duration.seconds(FlappyGhost.WIDTH / ghost.getSx()), background1.getImageView());
        trans1.setFromX(0);
        trans1.setToX(-640);
        trans1.setInterpolator(Interpolator.LINEAR);
        trans1.setCycleCount(Animation.INDEFINITE);

        trans2 = new TranslateTransition(Duration.seconds(FlappyGhost.WIDTH / ghost.getSx()), background2.getImageView());
        trans2.setFromX(640);
        trans2.setToX(0);
        trans2.setInterpolator(Interpolator.LINEAR);
        trans2.setCycleCount(Animation.INDEFINITE);

        parTrans = new ParallelTransition(trans1, trans2);

        gamePane.getChildren().addAll(background1.getImageView(), background2.getImageView(), ghost.getShape(), ghost.getImageView());
    }

    public void updatePane(double dt) {
        gamePane.getChildren().clear();
        ghost.update(dt);
        gamePane.getChildren().addAll(background1.getImageView(), background2.getImageView(), ghost.getShape(), ghost.getImageView());
    }

    @FXML
    protected void pauseButtonClicked() {
        gamePane.requestFocus();

        if (pause) {
            pauseButton.setText("Pause");
            timer.start();
            parTrans.play();
            pause = false;
        } else {
            pauseButton.setText("Jouer");
            timer.stop();
            parTrans.stop();
            pause = true;
        }
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

    @FXML
    protected void spaceBarPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            ghost.jump();
        }
    }
}