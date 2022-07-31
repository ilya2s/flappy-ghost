package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;

public class SinusObstacle extends Obstacle {

    public static final int SIN = 50;
    public double oldY = 0;
    public double sy;

    public SinusObstacle() {
        super();

        /* if Math.random() gives 0 -> we add the radius to not be outside the top of the scene
        if Math.random() gives 1 -> we substract the radius to not be outside the bottom of the Pane
         */
        sy = Math.random() * (FlappyGhost.GAME_HEIGHT - (2 * getRadius() + SIN)) + getRadius();
        setY(sy);
        //setY(Math.random() * (FlappyGhost.GAME_HEIGHT - (2 * getRadius() + SIN)) + getRadius());
        oldY = getY();
    }
    public void update(double dt) {
        double nextX = x - dt * Ghost.INIT_SPEED;

        sy += Math.sin(Ghost.INIT_SPEED) * SIN * dt;
        //System.out.println(sy);
        double nextY = sy * dt + getY() ;
        /*System.out.println("NEXT Y VALUE   " + nextY);
        System.out.println("OLD Y VALUE   " + oldY);
        System.out.println("TOTAL VALUE   " + (nextY - oldY));*/
        //double nextY = SIN * Math.sin( Math.toRadians(dt)) + oldY - SIN;
        //System.out.println(dt);

        if ( (Math.abs(nextY - oldY) > SIN * 2) || (nextY + radius > FlappyGhost.GAME_HEIGHT) || (nextY - radius < 0)) {
            sy *= -1;
            //System.out.println( " IN IF STATEMENT" + sy);

        } else { setY(nextY); }

        if (nextX + radius < 0) {
            out = true;
        }
        //System.out.println(oldY);
        setX(nextX);
        setY(Math.min(getY(), FlappyGhost.GAME_HEIGHT - radius));
        setY(Math.max(getY(), radius));
    }
}
