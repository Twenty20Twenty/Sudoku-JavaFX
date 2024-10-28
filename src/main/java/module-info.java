module ru.nstu.sudokugame {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nstu.sudokugame to javafx.fxml;
    exports ru.nstu.sudokugame;

    opens ru.nstu.sudokugame.Controller;
    exports ru.nstu.sudokugame.Controller;

    opens ru.nstu.sudokugame.Model;
}