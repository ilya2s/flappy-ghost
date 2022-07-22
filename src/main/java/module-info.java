module ca.umontreal.iro.fg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens ca.umontreal.iro.fg to javafx.fxml;
    exports ca.umontreal.iro.fg;
    exports ca.umontreal.iro.fg.obstacles;
    opens ca.umontreal.iro.fg.obstacles to javafx.fxml;
}