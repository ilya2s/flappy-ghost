package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class QuanticObstacle extends Obstacle {

    private static final double PERIOD = 0.2;
    private static final double RANGE = 30;
    public double oldY;

    public QuanticObstacle() {
        super();

        /* if Math.random() gives 0 -> we add the radius to not be outside the top of the scene
        if Math.random() gives 1 -> we substract the radius to not be outside the bottom of the Pane
         */
        //setY(Math.random() * (FlappyGhost.GAME_HEIGHT - (2 * getRadius() + RANGE)) + getRadius());
        oldY = Math.random() * (FlappyGhost.GAME_HEIGHT - (2 * getRadius() + RANGE)) + getRadius();
        setY(oldY);
    }

    public void update(double dt) {
        double nextX = x - dt * Ghost.INIT_SPEED;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(PERIOD), event -> {
            //setY(oldY + (Math.random() * 60));
            imageView.setImage(null);

            double rangeX = (Math.random() * 60);
            if ( rangeX < 30 ) {
                //imageView.setImage(null);
                setX( (nextX - rangeX) );
                System.out.println( " UNDER range 30    " + (nextX - rangeX));
                //imageView.setImage(image);
            } else if (rangeX >= 30) {
                setX( nextX + rangeX );
                System.out.println( "OVER range 30    " + nextX + rangeX);
            }
            imageView.setImage(image);
            /*
            double rangeY = (Math.random() * 60);
            if ( rangeY < 30 ) {
                //imageView.setImage(null);
                setY( (oldY - rangeY) );
                imageView.setImage(image);
            } else if (rangeY >= 30) {
                setY( oldY + rangeY );
            }*/
        }));

        if (nextX + radius + RANGE < 0) {
            out = true;
        }

        setX(nextX);
    }
}
