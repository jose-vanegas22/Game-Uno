module com.example.gameuno {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gameuno to javafx.fxml;
    exports com.example.gameuno;

    opens com.example.gameuno.Controllers to javafx.fxml;
}