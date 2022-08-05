/**
 * Auteurs: Ilyass El Ouazzani && Trinh Ngo
 */
package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * To create obstacles that teleport from a range of -30 to 30 px every 0.2 seconds
 */
public class QuanticObstacle extends Obstacle {

    private static final double PERIOD = 0.2;       // Obstacle teleporting every 0.2 seconds
    private static final double RANGE = 30;         // Obstacle teleporting between -30 and 30 px

    private final Timeline quanticTimeline;         // To time teleportation of obstacle

    /**
     * Teleporting obstacles arbitrarily between -30 and 30 px every 0.2 seconds
     * @param ghost : to keep track of the ghost's speed
     */
    public QuanticObstacle(Ghost ghost) {
        super(ghost);

        // Setting the position y of the obstacle
        setY( Math.random() * ( FlappyGhost.GAME_HEIGHT - 2 * getRadius() ) + getRadius() );

        // Teleporting in different positions every 0.2 seconds
        quanticTimeline = new Timeline( new KeyFrame( Duration.seconds(PERIOD), event -> {

            // Ranging arbitrarily between -30 and 30 px in position x
            double rangeX = ( Math.random() * RANGE * 2 );
            rangeX = rangeX < RANGE ? rangeX : RANGE - rangeX;

            // Ranging arbitrarily between -30 and 30 px in position y
            double rangeY = ( Math.random() * RANGE * 2 );
            rangeY = rangeY < RANGE ? rangeY : RANGE - rangeY;

            setX( x - rangeX );
            setY( y - rangeY );

            // To avoid obstacle from exiting the window
            setY(Math.min( y, FlappyGhost.GAME_HEIGHT - radius ));
            setY(Math.max( y, radius ));

        }));
        quanticTimeline.setCycleCount( Animation.INDEFINITE );
        quanticTimeline.play();
    }

    /**
     * To update the position X of the obstacle at a given time
     * @param dt : deltaTime in double
     */
    public void update(double dt) {
        double nextX = x - dt * ghost.getSx();

        // Check if obstacle exited the window
        if ( nextX + radius + RANGE < 0 ) {
            out = true;
        }

        setX( nextX );
    }

    /**
     * Getter for the Timeline to play or pause it
     * @return : Timeline
     */
    public Timeline getQuanticTimeline() {
        return quanticTimeline;
    }
}
