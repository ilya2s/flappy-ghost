package ca.umontreal.iro.fg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Ghost {

    public static final int RADIUS = 30;
    public static final int INIT_SPEED = 120;
    public static final int JUMP_HEIGHT = 300;
    public static final int MAX_Y_SPEED = 300;
    public static final Image IMAGE = new Image(String.valueOf(Ghost.class.getResource("assets/ghost.png")));

    private Circle ghost;
    private ImageView imageView;

    public Ghost(Circle ghost) {
        this.ghost = ghost;
        imageView = new ImageView(IMAGE);
    }
}
