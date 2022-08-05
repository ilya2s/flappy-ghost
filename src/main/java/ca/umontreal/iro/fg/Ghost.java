package ca.umontreal.iro.fg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Controller of Ghost's movements and image
 */
public class Ghost implements Debugable {
    public static final int RADIUS = 30;                // Radius of ghost
    public static final double INIT_SPEED = 120;        // Initial speed of ghost in x-axis
    public static final double AY = 500;                // Gravity of ghost in y-axis
    public static final double JUMP_SPEED = -300;       // Negative value to go up
    public static final double MAX_Y_SPEED = 300;       // Keep maximum speed
    public static final Image IMAGE = new Image(        // Load image of ghost in png
            String.valueOf(Ghost.class.getResource("assets/ghost.png")));
    private ImageView imageView;                        // To display image of ghost
    private final Circle shape;                         // To set ghost as circle in debug mode
    private final double x;                             // No change in ghost position in x-axis
    private double y;                                   // Ghost position in y-axis
    private double sx, sy;                              // Ghost speed in x and y-axis
    private double ay;                                  // Accumulation of gravity in y-axis
    private boolean debug;                              // Keep track of debug mode

    /**
     * To instantiate Ghost in the center with initial speed when game begins
     */
    public Ghost() {
        debug = false;
        x = FlappyGhost.WIDTH / 2;                      // Centering ghost
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
     * Turn the ghost into black circle if debug mode is ON
     */
    @Override
    public void startDebug() {
        // Empty the image, otherwise it will appear on the black circle
        imageView = new ImageView();
        shape.setFill(Color.BLACK);
        debug = true;
    }

    /**
     * Return the ghost to normal if debug mode is OFF
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

        // Prevent from exiting the border in y-axis
        setY(Math.min(y, FlappyGhost.GAME_HEIGHT - RADIUS));
        setY(Math.max(y, RADIUS));
    }

    /**
     * To accelerate ghost for every 2 obstacles
     */
    public void accelerate() {
        sx += 15;
        ay += 15;
    }

    /**
     * Reset the speed to jump back up
     */
    public void jump() {
        sy = JUMP_SPEED;
    }

    /**
     * Getter for object Circle of Ghost
     * @return : Circle representing Ghost
     */
    public Circle getShape() {
        return shape;
    }

    /**
     * Getter for ImageView of Ghost
     * @return : ImageView representing Ghost
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Getter for position x
     * @return : position x in double
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for position y
     * @return : position y in double
     */
    public double getY() {
        return y;
    }

    /**
     * Getter for speed in x
     * @return : speed in x in double
     */
    public double getSx() {
        return sx;
    }

    /**
     * Setter for position y
     * @param y : position y in double
     */
    public void setY(double y) {
        this.y = y;
        shape.setCenterY(y);
        imageView.setY(y - RADIUS);
    }
}
