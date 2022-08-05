package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private int score = 0;
    private AnimationTimer animationTimer;
    private Timeline timeline;
    private Background background;
    private Ghost ghost;
    private List<Obstacle> obstacles;
    private List<Obstacle> passedObstacles;
    private int obstacleCount = 0;
    private boolean pause;
    private boolean debugMode = false;

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
        animationTimer = new AnimationTimer() {
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

        animationTimer.start();
    }

    public void load() {
        setScore(0);
        pause = false;
        ghost = new Ghost();
        background = new Background();
        obstacles = new ArrayList<>();
        passedObstacles = new ArrayList<>();

        // Create now obstacle every 3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            Obstacle obstacle = Obstacle.makeObstacle();
            if (debugMode) obstacle.startDebug();   // make obstacle appear in debug mode
            obstacles.add(obstacle);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Update Nodes in Scene
        gamePane.getChildren().addAll(
                background.getImageView1(),
                background.getImageView2(),
                ghost.getShape(),
                ghost.getImageView()
        );
        for (Obstacle o : obstacles) {
            gamePane.getChildren().addAll(o.getShape(), o.getImageView());
        }

        background.move();  // move background
    }

    public void updatePane(double dt) {
        gamePane.getChildren().clear();     // clear the scene

        ghost.update(dt);
        for (Obstacle o : obstacles) {
            o.update(dt);   // update the obstacle position

            ScoreHandler.handle(ghost, o);
            if (o.isPassed() && !passedObstacles.contains(o)) {
                setScore(++score);
                passedObstacles.add(o);
                obstacleCount++;
                if (obstacleCount != 0 && obstacleCount % 2 == 0) {
                    ghost.setSx(ghost.getSx() + 15);
                    background.update();
                    System.out.println(ghost.getSx());
                }
            }
        }

        // Handle collision between ghost and obstacles
        for (Obstacle o : obstacles) {
            if(CollisionHandler.handle(ghost, o)) {
                // stop all animations
                animationTimer.stop();
                background.stop();
                timeline.stop();

                // clear all obstacles and Nodes from scene
                obstacles.clear();
                passedObstacles.clear();
                gamePane.getChildren().clear();

                // restart animationTimer -> calls load() method
                animationTimer.start();
                return;
            }
        }

        // if obstacle is out of screen remove from list
        obstacles.removeIf(Obstacle::isOut);
        passedObstacles.removeIf(Obstacle::isOut);

        // Redraw the updated nodes on the scene
        gamePane.getChildren().addAll(
                background.getImageView1(),
                background.getImageView2(),
                ghost.getShape(),
                ghost.getImageView()
        );
        for (Obstacle o : obstacles) {
            gamePane.getChildren().addAll(o.getShape(), o.getImageView());
        }
    }

    private void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    @FXML
    protected void pauseButtonClicked() {
        gamePane.requestFocus();

        if (pause) {
            pauseButton.setText("Pause");
            background.move();
            timeline.play();
            pause = false;
        } else {
            pauseButton.setText("Jouer");
            background.pause();
            timeline.pause();
            pause = true;
        }
    }

    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();

        if (debugBox.isSelected()) {
            debugMode = true;
            ghost.startDebug();
            for (Obstacle o : obstacles) {
                o.startDebug();
            }
            updatePane(0);  // Force update the scene on action even when paused

        } else {
            debugMode = false;
            ghost.stopDebug();
            for (Obstacle o : obstacles) {
                o.stopDebug();
            }
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