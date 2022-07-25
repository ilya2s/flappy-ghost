package ca.umontreal.iro.fg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ghost implements Debugable {

    public static final int RADIUS = 30;
    public static final double INIT_SPEED = 120;
    public static final double AX = 0;
    public static final double AY = 500;    // Gravity
    public static final double JUMP_SPEED = -300;   // Negative value to go up
    public static final double MAX_Y_SPEED = 300;
    public static final Image IMAGE = new Image(String.valueOf(Ghost.class.getResource("assets/ghost.png")));

    private final Circle shape;
    private ImageView imageView;
    private double x, y;    // position
    private double sx, sy;  // speed

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

        sx = INIT_SPEED;
        sy = 0;
    }

    public void startDebug() {
        imageView = new ImageView();
        shape.setFill(Color.BLACK);
    }

    public void stopDebug() {
        imageView = new ImageView(IMAGE);
        shape.setFill(null);
    }

    public void update(double dt) {
        sx += dt * AX;
        sy += dt * AY;

        if (sy > MAX_Y_SPEED || sy < -MAX_Y_SPEED) {
            sy = sy < 0 ? -MAX_Y_SPEED : MAX_Y_SPEED;
        }

        double nextX = x + dt * sx;
        double nextY = y + dt * sy;

        if (nextX + RADIUS > FlappyGhost.WIDTH || nextX - RADIUS < 0) {
            sx *= -1;
        } else {
            setX(nextX);
        }

        if (nextY + RADIUS > FlappyGhost.GAME_HEIGHT || nextY - RADIUS < 0) {
            sy *= -1;
        } else {
            setY(nextY);
        }

        // Force x and y to be inside borders of game
        setX(Math.min(x, FlappyGhost.WIDTH - RADIUS));
        setX(Math.max(x, RADIUS));

        setY(Math.min(y, FlappyGhost.GAME_HEIGHT - RADIUS));
        setY(Math.max(y, RADIUS));
    }

    public void jump() {
        sy = JUMP_SPEED;
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

    public double getSx() {
        return sx;
    }

    public double getSy() {
        return sy;
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

    public void setSx(double speed) {
        this.sx = speed;
    }

    public void setSy(double speed) {
        this.sy = speed;
    }

}
