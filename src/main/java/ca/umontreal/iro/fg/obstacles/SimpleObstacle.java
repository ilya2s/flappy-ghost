/**
 * Auteurs: Ilyass El Ouazzani && Trinh Ngo
 */
package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;

/**
 * To create fixed obstacles in arbitrarily position in y
 */
public class SimpleObstacle extends Obstacle {

    /**
     * Constructor to instantiate and set obstacle in position y
     * @param ghost
     */
    public SimpleObstacle(Ghost ghost) {
        super(ghost);

        /* if Math.random() gives 0 -> we add the radius to not be outside the top of the scene
        if Math.random() gives 1 -> we substract the radius to not be outside the bottom of the Pane
         */
        setY(Math.random() * (FlappyGhost.GAME_HEIGHT - 2 * getRadius()) + getRadius());
    }

    /**
     * To update the position X of the obstacle at a given time
     * @param dt : deltaTime in double
     */
    public void update(double dt) {
        double nextX = x - dt * ghost.getSx();

        // Check if obstacle exited the window
        if (nextX + radius < 0) {
            out = true;
        }

        setX(nextX);
    }
}
