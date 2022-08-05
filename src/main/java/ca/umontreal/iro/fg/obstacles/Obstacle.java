/**
 * Auteurs: Ilyass El Ouazzani && Trinh Ngo
 */

package ca.umontreal.iro.fg.obstacles;

import ca.umontreal.iro.fg.Debugable;
import ca.umontreal.iro.fg.FlappyGhost;
import ca.umontreal.iro.fg.Ghost;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Abstract class to create and control obstacles
 */
public abstract class Obstacle implements Debugable {

    public static final int MIN_RADIUS = 10;        // Minimum radius of obstacle
    public static final int MAX_RADIUS = 45;        // Maximum radius of obstacle
    public static final int OBSTACLES_COUNT = 26;   // Total number of obstacles
    protected final Circle shape;                   // To set obstacles as circles in debug mode
    protected final Image image;                    // Load image among 27 images of obstacles in png
    protected ImageView imageView;                  // To display image of obstacle
    protected final double radius;                  // Final radius of obstacle
    protected Ghost ghost;                          // To keep track of speed of ghost
    protected double x, y;                          // Position of obstacle in x and y-axis
    protected boolean passed;                       // To indicate if obstacle passed ghost
    protected boolean out;                          // To indicate if obstacle is outside the window
    protected boolean debug;                        // To indicate if debug mode is ON/OFF

    /**
     * Constructor to instantiate attributs
     * @param ghost : keep track ghost
     */
    public Obstacle(Ghost ghost) {
        this.ghost = ghost;
        debug = false;
        radius = Math.random() * (MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS; // Randomize radius between 10 and 45
        x = FlappyGhost.WIDTH + radius;
        passed = false;
        shape = new Circle(x, y, radius, null);

        int obstacleNumber = (int) (Math.random() * OBSTACLES_COUNT);   // Retrieving obstacle image by random
        image = new Image(String.valueOf(FlappyGhost.class.getResource("assets/obstacles/"
                + obstacleNumber + ".png")));
        imageView = new ImageView(image);
        resetImageView();
    }

    /**
     * Setting image and position of obstacle
     */
    private void resetImageView() {
        // Centering image of obstacle
        imageView.setX(x - radius);
        imageView.setY(y - radius);

        // Make the ImageView the same size as the shape
        imageView.setFitWidth(radius * 2);
        imageView.setFitHeight(radius * 2);
    }

    /**
     * Set obstacles as yellow circles when debug mode is ON
     */
    @Override
    public void startDebug() {
        imageView = new ImageView();
        shape.setFill(Color.YELLOW);
        debug = true;
    }

    /**
     * Set image back on obstacles when debug mode is OFF
     */
    @Override
    public void stopDebug() {
        imageView = new ImageView(image);
        resetImageView();
        shape.setFill(null);
        debug = false;
    }

    /**
     * Getter for boolean if debug mode is ON/OFF
     * @return : boolean
     */
    @Override
    public boolean isDebug() {
        return debug;
    }

    public abstract void update(double dt);

    /**
     * Create obstacles between Simple, Sinus & Quantic obstacles
     * @param ghost : Ghost object, to track speed of ghost
     * @return : new Obstacle Object
     */
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

    /**
     * Getter for Circle object
     * @return : Circle object
     */
    public Circle getShape() {
        return shape;
    }

    /**
     * Getter for ImageView of the obstacle
     * @return : ImageView of obstacle
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Getter for obstacle radius
     * @return : double
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter for position x
     * @return : double
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for position y
     * @return : double
     */
    public double getY() {
        return y;
    }

    /**
     * Getter for boolean if ghost passed the obstacle
     * @return : boolean
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Getter for boolean if ghost passed obstacle
     * @return : boolean
     */
    public boolean isOut() {
        return out;
    }

    /**
     * Setter to set obstacle in position x
     * @param x : double
     */
    public void setX(double x) {
        this.x = x;
        shape.setCenterX(x);
        imageView.setX(x - radius);
    }

    /**
     * Setter to set obstacle in position y
     * @param y : double
     */
    public void setY(double y) {
        this.y = y;
        shape.setCenterY(y);
        imageView.setY(y - radius);
    }

    /**
     * Setter to keep track if ghost passed obstacle
     * @param passed : boolean
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
