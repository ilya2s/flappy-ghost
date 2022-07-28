package ca.umontreal.iro.fg;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Background {

    public static final Image IMAGE = new Image(String.valueOf(FlappyGhost.class.getResource("assets/bg.png")));

    private final ImageView imageView1, imageView2;
    private final ParallelTransition parTransition;


    public Background() {
        imageView1 = new ImageView(IMAGE);
        imageView2 = new ImageView(IMAGE);

        Duration dt = Duration.seconds(FlappyGhost.WIDTH / Ghost.INIT_SPEED);
        TranslateTransition transition1 = new TranslateTransition(dt, imageView1);
        TranslateTransition transition2 = new TranslateTransition(dt, imageView2);

        transition1.setFromX(0);
        transition1.setToX(-FlappyGhost.WIDTH);
        transition1.setInterpolator(Interpolator.LINEAR);
        transition1.setCycleCount(Animation.INDEFINITE);

        transition2.setFromX(FlappyGhost.WIDTH);
        transition2.setToX(0);
        transition2.setInterpolator(Interpolator.LINEAR);
        transition2.setCycleCount(Animation.INDEFINITE);

        parTransition = new ParallelTransition(transition1, transition2);
    }

    public void move() {
        parTransition.play();
    }

    public ImageView getImageView1() {
        return imageView1;
    }

    public ImageView getImageView2() {
        return imageView2;
    }


}
