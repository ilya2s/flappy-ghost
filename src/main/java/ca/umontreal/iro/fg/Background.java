package ca.umontreal.iro.fg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background {

    public static final Image IMAGE = new Image(String.valueOf(FlappyGhost.class.getResource("assets/bg.png")));

    private final ImageView imageView;

    public Background() {
        imageView = new ImageView(IMAGE);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
