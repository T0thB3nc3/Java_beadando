package help;

import application.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Help_Controller implements Initializable, EventHandler<ActionEvent> {


    private String topicID;
    private final Stage window;
    private final String FXMLPATH="Help_View.fxml";
    @FXML private Button btClose;
    @FXML private Label lbTitle;
    @FXML private TextFlow textFlow;

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public Help_Controller() {
        window = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPATH));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,600, 600);
            window.setOnCloseRequest(e->closeEvent());
            window.setScene(scene);
        } catch (IOException ioe) {
            Message.message(Alert.AlertType.ERROR, "A GUI nem betölthető!",
                    "A segítséget megjelenítő ablak grafikus felülete nem betölthető!");
        }
    }

    public void show() {
        if (topicID.isEmpty()) {
            Message.message(Alert.AlertType.ERROR, "Nem került kiválasztásra, hogy milyen információt jelenítsen meg a program!", "");
            return;
        }
        BufferedReader reader;
        ArrayList<String> helpContent = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+ File.separator+"help"+File.separator+topicID));
            String line = reader.readLine();
            while (line != null) {
                helpContent.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Message.message(Alert.AlertType.ERROR, "Az alábbi help-fájl beolvasása sikertelen!", topicID);
            return;
        }
        lbTitle.setText("A megjelenített segítség: "+topicID);
        for (String string : helpContent) {
            textFlow.getChildren().add(new Text(string+System.lineSeparator()));
        }
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->closeEvent());
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btClose) {
            closeEvent();
        }
    }

    private void closeEvent() {
        window.close();
    }
}
