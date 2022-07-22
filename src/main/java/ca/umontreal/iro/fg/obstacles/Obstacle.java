package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.FlappyGhost;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Obstacle implements Avoidable {
    public static final int MIN_RADIUS = 10;
    public static final int MAX_RADIUS = 45;
    public static final int OBSTACLES_COUNT = 27;

    private final Circle shape;
    private final Image image;
    private final ImageView imageView;
    private final double radius;
    private double x;
    private double y;
    private boolean passed;

    public Obstacle() {
        radius = Math.random() * (MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;
         // x = FlappyGhost.WIDTH + radius;
         x = ((double) FlappyGhost.WIDTH / 2);
        passed = false;
        shape = new Circle(x, y, radius, Color.YELLOW);

        int obstacleNumber = (int) (Math.random() * OBSTACLES_COUNT);
        image = new Image(String.valueOf(FlappyGhost.class.getResource("assets/obstacles/"
                + obstacleNumber + ".png")));
        imageView = new ImageView(image);
        imageView.setX(x - radius);
        imageView.setY(y - radius);

        // Make the ImageView the same size as the shape
        imageView.setFitWidth(radius * 2);
        imageView.setFitHeight(radius * 2);
    }

    public Circle getShape() {
        return shape;
    }

    public Image getImage() {
        return image;
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
