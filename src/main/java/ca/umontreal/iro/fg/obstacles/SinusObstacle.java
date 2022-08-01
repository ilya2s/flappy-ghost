package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;

public class SinusObstacle extends Obstacle {

    public static final int SIN = 50;
    public double sy;

    public SinusObstacle(Ghost ghost) {
        super(ghost);

        /* if Math.random() gives 0 -> we add the radius to not be outside the top of the scene
        if Math.random() gives 1 -> we substract the radius to not be outside the bottom of the Pane
         */
        sy = Math.random() * (FlappyGhost.GAME_HEIGHT - (2 * getRadius() + SIN)) + getRadius();
        setY(sy);
    }
    public void update(double dt) {
        double oldY = y;

        double nextX = x - dt * ghost.getSx();
        sy += Math.sin(Ghost.INIT_SPEED) * SIN * dt;
        double nextY =  y + dt * sy;

        if ((Math.abs(nextY - oldY) > SIN * 2) || (nextY + radius > FlappyGhost.GAME_HEIGHT) || (nextY - radius < 0)) {
            sy *= -1;
        } else {
            setY(nextY);
        }

        if (nextX + radius < 0) {
            out = true;
        }


        setX(nextX);
        setY(Math.min(getY(), FlappyGhost.GAME_HEIGHT - radius));
        setY(Math.max(getY(), radius));
    }
}
