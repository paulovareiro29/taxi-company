module project.desktop.main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens pt.ipvc to javafx.fxml;
    exports pt.ipvc;
}