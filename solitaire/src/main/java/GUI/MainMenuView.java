package GUI;

import Klondike.InicializadorPartidaKlondike;
import Klondike.JuegoKlondike;
import SolitarioBase.TipoSolitario;
import Spider.InicializadorPartidaSpider;
import Spider.JuegoSpider;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.Random;

public class MainMenuView {
    private VBox window;
    private ChoiceBox<TipoSolitario> choiceBox;
    private Stage stage;
    private GameSaver myGameSaver;
    private App myApp;
    private TextField seedInput;
    private static final int maxSeed = 9999;
    private static final int spiderDefaultWidth = 1650;
    private static final int spiderDefaultHeight = 950;
    private static final int klondikeDefaultWidth = 1500;
    private static final int klondikeDefaultHeight = 1000;

    public Scene getScene(Stage mainStage, double width, double height, GameSaver gameSaver, App app) {
        stage = mainStage;
        myGameSaver = gameSaver;
        myApp = app;

        window = new VBox();

        choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(TipoSolitario.KLONDIKE, TipoSolitario.SPIDER));
        Button btn = new Button("Start");
        seedInput = new TextField();
        var labelSeedBox = new Label("Coloca una semilla (1-9999) o nada para elegir una aleatoria...");

        window.setId("window");
        choiceBox.setId("ChoiceBox");
        btn.setId("StartButton");
        seedInput.setId("seedInput");
        labelSeedBox.setId("seedLabel");

        var title = new Label("Elige un tipo de solitario");
        title.setId("titleSoli");

        var seedBox = new VBox();
        seedBox.setId("seedBox");
        seedBox.getChildren().add(labelSeedBox);
        seedBox.getChildren().add(seedInput);

        var startBox = new VBox();
        startBox.setId("startBox");
        startBox.getChildren().add(choiceBox);
        startBox.getChildren().add(btn);

        window.getChildren().add(title);
        window.getChildren().add(startBox);
        window.getChildren().add(seedBox);

        var scene = new Scene(window, width, height);
        btn.setOnAction(new MyClickListener());
        scene.getStylesheets().add(GUI.ResourceManager.getStylePath("mainmenu"));
        return scene;
    }

    class MyClickListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            Scene scene;
            try { scene = setSolitaireScene(); }
            catch (FileNotFoundException e) { throw new RuntimeException(e); }

            if (scene != null) {
                stage.setScene(scene);
                return;
            }

            var mainWindowNodes = window.getChildren();
            if (mainWindowNodes.size() == 3) {
                var label = new Label("Debe seleccionar un modo de juego para poder continuar...");
                window.getChildren().addFirst(label);
            }
        }
    }

    // Setea el state y asigna la escena al stage.
    private Scene setSolitaireScene() throws FileNotFoundException {
        var choice = choiceBox.getValue();
        Scene scene = null;

        int seed = parseSeed(seedInput.getText()); // 24 es piola para spider, 8 es buena para klondike

        if (choice != null) {
            if (choice.esKlondike()) {
                var inicializador = new InicializadorPartidaKlondike(seed);
                var estadoInicial = inicializador.obtenerEstado();
                var juegoKlondike = new JuegoKlondike(estadoInicial);
                myApp.setGame(juegoKlondike);
                scene = new GUI.KlondikeView().getScene(stage, juegoKlondike, klondikeDefaultWidth, klondikeDefaultHeight, myGameSaver, myApp);
            }
            else if (choice.esSpider()) {
                var inicializador = new InicializadorPartidaSpider(seed);
                var estadoInicial = inicializador.obtenerEstado();
                var juegoSpider = new JuegoSpider(estadoInicial);
                myApp.setGame(juegoSpider);
                scene = new GUI.SpiderView().getScene(stage, juegoSpider, spiderDefaultWidth, spiderDefaultHeight, myGameSaver, myApp);
            }
        }
        return scene;
    }

    private int parseSeed(String seedStr) {
        int intVal;
        try {
            intVal = Integer.parseInt(seedStr);
        } catch (NumberFormatException e) {
            Random rand = new Random();
            intVal = rand.nextInt(maxSeed);
        }
        return intVal;
    }
}
