/**
 * Auteurs: Ilyass El Ouazzani && Trinh Ngo
 */
package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;

/**
 * To oscillate obstacle with an amplitude of 50 px
 */
public class SinusObstacle extends Obstacle {

    public static final int SIN = 50;           // Amplitude of the sine wave of the obstacle
    private final double oldY;                  // To keep track of the position y during oscillation
    private double sy;                          // Speed of obstacle in position y

    /**
     * Constructor to instantiate and arbitrarily set obstacle in position y
     * @param ghost : to keep track of speed of ghost
     */
    public SinusObstacle(Ghost ghost) {
        super(ghost);

        /* if Math.random() gives 0 -> we add the radius to not be outside the top of the scene
        if Math.random() gives 1 -> we substract the radius to not be outside the bottom of the Pane
         */
        sy = Math.random() * (FlappyGhost.GAME_HEIGHT - (2 * getRadius() + SIN)) + getRadius();
        setY(sy);
        oldY = y;
    }

    /**
     * To update the position X of the obstacle at a given time
     * @param dt : deltaTime in double
     */
    public void update(double dt) {
        double nextX = x - dt * ghost.getSx();

        sy += Math.sin(Ghost.INIT_SPEED) * SIN * dt;
        double nextY =  y + dt * sy;

        // Control oscillation of an amplitude of 50 px
        if (( Math.abs(nextY - oldY) > SIN ) || (nextY + radius > FlappyGhost.GAME_HEIGHT) || (nextY - radius < 0)) {
            sy *= -1;
        } else {
            setY(nextY);
        }

        // If obstacle exited the window
        if (nextX + radius < 0) {
            out = true;
        }

        setX(nextX);
        setY(Math.min(getY(), FlappyGhost.GAME_HEIGHT - radius));       // Keep obstacle from out of border
        setY(Math.max(getY(), radius));
    }
}
