module ru.nstu.sudokugame {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nstu.sudokugame to javafx.fxml;
    exports ru.nstu.sudokugame;

    opens ru.nstu.sudokugame.ControllerFXML;
    exports ru.nstu.sudokugame.ControllerFXML;

    opens ru.nstu.sudokugame.Model;
}