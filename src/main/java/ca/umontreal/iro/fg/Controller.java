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
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Background background;

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