package GUI;

import Klondike.EstadoKlondike;
import Klondike.JuegoKlondike;
import SolitarioBase.JuegoSolitario;
import Spider.EstadoSpider;
import Spider.JuegoSpider;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    private final GameSaver gameSaver = new GameSaver();
    private JuegoSolitario myGame;
    private static final double defaultWindowsWidth = 1460;
    private static final double defaultWindowsHeight = 860;
    private static final int spiderDefaultWidth = 1650;
    private static final int spiderDefaultHeight = 950;
    private static final int klondikeDefaultWidth = 1500;
    private static final int klondikeDefaultHeight = 1000;

    public void setGame(JuegoSolitario game) { this.myGame = game; }

    public static void main(String[] args) { launch(); }

    private void setSavedGame() {
        if (gameSaver.isSaved()) {
            try {
                gameSaver.loadEstado();
            } catch (IOException e) {
                Alert myAlert = new Alert(Alert.AlertType.ERROR);
                myAlert.setContentText("Algo fallo al intentar cargar el archivo...");
                myAlert.setHeaderText("Error al cargar");
                myAlert.show();
            } catch (ClassNotFoundException e) {
                Alert myAlert = new Alert(Alert.AlertType.ERROR);
                myAlert.setContentText("Algo fallo al intentar cargar el archivo, la partida fue guardada con una version mas reciente del juego");
                myAlert.setHeaderText("Error al cargar");
                myAlert.show();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        setSavedGame();
        var estado = gameSaver.getEstado();
        if (estado != null) {
            var tipoSolitario = estado.getTipoSolitaire();
            if (tipoSolitario.esKlondike()) {
                myGame = (new JuegoKlondike((EstadoKlondike) estado));
                stage.setScene(new GUI.KlondikeView().getScene(stage, myGame, klondikeDefaultWidth, klondikeDefaultHeight, gameSaver, this));
            } else {
                myGame = (new JuegoSpider((EstadoSpider) estado));
                stage.setScene(new GUI.SpiderView().getScene(stage, myGame, spiderDefaultWidth, spiderDefaultHeight, gameSaver, this)); }
        } else {
            stage.setScene(new GUI.MainMenuView().getScene(stage, defaultWindowsWidth, defaultWindowsHeight, gameSaver, this));
        }
        stage.show();
    }

    public void stop() throws Exception {
        if (myGame == null) { return; } // Nunca eligio tipo de solitario

        var estadoCierre = myGame.getEstado();
        if (estadoCierre == null) { return; } // Quiere reset

        try {
            gameSaver.setEstado(estadoCierre);
            gameSaver.saveEstado();
        }
        catch (IOException e) {
            Alert myAlert = new Alert(Alert.AlertType.ERROR);
            myAlert.setContentText("Algo fallo al intentar guardar el archivo...");
            myAlert.setHeaderText("Error al guardar");
            myAlert.show();
        }
    }
}