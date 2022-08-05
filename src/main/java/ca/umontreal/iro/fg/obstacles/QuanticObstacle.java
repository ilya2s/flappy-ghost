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

    public QuanticObstacle(Ghost ghost) {
        super(ghost);

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
        double nextX = x - dt * ghost.getSx();

        if (nextX + radius + RANGE < 0) {
            out = true;
        }

        setX(nextX);
    }
}
