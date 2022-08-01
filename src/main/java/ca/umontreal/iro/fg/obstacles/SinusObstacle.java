package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;

public class SinusObstacle extends Obstacle {

    public static final int SIN = 50;

    public SinusObstacle(Ghost ghost) {
        super(ghost);

        /* if Math.random() gives 0 -> we add the radius to not be outside the top of the scene
        if Math.random() gives 1 -> we substract the radius to not be outside the bottom of the Pane
         */
        setY(Math.random() * (FlappyGhost.GAME_HEIGHT - 2 * getRadius()) + getRadius());
    }
    public void update(double dt) {
        double nextX = x - dt * ghost.getSx();

        if (nextX + radius < 0) {
            out = true;
        }

        setX(nextX);
    }
}
