package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;

public class SimpleObstacle extends Obstacle {

    public SimpleObstacle() {
        super();

        /* if Math.random() gives 0 -> we add the radius to not be outside the top of the scene
        if Math.random() gives 1 -> we substract the radius to not be outside the bottom of the Pane
         */
        setY(Math.random() * (FlappyGhost.GAME_HEIGHT - 2 * getRadius()) + getRadius());
    }

    public void move() {

    }
}
