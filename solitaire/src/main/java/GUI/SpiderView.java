package GUI;

import SolitarioBase.*;
import Spider.ZonaSpider;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SpiderView {
    private Stage myStage;
    private Movimiento myMove;
    private JuegoSolitario myGame;
    private GameSaver myGameSaver;
    private App myApp;
    private final ResourceManager myResourceManager = new ResourceManager();
    private static final double frameworkHeightOffset = 32.0;
    private static final String refreshButtonFilename = "refreshing";

    public Scene getScene(Stage stage, JuegoSolitario miJuegoSol, double width, double height, GameSaver gameSaver, App app) throws FileNotFoundException {
        myStage = stage;
        myMove = new Movimiento();
        myGame = miJuegoSol;
        myGameSaver = gameSaver;
        myApp = app;

        var espacioCartasPozo = new StackPane();
        espacioCartasPozo.setId("box-pozo");
        espacioCartasPozo.setOnMouseClicked(new QuiereCartasPozo());

        var espacioPilasJuego = new HBox();
        espacioPilasJuego.setId("box-pilajuegos");

        var resetButton = myResourceManager.getGenericImage(refreshButtonFilename);
        resetButton.setId("refreshing");
        resetButton.setOnMouseClicked(new QuiereReset());

        var leftBar = new HBox();
        leftBar.setId("leftbar");
        leftBar.getChildren().addLast(espacioCartasPozo);
        leftBar.getChildren().addLast(resetButton);

        var descripcionNewGame = myGame.getEstado().generarDescripcion();
        var descCartasPozo = descripcionNewGame.get(0);
        var fabricaCardView = new GUI.CardView();

        // Pozo
        for (String cardDesc : descCartasPozo) {
            espacioCartasPozo.getChildren().addLast(fabricaCardView.fabricarCard(cardDesc));
        }

        // Juegos
        for (int i = 1; i < descripcionNewGame.size(); i++) {
            var pilaCartas = new VBox();
            pilaCartas.getStyleClass().add("pila-juego");
            pilaCartas.setId(String.format("%d",i));

            var cardList = descripcionNewGame.get(i);
            if (cardList.isEmpty()) {
                pilaCartas.getStyleClass().add("pila-juego-vacia");
                pilaCartas.setAlignment(Pos.TOP_CENTER);
                pilaCartas.setOnMouseClicked(new QuiereColocarEnColVacia());
            }

            for (String cardDesc : cardList) {
                var carta = fabricaCardView.fabricarCard(cardDesc);
                carta.setId(cardDesc);
                carta.setOnMouseClicked(new UpdateMovement());
                pilaCartas.getChildren().addLast(carta);
            }
            espacioPilasJuego.getChildren().addLast(pilaCartas);
        }

        var mainWindow = new HBox();
        mainWindow.getChildren().addLast(leftBar);
        mainWindow.getChildren().addLast(espacioPilasJuego);
        mainWindow.setId("mainWindowS");

        var scene = new Scene(mainWindow, width, height);
        scene.getStylesheets().add(GUI.ResourceManager.getStylePath("spider"));

        return scene;
    }

    class QuiereReset implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            myGame.resetEstado();
            if (myGameSaver.isSaved()) {
                try {
                    myGameSaver.deleteGame();
                } catch (IOException e) {
                    Alert myAlert = new Alert(Alert.AlertType.ERROR);
                    myAlert.setContentText("Algo fallo al intentar borrar el archivo de partida...");
                    myAlert.setHeaderText("Error al resetear");
                    myAlert.show();
                    throw new RuntimeException(e);
                }
            }
            myStage.setScene(new MainMenuView().getScene(myStage, myStage.getWidth(), myStage.getHeight(), myGameSaver, myApp));
        }
    }

    class QuiereCartasPozo implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            var movimiento = new Movimiento(ZonaSpider.POZO, null, null, null, false, 0,0);
            myGame.hacerMovimiento(movimiento);
            reRenderScene();
        }
    }

    class UpdateMovement implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            var cardClicked = (ImageView) mouseEvent.getSource();
            var pilaClicked = (VBox) cardClicked.getParent();
            var cardDes = cardClicked.getId();
            String cardinalZonaSpider = pilaClicked.getId();

            var carta = FabricaCartas.generarCarta(cardDes);
            if (carta.estaOculta()) { return; }
            var zona = ZonaSpider.values()[Integer.parseInt(cardinalZonaSpider)];
            var posEnpila = pilaClicked.getChildren().indexOf(cardClicked);
            var lastIndex = pilaClicked.getChildren().size()-1;
            var esCascada = !(posEnpila == lastIndex);

            if (myMove.isPilaOrigenSet()) {
                if (esCascada) { return; } // Caso clickea destino cualquier cosa que no sea la ultima carta
                myMove.setDestino(zona);
                myMove.setCartaDestino(carta);
                myMove.setPosDestino(posEnpila);

                myGame.hacerMovimiento(myMove);

                if (myGame.juegoGanado()) {
                    myStage.setScene(GUI.WinView.getScene());
                    return;
                }

                myMove = new Movimiento();
                reRenderScene();
                return;
            }

            myMove.setOrigen(zona);
            myMove.setCartaOrigen(carta);
            myMove.setEsCascada(esCascada);
            myMove.setPosOrigen(posEnpila);

            ColorAdjust bNW = new ColorAdjust();
            bNW.setBrightness(-0.5);
            cardClicked.setEffect(bNW);
        }
    }

    class QuiereColocarEnColVacia implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (myMove.isPilaOrigenSet()) {
                var pilaClicked = (VBox) mouseEvent.getSource();
                String cardinalZonaSpider = pilaClicked.getId();
                var zona = ZonaSpider.values()[Integer.parseInt(cardinalZonaSpider)];

                myMove.setDestino(zona);
                myMove.setCartaDestino(null);
                myMove.setPosDestino(0);

                myGame.hacerMovimiento(myMove);
                myMove = new Movimiento();
                reRenderScene();
            }
        }
    }

    private void reRenderScene() {
        Scene reRenderedScene;
        var currentWidth = myStage.getWidth();
        var currentHeight = myStage.getHeight() - frameworkHeightOffset;

        try { reRenderedScene = getScene(myStage, myGame, currentWidth, currentHeight, myGameSaver, myApp); }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        myStage.setScene(reRenderedScene);
    }
}