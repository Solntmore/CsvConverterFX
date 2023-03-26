module com.example.csvconverterfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.apache.poi.ooxml;
    requires lombok;
    requires java.desktop;

    opens com.example.csvconverterfx to javafx.fxml;
    exports com.example.csvconverterfx;
}