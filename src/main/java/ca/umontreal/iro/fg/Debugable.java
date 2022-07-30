package ca.umontreal.iro.fg;

import javafx.scene.image.ImageView;

public interface Debugable {
    boolean isDebug();
    void startDebug();
    void stopDebug();
}
