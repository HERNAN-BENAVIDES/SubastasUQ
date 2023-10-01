module co.edu.uniquindio.subastasuq {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.subastasuq to javafx.fxml;
    exports co.edu.uniquindio.subastasuq;
}