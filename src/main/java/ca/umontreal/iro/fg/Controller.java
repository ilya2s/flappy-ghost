package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;
import ca.umontreal.iro.fg.obstacles.QuanticObstacle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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

/**
 * Controller of Flappy Ghost
 */
public class Controller implements Initializable {

    private int score = 0;                      // Initial score when game starts
    private AnimationTimer animationTimer;      // Create timer for each frame
    private Timeline timeline;                  // To create 3 seconds timeframe for obstacles
    private Background background;              // background Object to set up background of game
    private Ghost ghost;                        // ghost Object to set up ghost
    private List<Obstacle> obstacles;           // List of obstacles to add in game
    private List<Obstacle> passedObstacles;     // List of obstacles exited the window
    private int obstacleCount = 0;              // Keep track of every 2 obstacles to accelerate
    private boolean pause;                      // Keep track of when game is paused
    private boolean debugMode = false;          // Start game without debug mode ON

    @FXML
    private Button pauseButton;                 // Pause button
    @FXML
    private Pane gamePane;                      // Layout Pane of game
    @FXML
    private CheckBox debugBox;                  // CheckBox for debug mode

    @FXML
    private Label scoreLabel;                   // Label for score

    /**
     * To initialize the attributs and load the game window
     * @param url Location
     * @param resourceBundle Resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animationTimer = new AnimationTimer() {
            private long lastTime;

            /**
             * Load the game
             */
            @Override
            public void start() {
                lastTime = System.nanoTime();
                super.start();
                load();
            }

            /**
             * Keep track of time when paused to resume from current position
             * @param now
             *            The timestamp of the current frame given in nanoseconds. This
             *            value will be the same for all {@code AnimationTimers} called
             *            during one frame.
             */
            @Override
            public void handle(long now) {

                // If paused, keep track of current time, skip calculating dt and updating pane
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

    /**
     * To instantiate the attributs
     */
    public void load() {
        setScore(0);
        pause = false;
        ghost = new Ghost();
        background = new Background();
        obstacles = new ArrayList<>();
        passedObstacles = new ArrayList<>();

        // Create obstacle every 3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            Obstacle obstacle = Obstacle.makeObstacle(ghost);

            // Make obstacle appear in debug mode
            if (debugMode) obstacle.startDebug();
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

        // Move background
        background.move();
    }

    /**
     * To update the pane at a given time
     * @param dt : Time in double
     */
    public void updatePane(double dt) {

        // Clear the scene
        gamePane.getChildren().clear();

        // Update the ghost position
        ghost.update(dt);
        for (Obstacle o : obstacles) {

            // Update the obstacle position
            o.update(dt);

            // Update the score if ghost passed the obstacle
            ScoreHandler.handle(ghost, o);
            if (o.isPassed() && !passedObstacles.contains(o)) {
                setScore(score + 5);
                passedObstacles.add(o);
                obstacleCount++;

                // Accelerate ghost for every 2 obstacles
                if (obstacleCount != 0 && obstacleCount % 2 == 0) {
                    ghost.accelerate();
                    background.update();
                }
            }
        }

        // Handle collision between ghost and obstacles
        for (Obstacle o : obstacles) {
            if(CollisionHandler.handle(ghost, o)) {

                // Stop all animations
                animationTimer.stop();
                background.stop();
                timeline.stop();

                // Clear all obstacles and Nodes from scene
                obstacles.clear();
                passedObstacles.clear();
                gamePane.getChildren().clear();

                // Restart animationTimer -> calls load() method
                animationTimer.start();
                return;
            }
        }

        // If obstacle is out of screen, remove from list
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

    /**
     * Score setter
     * @param score : integer
     */
    private void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    /**
     * To pause the background and pane, if pause button clicked
     */
    @FXML
    protected void pauseButtonClicked() {
        gamePane.requestFocus();

        if (pause) {
            pauseButton.setText("Pause");
            background.move();
            timeline.play();
            for (Obstacle o : obstacles) {
                if (o instanceof QuanticObstacle) {
                    ((QuanticObstacle) o).getQuanticTimeline().play();
                }
            }
            pause = false;

        } else {
            pauseButton.setText("Resume");
            background.pause();
            timeline.pause();
            for (Obstacle o : obstacles) {
                if (o instanceof QuanticObstacle) {
                    ((QuanticObstacle) o).getQuanticTimeline().pause();
                }
            }
            pause = true;
        }
    }

    /**
     * Events if debug Box is checked
     */
    @FXML
    protected void debugBoxChecked() {
        gamePane.requestFocus();

        // Turn ghost and obstacles into circles if debug mode is checked
        if (debugBox.isSelected()) {
            debugMode = true;
            ghost.startDebug();
            for (Obstacle o : obstacles) {
                o.startDebug();
            }
            
            // Force to update the scene on action even when paused
            updatePane(0);

        } else {
            debugMode = false;
            ghost.stopDebug();
            for (Obstacle o : obstacles) {
                o.stopDebug();
            }
            updatePane(0);
        }
    }

    /**
     * To make the ghost jump when the spacebar is pressed
     * @param event : KeyEvent
     */
    @FXML
    protected void spaceBarPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            ghost.jump();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            Platform.exit();
        }
    }
}