package ca.umontreal.iro.fg;

import ca.umontreal.iro.fg.obstacles.Obstacle;

import static javafx.scene.paint.Color.*;

/*
To detect the collision between the Ghost and the obstacles
 */
public interface CollisionHandler {

    private static boolean intersects(Ghost ghost, Obstacle obstacle) {
        double dx = ghost.getX() - obstacle.getX();
        double dy = ghost.getY() - obstacle.getY();
        double dSquare = dx * dx + dy * dy;

        return dSquare < (Ghost.RADIUS + Ghost.RADIUS) * (obstacle.getRadius() + obstacle.getRadius());
    }

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

