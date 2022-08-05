/**
 * Auteurs: Ilyass El Ouazzani && Trinh Ngo
 */
package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;

import static javafx.scene.paint.Color.*;

/**
 * Class to detect collision between ghost and obstacles & handle the event
 */
public interface CollisionHandler {

    /**
     * To detect when collision happens
     * @param ghost : to access position of ghost
     * @param obstacle : to access position and radius of obstacles
     * @return : boolean if collision happened
     */
    private static boolean intersects(Ghost ghost, Obstacle obstacle) {
        double dx = ghost.getX() - obstacle.getX();
        double dy = ghost.getY() - obstacle.getY();
        double dSquare = dx * dx + dy * dy;

        return dSquare < (Ghost.RADIUS + Ghost.RADIUS) * (obstacle.getRadius() + obstacle.getRadius());
    }

    /**
     * To set color of circles (obstacles in debug mode) and detect collision
     * @param ghost
     * @param obstacle
     * @return
     */
    static boolean handle(Ghost ghost, Obstacle obstacle) {
        boolean intersects = CollisionHandler.intersects(ghost, obstacle);
        boolean debug = obstacle.isDebug();

        // To detect collision even during debug mode
        if (intersects && debug) {
            obstacle.getShape().setFill(RED);
            return false;

        } else if (intersects) {
            return true;

        } else if (debug) {
            obstacle.getShape().setFill(YELLOW);
            return false;

        } else {
            return false;
        }
    }
}

