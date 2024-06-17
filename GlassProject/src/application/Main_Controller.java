package application;

import draw.Draw_Controller;
import help.Help_Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Glass;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Main_Controller implements Initializable, EventHandler<ActionEvent> {

    private final String FXMLPATH="Main.fxml";
    private final Stage window;
    @FXML private MenuItem miSave;
    @FXML private MenuItem miLoad;
    @FXML private MenuItem miDelete;
    @FXML private MenuItem miGenerate;
    @FXML private MenuItem miDraw;
    @FXML private MenuItem miShow;
    @FXML private MenuItem miAbout;
    @FXML private MenuItem miHelp;

    private Glass glass;
    private final String ext = "glass";


    public Main_Controller() {
        //<editor-fold defaultstate="collapsed" desc="GUI Initialize">
        window = new Stage();
        //</editor-fold>
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPATH));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600,400);
            window.setScene(scene);
            window.setOnCloseRequest(e->closeEvent());
            window.show();
        } catch (IOException ioe) {
            Message.message(Alert.AlertType.ERROR, "A GUI nem betölthető!", "");
        }
    }

    private void closeEvent() {
        System.out.println("Application closed.");
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == miSave)  {
            if (glass == null) {
               Message.message(Alert.AlertType.ERROR, "A szemüveg nem menthető!", "Nincs betöltött/generált szemüveg.");
               return;
            }
            saveGlass(glass);
        }
        else if (actionEvent.getSource() == miDelete) {
            Decision decision = Message.confirmation("Biztosan törölni szeretné az aktuális szemüveget?", "A törlés nem vonató vissza!", false);
            if (decision == Decision.YES) {
                glass = null;
                Message.message(Alert.AlertType.INFORMATION, "A szemüveg törölve lett.", "");
            }
        }
        else if (actionEvent.getSource() == miDraw) {
            Draw_Controller drawController = new Draw_Controller();
            if (glass != null) {
                drawController.setGlass(glass);
            }
            drawController.showAndWait();
            if (drawController.getGlass() != null) {
                saveGlass(glass);
            }
        }
        else if (actionEvent.getSource() == miGenerate) {
            glass = generateGlass();
        }
        else if (actionEvent.getSource() == miLoad) {
            if (glass != null) {
                Decision decision = Message.confirmation("Nem mentett adatok vannak!", "Szeretné lementeni?", true);
                if (decision == Decision.YES) {
                    saveGlass(glass);
                } else if (decision == Decision.NO) {
                    //Törölhető az adat
                    glass = null;
                } else {
                    return;
                }
            }
            loadGlass();
        }
        else if (actionEvent.getSource() == miShow) {
            if (glass == null) {
                Message.message(Alert.AlertType.ERROR,"Nincs megjeleníthető szemüveg!", "Generáljon, vagy töltsön be egy szemüveget!");
                return;
            }
            Message.message(Alert.AlertType.INFORMATION, glass.toString(), "");
        }
        else if (actionEvent.getSource() == miHelp) {
            Help_Controller helpController = new Help_Controller();
            helpController.setTopicID("main_window.txt");
            helpController.show();
        } else if (actionEvent.getSource() == miAbout) {
            Message.message(Alert.AlertType.INFORMATION, "Szemüveg-modellezó program"+System.lineSeparator()+
                    "Készítette:"+System.lineSeparator()+
                    "Mészáros Dániel, Programtervezető Informatikus BsC"+System.lineSeparator()+
                    "Tóth Bence, Programtervező Informatikus BsC", "");
        }
    }

    private void saveGlass(Glass glassParameter) {
        try {
            FileChooser valaszto = new FileChooser();
            valaszto.setTitle("Adja meg a menteni kívánt szemüveg nevét");
            valaszto.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Szemüveg fájlok", "*."+ext));
            File f = valaszto.showSaveDialog(window);
            if (f == null) {
                Message.message(Alert.AlertType.ERROR, "Nincs megadott fájlnév!", "Fájlnév megadása nélkül a mentés nem lehetséges!");
                return;
            }
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(glassParameter);
            out.close();
            fileOut.close();
            Message.message(Alert.AlertType.INFORMATION, "A szemüveg lementése sikeres!", "");
        } catch (IOException i) {
            Message.message(Alert.AlertType.ERROR, "A szemüveg lementése sikertelen!", i.getMessage());
        }
    }

    private Glass generateGlass() {
        Glass glass = new Glass();
        Random rand = new Random();
        glass.setDiameter(rand.nextInt(40) + 30);
        glass.setLensDistance(rand.nextInt(20) + 10);
        glass.setLensThickness(rand.nextInt(7) + 3);
        glass.setSideArmLength(rand.nextInt(80) + 120);
        glass.setSideArmThickness(rand.nextInt(7) + 3);
        glass.setRed(rand.nextInt(255));
        glass.setGreen(rand.nextInt(255));
        glass.setBlue(rand.nextInt(255));
        glass.calculate(glass.getDiameter(), glass.getLensDistance(), glass.getLensThickness(), glass.getSideArmThickness(), glass.getSideArmLength());
        Message.message(Alert.AlertType.INFORMATION, "A szemüveg generálása sikeres.", glass.toString());
        return glass;
    }

    private void loadGlass() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Válassza ki a betölteni kívánt szemüveget!");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Szemüveg fájlok", "*."+ext));
        File f = chooser.showOpenDialog(window);
        if (f == null) {
            Message.message(Alert.AlertType.ERROR, "Nincsen kiválasztott fájl!", "");
            return;
        }
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            glass = (Glass) in.readObject();
            in.close();
            fileIn.close();
            glass.calculate(glass.getDiameter(), glass.getLensDistance(), glass.getLensThickness(), glass.getSideArmThickness(), glass.getSideArmLength());
        } catch (IOException i) {
            Message.message(Alert.AlertType.ERROR, "Fájl beolvasása sikertelen!", "");
        } catch (ClassNotFoundException c) {
            Message.message(Alert.AlertType.ERROR, "A betölteni kívánt fájl nem egy szemüveg!", "Válasszon másik fájlt!");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        miSave.setOnAction(this);
        miDelete.setOnAction(this);
        miShow.setOnAction(this);
        miDraw.setOnAction(this);
        miGenerate.setOnAction(this);
        miLoad.setOnAction(this);
        miHelp.setOnAction(this);
        miAbout.setOnAction(this);
    }
}
