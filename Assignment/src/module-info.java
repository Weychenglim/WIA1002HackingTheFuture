module Assignment {
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;
    requires org.jgrapht.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gluonhq.charm.glisten;
    requires com.gluonhq.attach.util;
    requires javafx.swing;
    requires org.json;

    exports Assignment;
    opens Assignment to javafx.fxml;
}