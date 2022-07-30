package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;
import javafx.scene.control.Label;

public interface ScoreHandler {

    private static boolean pass(Ghost ghost, Obstacle obstacle) {
        return (ghost.getX() - Ghost.RADIUS) > (obstacle.getX() + obstacle.getRadius());
    }

    static void handle(Ghost ghost, Obstacle obstacle) {
        obstacle.setPassed(pass(ghost, obstacle));
    }
}
