module cz.mendelu.xmackiv.projekt_2048.projekt_2048 {
    requires javafx.controls;
    requires org.controlsfx.controls;

    opens cz.mendelu.xmackiv.projekt_2048.projekt_2048 to javafx.fxml;
    exports cz.mendelu.xmackiv.projekt_2048.projekt_2048;
}