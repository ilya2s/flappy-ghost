package ca.umontreal.iro.fg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ghost implements Debugable {

    public static final int RADIUS = 30;
    public static final double INIT_SPEED = 120;
    public static final double AY = 500;
    public static final double JUMP_SPEED = 300;
    public static final double MAX_Y_SPEED = 300;
    public static final double AX = 15;
    public static final Image IMAGE = new Image(String.valueOf(Ghost.class.getResource("assets/ghost.png")));

    private final Circle shape;
    private ImageView imageView;
    private double x;
    private double y;
    private double xSpeed;
    private double ySpeed;

    public Ghost() {
        x = RADIUS;
        y = RADIUS;
        this.shape = new Circle(x, y, RADIUS, null);

        imageView = new ImageView(IMAGE);
        imageView.setX(x - RADIUS);
        imageView.setY(y - RADIUS);

        // Make the ImageView the same size as the shape
        imageView.setFitWidth(RADIUS * 2);
        imageView.setFitHeight(RADIUS * 2);

        xSpeed = INIT_SPEED;
        ySpeed = 0;
    }

    public Circle getShape() {
        return shape;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setX(double x) {
        this.x = x;
        shape.setCenterX(x);
        imageView.setX(x - RADIUS);
    }

    public void setY(double y) {
        this.y = y;
        shape.setCenterY(y);
        imageView.setY(y - RADIUS);
    }

    public void setxSpeed(double speed) {
        this.xSpeed = speed;
    }

    public void setySpeed(double speed) {
        this.ySpeed = speed;
    }

    public void startDebug() {
        imageView = new ImageView();
        shape.setFill(Color.BLACK);
    }

    public void stopDebug() {
        imageView = new ImageView(IMAGE);
        shape.setFill(null);
    }
}
