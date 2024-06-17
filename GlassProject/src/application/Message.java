package application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Optional;

public final class Message {

    public static void message (Alert.AlertType type, String upper, String lower) {
        Alert alert = new Alert(type);
        alert.setHeaderText(upper);
        alert.setContentText(lower);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public static Decision confirmation(String felso, String also, boolean threeButton) {
        ButtonType btIgen = new ButtonType("Igen", ButtonBar.ButtonData.YES);
        ButtonType btNem = new ButtonType("Nem", ButtonBar.ButtonData.NO);
        Alert alert;
        if (threeButton) {
            ButtonType btMegse = new ButtonType("Mégse", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert = new Alert(Alert.AlertType.CONFIRMATION, also, btIgen, btNem, btMegse);
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, also, btIgen, btNem);
        }
        alert.setTitle("Megerősítés kérése...");
        alert.setHeaderText(felso);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btIgen) {
            return Decision.YES;
        }
        if (result.get() == btNem) {
            return Decision.NO;
        }
        return Decision.CANCEL;
    }
}
