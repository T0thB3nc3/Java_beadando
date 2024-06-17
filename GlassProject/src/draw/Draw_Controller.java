package draw;

import application.Decision;
import application.Message;
import help.Help_Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Glass;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Draw_Controller implements Initializable, EventHandler<ActionEvent> {

    private final String FXMLPATH="Draw_View.fxml";
    private final Stage window;
    @FXML private Slider slDiameter;
    @FXML private Slider slLensDistance;
    @FXML private Slider slSideArmLength;
    @FXML private Slider slSideArmThickness;
    @FXML private Slider slLensThickness;
    @FXML private Slider slRed;
    @FXML private Slider slGreen;
    @FXML private Slider slBlue;
    @FXML private Button drawButton;
    @FXML private Button btInfo;
    @FXML private Pane pnSide;
    @FXML private Pane pnFront;
    @FXML private Pane pnOver;
    private Glass glass;
    private Color ownColor;

    public Glass getGlass() {
        return glass;
    }

    public void setGlass(Glass glass) {
        this.glass = glass;
        slDiameter.adjustValue(glass.getDiameter());
        slLensDistance.adjustValue(glass.getLensDistance());
        slSideArmLength.adjustValue(glass.getSideArmLength());
        slSideArmThickness.adjustValue(glass.getSideArmThickness());
        slLensThickness.adjustValue(glass.getLensThickness());
        slRed.adjustValue(glass.getRed());
        slGreen.adjustValue(glass.getGreen());
        slBlue.adjustValue(glass.getBlue());
    }

    public Draw_Controller () {
        window = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPATH));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 800,600);
            window.setScene(scene);
            window.setOnCloseRequest(e->closeEvent());
        } catch (IOException ioe) {
            Message.message(Alert.AlertType.ERROR, "A GUI nem betölthető!",
                    "A rajzoláshoz szükséges ablak grafikus felülete nem betölthető!");
        }
    }

    private void closeEvent() {
        Decision decision = Message.confirmation("Nem mentett adatok vannak!", "Szeretné lementeni?", false);
        if (decision == Decision.YES) {
            if (glass == null) {
                glass = new Glass();
            }
            glass.setDiameter((int)slDiameter.getValue());
            glass.setLensDistance((int)slLensDistance.getValue());
            glass.setSideArmLength((int)slSideArmLength.getValue());
            glass.setSideArmThickness((int)slSideArmThickness.getValue());
            glass.setLensThickness((int)slLensThickness.getValue());
            glass.setRed((int)slRed.getValue());
            glass.setGreen((int)slGreen.getValue());
            glass.setBlue((int)slBlue.getValue());
            window.close();
        } else if (decision == Decision.NO) {
            //Törölhető az adat
            glass = null;
            window.close();
        }
    }

    public void showAndWait() {
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == drawButton) {
            ownColor = Color.rgb((int) slRed.getValue(), (int)slGreen.getValue(), (int)slBlue.getValue());
            drawFront();
            drawSide();
            drawOver();
        }
        if (actionEvent.getSource() == btInfo) {
            Help_Controller helpController = new Help_Controller();
            helpController.setTopicID("draw_window.txt");
            helpController.show();
        }
    }

    private void drawOver() {
        pnOver.getChildren().clear();
        Rectangle oLeftSideArm = new Rectangle();
        oLeftSideArm.setX(150.0f);
        oLeftSideArm.setY(210.0f-slSideArmLength.getValue());
        oLeftSideArm.setWidth(slSideArmThickness.getValue());
        oLeftSideArm.setHeight(slSideArmLength.getValue());
        oLeftSideArm.setFill(ownColor);
        Rectangle oLeftLens = new Rectangle();
        oLeftLens.setX(150.0f);
        oLeftLens.setY(210.f);
        oLeftLens.setWidth(slDiameter.getValue());
        oLeftLens.setHeight(slLensThickness.getValue());
        oLeftLens.setFill(ownColor);
        Rectangle oLensDistance = new Rectangle();
        oLensDistance.setX(150+slDiameter.getValue());
        oLensDistance.setY(210+slLensThickness.getValue()/6);
        oLensDistance.setWidth(slLensDistance.getValue());
        oLensDistance.setHeight(slLensThickness.getValue()/3*2);
        oLensDistance.setFill(ownColor);
        Rectangle oRightLens = new Rectangle();
        oRightLens.setX(150.0f+slDiameter.getValue()+slLensDistance.getValue());
        oRightLens.setY(210.f);
        oRightLens.setWidth(slDiameter.getValue());
        oRightLens.setHeight(slLensThickness.getValue());
        oRightLens.setFill(ownColor);
        Rectangle oRightSideArm = new Rectangle();
        oRightSideArm.setX(150.0f+slDiameter.getValue()*2+slLensDistance.getValue()-slSideArmThickness.getValue());
        oRightSideArm.setY(210.0f-slSideArmLength.getValue());
        oRightSideArm.setWidth(slSideArmThickness.getValue());
        oRightSideArm.setHeight(slSideArmLength.getValue());
        oRightSideArm.setFill(ownColor);
        pnOver.getChildren().addAll(oLeftSideArm, oLeftLens, oLensDistance, oRightLens, oRightSideArm);
    }

    private void drawSide() {
        pnSide.getChildren().clear();
        Rectangle sideArm = new Rectangle();
        sideArm.setX(150.0f);
        sideArm.setY(150.0f);
        sideArm.setWidth(slSideArmLength.getValue());
        sideArm.setHeight(slSideArmThickness.getValue());
        sideArm.setFill(ownColor);
        Rectangle sideLens = new Rectangle();
        sideLens.setX(150.0f-slLensThickness.getValue());
        sideLens.setY(150.0f-slDiameter.getValue()/2);
        sideLens.setWidth(slLensThickness.getValue());
        sideLens.setHeight(slDiameter.getValue());
        sideLens.setFill(ownColor);
        pnSide.getChildren().addAll(sideLens, sideArm);
    }

    private void drawFront() {
        pnFront.getChildren().clear();
        Rectangle fLeftSideArm = new Rectangle();
        fLeftSideArm.setX(100);
        fLeftSideArm.setY(150-slSideArmThickness.getValue()/2);
        fLeftSideArm.setWidth(slSideArmThickness.getValue());
        fLeftSideArm.setHeight(slSideArmThickness.getValue());
        fLeftSideArm.setFill(ownColor);
        Circle fLeftLens = new Circle();
        fLeftLens.setCenterX(100+slSideArmThickness.getValue()+slDiameter.getValue()/2);
        fLeftLens.setCenterY(150);
        fLeftLens.setRadius(slDiameter.getValue()/2);
        fLeftLens.setFill(Color.WHITE);
        fLeftLens.setStroke(ownColor);
        Rectangle fLensDistance = new Rectangle();
        fLensDistance.setX(100+slSideArmThickness.getValue()+slDiameter.getValue());
        fLensDistance.setY(150-slLensThickness.getValue()/2);
        fLensDistance.setWidth(slLensDistance.getValue());
        fLensDistance.setHeight(slLensThickness.getValue());
        fLensDistance.setFill(ownColor);
        Circle fRightLens = new Circle();
        fRightLens.setCenterX(100+slSideArmThickness.getValue()+slDiameter.getValue()/2*3 + slLensDistance.getValue());
        fRightLens.setCenterY(150);
        fRightLens.setRadius(slDiameter.getValue()/2);
        fRightLens.setFill(Color.WHITE);
        fRightLens.setStroke(ownColor);
        Rectangle fRightSideArm = new Rectangle();
        fRightSideArm.setX(100+slSideArmThickness.getValue()+slDiameter.getValue()*2 + slLensDistance.getValue());
        fRightSideArm.setY(150-slSideArmThickness.getValue()/2);
        fRightSideArm.setWidth(slSideArmThickness.getValue());
        fRightSideArm.setHeight(slSideArmThickness.getValue());
        fRightSideArm.setFill(ownColor);
        pnFront.getChildren().addAll(fLeftSideArm, fLeftLens, fLensDistance, fRightLens, fRightSideArm);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawButton.setOnAction(this);
        btInfo.setOnAction(this);
    }
}
