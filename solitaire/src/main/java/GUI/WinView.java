package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WinView {
    public static Scene getScene() {
        var window = new VBox();
        window.setId("window");

        var label = new Label("GANASTE!!!");
        label.setId("titulo");

        window.getChildren().add(label);

        var scene = new Scene(window, 1800, 800);
        scene.getStylesheets().add(GUI.ResourceManager.getStylePath("win-screen"));

        return scene;
    }
}
