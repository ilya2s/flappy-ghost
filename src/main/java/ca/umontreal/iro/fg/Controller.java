package ca.umontreal.iro.fg;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Ghost[] ghosts;

    private AnimationTimer timer;

    private boolean pause;

    @FXML
    private Pane gamePane;
    @FXML
    private CheckBox debugBox;
    @FXML
    private Label scoreLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new AnimationTimer() {
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
                if (ghosts[0].getSy() > 300 || ghosts[0].getSy() < -300) {
                    System.out.println("xSpeed : " + ghosts[0].getSx() + " | ySpeed : " + ghosts[0].getSy());
                }

                lastTime = now;
            }
        };
        timer.start();
    }

    public void load() {
        pause = false;
        ghosts = new Ghost[1];

        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i] = new Ghost();
            ghosts[i].setX(Math.random() * FlappyGhost.WIDTH);
            ghosts[i].setY(Math.random() * FlappyGhost.GAME_HEIGHT);

            gamePane.getChildren().add(ghosts[i].getShape());
            gamePane.getChildren().add(ghosts[i].getImageView());

        }

        /*
        ghost1 = new Ghost();
        gamePane.getChildren().add(ghost1.getImageView());

        ghost2 = new Ghost();
        ghost2.setX(ghost1.getX() + Ghost.RADIUS * 4);
        ghost2.setY(ghost1.getY() + Ghost.RADIUS * 4);
        gamePane.getChildren().add(ghost2.getImageView());
         */
    }

    public void updatePane(double dt) {

        gamePane.getChildren().clear();

        for (int i = 0; i < ghosts.length; i++) {
            Ghost ghost = ghosts[i];
            ghost.update(dt);

            for (int j = i + 1; j < ghosts.length; j++) {
                Ghost other = ghosts[j];
                CollisionHandler.handle(ghost, other);
            }

            gamePane.getChildren().add(ghost.getShape());
            gamePane.getChildren().add(ghost.getImageView());
        }

        /*
        ghost1.update(dt);
        ghost2.update(dt);

        CollisionHandler.handle(ghost1, ghost2);

        gamePane.getChildren().clear();
        gamePane.getChildren().add(ghost1.getShape());
        gamePane.getChildren().add(ghost1.getImageView());
        gamePane.getChildren().add(ghost2.getShape());
        gamePane.getChildren().add(ghost2.getImageView());
         */
    }

    @FXML
    protected void pauseButtonCLicked() {
        gamePane.requestFocus();

        if (pause) {
            timer.start();
            pause = false;
        } else {
            timer.stop();
            pause = true;
        }
    }

    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();

        if (debugBox.isSelected()) {
            for (Ghost ghost : ghosts) {
                ghost.startDebug();
            }
        } else {
            for (Ghost ghost : ghosts) {
                ghost.stopDebug();
            }
        }

        /*
        if (debugBox.isSelected()) {
            ghost1.startDebug();
            ghost2.startDebug();
        } else {
            ghost1.stopDebug();
            ghost2.stopDebug();
        }
         */
    }

    @FXML
    protected void spaceBarPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            for (Ghost ghost : ghosts) {
                ghost.jump();
            }
        }
    }

}