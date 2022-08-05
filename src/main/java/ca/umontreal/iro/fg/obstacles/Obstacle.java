package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.Debugable;
import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Obstacle implements Debugable {
    public static final int MIN_RADIUS = 10;
    public static final int MAX_RADIUS = 45;
    public static final int OBSTACLES_COUNT = 26;

    protected final Circle shape;
    protected final Image image;
    protected ImageView imageView;
    protected final double radius;
    protected Ghost ghost;
    protected double x, y;
    protected boolean passed;
    protected boolean out;
    protected boolean debug;

    public Obstacle(Ghost ghost) {
        this.ghost = ghost;
        debug = false;
        radius = Math.random() * (MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;
         x = FlappyGhost.WIDTH + radius;
        passed = false;
        shape = new Circle(x, y, radius, null);

        int obstacleNumber = (int) (Math.random() * OBSTACLES_COUNT);
        image = new Image(String.valueOf(FlappyGhost.class.getResource("assets/obstacles/"
                + obstacleNumber + ".png")));
        imageView = new ImageView(image);
        resetImageView();
    }

    private void resetImageView() {
        imageView.setX(x - radius);
        imageView.setY(y - radius);

        // Make the ImageView the same size as the shape
        imageView.setFitWidth(radius * 2);
        imageView.setFitHeight(radius * 2);
    }

    @Override
    public void startDebug() {
        imageView = new ImageView();
        shape.setFill(Color.YELLOW);
        debug = true;
    }


    @Override
    public void stopDebug() {
        imageView = new ImageView(image);
        resetImageView();
        shape.setFill(null);
        debug = false;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    public abstract void update(double dt);

    public static Obstacle makeObstacle(Ghost ghost) {
        int num = (int) (Math.random() * 3) + 1;
        switch (num) {
            case (1) -> {
                return new SinusObstacle(ghost);
            }
            case (2) -> {
                return new QuanticObstacle(ghost);
            }
            default -> {
                return new SimpleObstacle(ghost);
            }
        }
    }

    public Circle getShape() {
        return shape;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getRadius() {
        return radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isPassed() {
        return passed;
    }

    public boolean isOut() {
        return out;
    }

    public void setX(double x) {
        this.x = x;
        shape.setCenterX(x);
        imageView.setX(x - radius);
    }

    public void setY(double y) {
        this.y = y;
        shape.setCenterY(y);
        imageView.setY(y - radius);
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
