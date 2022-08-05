package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class QuanticObstacle extends Obstacle {

    private static final double PERIOD = 0.2;
    private static final double RANGE = 30;

    public QuanticObstacle() {
        super();

        setY(Math.random() * (FlappyGhost.GAME_HEIGHT - 2 * getRadius()) + getRadius());

        Timeline quanticTimeline = new Timeline(new KeyFrame(Duration.seconds(PERIOD), event -> {
            double rangeX = (Math.random() * 60);
            rangeX = rangeX < 30 ? rangeX : 30 - rangeX;

            double rangeY = (Math.random() * 60);
            rangeY = rangeY < 30 ? rangeY : 30 - rangeY;

            setX(x - rangeX);
            setY(y - rangeY);

            setY(Math.min(y, FlappyGhost.GAME_HEIGHT - radius));
            setY(Math.max(y, radius));
        }));
        quanticTimeline.setCycleCount(Animation.INDEFINITE);
        quanticTimeline.play();
    }

    public void update(double dt) {
        double nextX = x - dt * Ghost.INIT_SPEED;

<<<<<<< HEAD
        double rangeX = (Math.random() * 60);
        if ( rangeX < 30 ) {
            newX = rangeX;
            //imageView.setImage(null);
            //setX( (nextX - rangeX) );
            //System.out.println( " UNDER range 30    " + (nextX - rangeX));
            //imageView.setImage(image);
        } else if (rangeX >= 30) {
            //setX( nextX + rangeX );
            newX = rangeX;
            //System.out.println( "OVER range 30    " + nextX + rangeX);
        }
        //imageView.setImage(image);

        double rangeY = (Math.random() * 60);
        if ( rangeY < 30 ) {
            nextY = rangeY;
            //imageView.setImage(null);
            setY( (oldY - rangeY) );
            //imageView.setImage(image);
        } else if (rangeY >= 30) {
            nextY = rangeY;
            setY( oldY + rangeY );
        }


=======
>>>>>>> c2b17268b4742c5e61d9e6de03424d7d2459548a
        if (nextX + radius + RANGE < 0) {
            out = true;
        }

        setX(nextX);
    }
}
