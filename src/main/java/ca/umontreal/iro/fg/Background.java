/**
 * Auteurs: Ilyass El Ouazzani && Trinh Ngo
 */
package ca.umontreal.iro.fg;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Class to set up the background
 */
public class Background {

    public static final double ACC = 15.0;                        // Acceleration for every 2 obstacles ghost passes
    public static final Image IMAGE = new Image(                  // Load image of the background
            String.valueOf(FlappyGhost.class.getResource("assets/bg.png")));
    private final ImageView imageView1, imageView2;               // Instantiate two images for loop effect
    private final ParallelTransition parTransition;               // Class to make the loop
    private final TranslateTransition transition1, transition2;   // Create a move/translation animation

    /**
     * Constructor to instantiate and set the translation animation
     */
    public Background() {
        imageView1 = new ImageView(IMAGE);
        imageView2 = new ImageView(IMAGE);

        // dx / sx = dt
        Duration dt = Duration.seconds(FlappyGhost.WIDTH / Ghost.INIT_SPEED);
        transition1 = new TranslateTransition(dt, imageView1);
        transition2 = new TranslateTransition(dt, imageView2);

        // Animation heading to the left
        transition1.setFromX(0);
        transition1.setToX(-FlappyGhost.WIDTH);
        transition1.setInterpolator(Interpolator.LINEAR);
        transition1.setCycleCount(Animation.INDEFINITE);

        // To create loop effect
        transition2.setFromX(FlappyGhost.WIDTH);
        transition2.setToX(0);
        transition2.setInterpolator(Interpolator.LINEAR);
        transition2.setCycleCount(Animation.INDEFINITE);

        parTransition = new ParallelTransition(transition1, transition2);
    }

    /**
     * To accelerate the background every 2 obstacles ghost passes
     */
    public void update() {
        parTransition.setRate(parTransition.getRate() + ACC / Ghost.INIT_SPEED);
    }

    /**
     * To start the animation
     */
    public void move() {
        parTransition.play();
    }

    /**
     * To pause the animation
     */
    public void pause() {
        parTransition.pause();
    }

    /**
     * To stop transition completely
     */
    public void stop() {
        parTransition.stop();
    }

    /**
     * Getter of 1st ImageView of background
     * @return : ImageView
     */
    public ImageView getImageView1() {
        return imageView1;
    }

    /**
     * Getter of 2nd ImageView of background
     * @return : ImageView
     */
    public ImageView getImageView2() {
        return imageView2;
    }
}
