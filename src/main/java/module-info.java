module co.edu.uniquindio.subastasuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct;
    requires java.logging;
    requires java.desktop;
    requires com.rabbitmq.client;
    requires com.opencsv;

    exports co.edu.uniquindio.subastasuq.model;
    opens co.edu.uniquindio.subastasuq to javafx.fxml;
    exports co.edu.uniquindio.subastasuq;
    exports co.edu.uniquindio.subastasuq.viewController;
    opens co.edu.uniquindio.subastasuq.viewController to javafx.fxml;
}