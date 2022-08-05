package ca.umontreal.iro.fg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Controller of Ghost's movements and image
 */
public class Ghost implements Debugable {
    public static final int RADIUS = 30;
    public static final double INIT_SPEED = 120;
    public static final double AY = 500;    // Gravity
    public static final double JUMP_SPEED = -300;   // Negative value to go up
    public static final double MAX_Y_SPEED = 300;
    public static final Image IMAGE = new Image(String.valueOf(Ghost.class.getResource("assets/ghost.png")));
    private ImageView imageView;
    private final Circle shape;
    private final double x;
    private double y;    // position
    private double sx, sy;  // speed
    private double ay;  // variable gravity

    private boolean debug;

    /**
     * To instantiate Ghost
     */
    public Ghost() {
        debug = false;
        x = FlappyGhost.WIDTH / 2;
        y = FlappyGhost.GAME_HEIGHT / 2;
        this.shape = new Circle(x, y, RADIUS, null);

        imageView = new ImageView(IMAGE);

        resetImageView();

        sy = 0;
        sx = INIT_SPEED;

        ay = AY;
    }

    /**
     * Set the ghost image to the center
     */
    private void resetImageView() {
        imageView.setX(x - RADIUS);
        imageView.setY(y - RADIUS);

        // Make the ImageView the same size as the shape
        imageView.setFitWidth(RADIUS * 2);
        imageView.setFitHeight(RADIUS * 2);
    }

    /**
     * Turn the ghost into black if debug mode is on
     */
    @Override
    public void startDebug() {
        imageView = new ImageView();         // Empty the image, otherwise it will appear on the black circle
        shape.setFill(Color.BLACK);
        debug = true;
    }

    /**
     * Return the ghost to normal if debug mode is off
     */
    @Override
    public void stopDebug() {
        imageView = new ImageView(IMAGE);    // Fill the imageView to the ghost image
        imageView.setX(x - RADIUS);
        shape.setFill(null);                 // Remove the black circle
        debug = false;
    }

    /**
     * Getter for boolean of Debug mode
     * @return boolean si on est en mode debug
     */
    @Override
    public boolean isDebug() {
        return debug;
    }

    /**
     * To update the Ghost position at a given time
     * @param dt : Time in double
     */
    public void update(double dt) {
        sy += dt * ay;

        // Set max Y speed
        if (sy > MAX_Y_SPEED) sy = MAX_Y_SPEED;
        if (sy < -MAX_Y_SPEED) sy = -MAX_Y_SPEED;

        double nextY = y + dt * sy;

        // If the ghost reaches the border of window, it moves in the opposite direction for the bounce back effect
        if (nextY + RADIUS > FlappyGhost.GAME_HEIGHT || nextY - RADIUS < 0) {
            sy *= -1;
        } else {
            setY(nextY);
        }

        setY(Math.min(y, FlappyGhost.GAME_HEIGHT - RADIUS));
        setY(Math.max(y, RADIUS));
    }


    /**
     * Reset the speed to jump back up
     */
    public void accelerate() {
        sx += 15;
        ay += 15;
    }

    public void jump() {
        sy = JUMP_SPEED;
    }

    /**
     * Getter pour la shape
     * @return Circle le cercle qui represente le ghost
     */
    public Circle getShape() {
        return shape;
    }

    /**
     * Getter pour ImageView
     * @return ImageView l'image qui represente le ghost
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Getter pour la position x
     * @return double la postion x
     */
    public double getX() {
        return x;
    }

    /**
     * Getter pour la position y
     * @return double la position y
     */
    public double getY() {
        return y;
    }

    /**
     * Getter pour la vitesse x
     * @return sx
     */
    public double getSx() {
        return sx;
    }

    /**
     * Setter pour la position y
     * @param y la nouvelle position
     */
    public void setY(double y) {
        this.y = y;
        shape.setCenterY(y);
        imageView.setY(y - RADIUS);
    }
}
