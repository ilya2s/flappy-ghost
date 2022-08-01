package ca.umontreal.iro.fg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ghost implements Debugable {
    public static final int RADIUS = 30;
    public static final double INIT_SPEED = 120;
    public static final double AY = 500;    // Gravity
    public static final double JUMP_SPEED = -300;   // Negative value to go up
    public static final double MAX_Y_SPEED = 300;
    public static final Image IMAGE = new Image(String.valueOf(Ghost.class.getResource("assets/ghost.png")));

    private final Circle shape;
    private ImageView imageView;
    private final double x;
    private double y;    // position
    private double sx, sy;  // speed
    private boolean debug;

    public Ghost() {
        debug = false;
        x = FlappyGhost.WIDTH / 2;
        y = FlappyGhost.GAME_HEIGHT / 2;
        this.shape = new Circle(x, y, RADIUS, null);

        imageView = new ImageView(IMAGE);

        resetImageView();

        sy = 0;
        sx = INIT_SPEED;
    }

    private void resetImageView() {
        imageView.setX(x - RADIUS);
        imageView.setY(y - RADIUS);

        // Make the ImageView the same size as the shape
        imageView.setFitWidth(RADIUS * 2);
        imageView.setFitHeight(RADIUS * 2);
    }

    @Override
    public void startDebug() {
        imageView = new ImageView();
        shape.setFill(Color.BLACK);
        debug = true;
    }

    @Override
    public void stopDebug() {
        imageView = new ImageView(IMAGE);
        imageView.setX(x - RADIUS);
        shape.setFill(null);
        debug = false;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    public void update(double dt) {
        sy += dt * AY;

        // Set max Y speed
        if (sy > MAX_Y_SPEED) sy = MAX_Y_SPEED;
        if (sy < -MAX_Y_SPEED) sy = -MAX_Y_SPEED;

        double nextY = y + dt * sy;

        if (nextY + RADIUS > FlappyGhost.GAME_HEIGHT || nextY - RADIUS < 0) {
            sy *= -1;
        } else {
            setY(nextY);
        }

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

    public void setSx(double sx) {
        this.sx = sx;
    }

    public void setY(double y) {
        this.y = y;
        shape.setCenterY(y);
        imageView.setY(y - RADIUS);
    }
}
