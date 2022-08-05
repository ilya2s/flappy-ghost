package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;

/**
 * Add points to score if ghost passed the obstacle
 */
public interface ScoreHandler {

    /**
     * To notify if ghost has passed the obstacle
     * @param ghost : To retrieve position of ghost
     * @param obstacle : To retrieve position of obstacle
     * @return : if ghost has passed obstacle
     */
    private static boolean pass(Ghost ghost, Obstacle obstacle) {
        return (ghost.getX() - Ghost.RADIUS) > (obstacle.getX() + obstacle.getRadius());
    }

    /**
     * To update if ghost has passed obstacle or not
     * @param ghost
     * @param obstacle
     */
    static void handle(Ghost ghost, Obstacle obstacle) {
        obstacle.setPassed(pass(ghost, obstacle));
    }
}
