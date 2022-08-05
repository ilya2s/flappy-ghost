package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;

/**
 * Add points to score if ghost passed the obstacle
 */
public interface ScoreHandler {

    /**
     * To notify if the ghost has passed the obstacle
     * @param ghost !!!!!!!!!!!!!!!!!!!!!!!!!!1
     * @param obstacle !!!!!!!!!!!!!!!!!
     * @return !!!!!!!!!!!!!!!!!!!!!!!
     */
    private static boolean pass(Ghost ghost, Obstacle obstacle) {
        return (ghost.getX() - Ghost.RADIUS) > (obstacle.getX() + obstacle.getRadius());
    }

    static void handle(Ghost ghost, Obstacle obstacle) {
        obstacle.setPassed(pass(ghost, obstacle));
    }
}
