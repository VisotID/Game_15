module com.inessa.game_in_15 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.inessa.game_in_15 to javafx.fxml;
    exports com.inessa.game_in_15;
}