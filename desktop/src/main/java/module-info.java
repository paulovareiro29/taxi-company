module taxi.company.desktop.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires taxi.company.database.main;

    opens pt.ipvc to javafx.fxml;
    exports pt.ipvc;
}