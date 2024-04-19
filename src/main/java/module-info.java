module cc103.group3.philspot {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens cc103.group3.philspot to javafx.fxml;
    exports cc103.group3.philspot;
}