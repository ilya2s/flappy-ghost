package ca.umontreal.iro.fg;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Background {

    public static final Image IMAGE = new Image(String.valueOf(FlappyGhost.class.getResource("assets/bg.png")));

    private final ImageView imageView1, imageView2;
    private double x;


    public Background() {
        x = FlappyGhost.WIDTH;
        imageView1 = new ImageView(IMAGE);
        imageView2 = new ImageView(IMAGE);
        imageView2.setX(x);
    }

    public void move(double dx) {
        x -= dx;

        if (x <= 0) {
            x = FlappyGhost.WIDTH;
        }

        imageView1.setX(x - FlappyGhost.WIDTH);
        imageView2.setX(x);
    }

    public ImageView getImageView1() {
        return imageView1;
    }

    public ImageView getImageView2() {
        return imageView2;
    }


}
