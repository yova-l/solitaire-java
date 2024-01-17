package GUI;

import Klondike.ZonaKlondike;
import SolitarioBase.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class KlondikeView {
    private static final String refreshButtonFilename = "refreshing";
    private static final double frameworkHeightOffset = 32.0;

    private Stage myStage;
    private Movimiento myMove;
    private JuegoSolitario myGame;
    private GameSaver myGameSaver;
    private App myApp;
    private final ResourceManager myResourceManager = new ResourceManager();

    public Scene getScene(Stage stage, JuegoSolitario miJuegoSol, double width, double height, GameSaver gameSaver, App app) throws FileNotFoundException {
        myStage = stage;
        myMove = new Movimiento();
        myGame = miJuegoSol;
        myGameSaver = gameSaver;
        myApp = app;

        var espacioCartasPozo = new StackPane();
        espacioCartasPozo.setId("box-pozo");
        espacioCartasPozo.getStyleClass().add("cardstack");

        var espacioWaste = new StackPane();
        espacioWaste.setId("box-waste");
        espacioWaste.getStyleClass().add("cardstack");

        var bottomBarPozos = new HBox();
        bottomBarPozos.setId("box-pozos");
        bottomBarPozos.getChildren().addLast(espacioCartasPozo);
        bottomBarPozos.getChildren().addLast(espacioWaste);

        var bottomBarPilasResultado = new HBox();
        bottomBarPilasResultado.setId("box-resultados");


        var resetButton = myResourceManager.getGenericImage(refreshButtonFilename);
        resetButton.setId("refreshing");
        resetButton.setOnMouseClicked(new QuiereReset());

        var bottomBarFull = new HBox();
        bottomBarFull.setId("bottom-bar-full");
        bottomBarFull.getChildren().addLast(bottomBarPozos);
        bottomBarFull.getChildren().addLast(bottomBarPilasResultado);
        bottomBarFull.getChildren().addLast(resetButton);

        var espacioPilasJuego = new HBox();
        espacioPilasJuego.setId("box-pilajuegos");

        var descripcionNewGame = myGame.getEstado().generarDescripcion();
        var descCartasPozo = descripcionNewGame.get(0);
        var descCartasWaste = descripcionNewGame.get(1);
        var fabricaCardView = new GUI.CardView();

        // Pozo
        if (descCartasPozo.isEmpty()) { espacioCartasPozo.setOnMouseClicked(new QuiereRellenarPozo()); }
        for (String cardDesc : descCartasPozo) {
            processCard(fabricaCardView, cardDesc, new QuiereCartasPozo(), espacioCartasPozo);
        }

        // Waste
        if (!descCartasWaste.isEmpty()) {
            // A nivel GUI solo nos interesa la ultima
            processCard(fabricaCardView, descCartasWaste.getLast(), new QuiereDelWaste(), espacioWaste);
        }

        // PilasResultado
        for (int i = ZonaKlondike.PILARESULTADO1.ordinal(); i < ZonaKlondike.PILARESULTADO4.ordinal()+1; i++) {
            var pilaCartas = new StackPane();
            pilaCartas.getStyleClass().add("pila-resultado");
            pilaCartas.getStyleClass().add("cardstack");
            pilaCartas.setId(String.format("%d",i));

            var pilaResDesc = descripcionNewGame.get(i);
            if (pilaResDesc.isEmpty()) { pilaCartas.setOnMouseClicked(new QuiereColocarLaPrimeraEnRes()); }
            else {
                processCard(fabricaCardView, pilaResDesc.getLast(), new UpdateMovement(), pilaCartas);
            }
            bottomBarPilasResultado.getChildren().addLast(pilaCartas);
        }

        // PilasJuego
        for (int i = ZonaKlondike.PILAJUEGO1.ordinal(); i < descripcionNewGame.size(); i++) {
            var pilaCartas = new VBox();
            pilaCartas.getStyleClass().add("pila-juego");
            pilaCartas.setId(String.format("%d", i)); // El id es el cardinal

            var cardList = descripcionNewGame.get(i);
            if (cardList.isEmpty()) {
                pilaCartas.getStyleClass().add("pila-juego-vacia");
                pilaCartas.setOnMouseClicked(new QuiereColocarEnColVacia());
            }

            for (String cardDesc : cardList) {
                processCard(fabricaCardView, cardDesc, new UpdateMovement(), pilaCartas);
            }
            espacioPilasJuego.getChildren().addLast(pilaCartas);
        }

        var mainWindow = new BorderPane();
        mainWindow.setId("mainWindowK");
        mainWindow.setTop(espacioPilasJuego);
        mainWindow.getChildren().addLast(resetButton);
        mainWindow.setBottom(bottomBarFull);

        var scene = new Scene(mainWindow, width, height);
        scene.getStylesheets().add(GUI.ResourceManager.getStylePath("klondike"));

        return scene;
    }

    private void processCard(CardView fabrica, String descripcion, EventHandler<MouseEvent> evento, Pane container) {
        var card = fabrica.fabricarCard(descripcion);
        card.setOnMouseClicked(evento);
        container.getChildren().addLast(card);
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
                }
            }
            myStage.setScene(new MainMenuView().getScene(myStage, myStage.getWidth(), myStage.getHeight(), myGameSaver, myApp));
        }
    }

    class QuiereCartasPozo implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            var cardClicked = (ImageView) mouseEvent.getSource();
            var cardDes = cardClicked.getId();
            var carta = FabricaCartas.generarCarta(cardDes);

            var movimiento = new Movimiento(ZonaKlondike.POZO1, ZonaKlondike.POZO2, carta, null, false, -1,-1);
            myGame.hacerMovimiento(movimiento);
            reRenderScene();
        }
    }

    class QuiereRellenarPozo implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            var movimiento = new Movimiento(ZonaKlondike.POZO1, ZonaKlondike.POZO2, null, null, false, -1,-1);
            myGame.hacerMovimiento(movimiento);
            reRenderScene();
        }
    }

    class QuiereColocarLaPrimeraEnRes implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (!myMove.isPilaOrigenSet()) { return; }

            var pilaClicked = (StackPane) mouseEvent.getSource();
            var zonaKlondikeCardinal = Integer.parseInt(pilaClicked.getId());
            var zona = ZonaKlondike.values()[zonaKlondikeCardinal];

            myMove.setPosDestino(0);
            myMove.setDestino(zona);

            myGame.hacerMovimiento(myMove);
            myMove = new Movimiento();
            reRenderScene();
        }
    }

    class QuiereColocarEnColVacia implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (myMove.isPilaOrigenSet()) {
                var pilaClicked = (VBox) mouseEvent.getSource();
                String cardinalZonaKlondike = pilaClicked.getId();
                var zona = ZonaKlondike.values()[Integer.parseInt(cardinalZonaKlondike)];

                myMove.setDestino(zona);
                myMove.setCartaDestino(null);
                myMove.setPosDestino(0);

                myGame.hacerMovimiento(myMove);
                myMove = new Movimiento();

                reRenderScene();
            }
        }
    }

    class QuiereDelWaste implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (myMove.isPilaOrigenSet()) { myMove = new Movimiento(); return; }

            var cardClicked = (ImageView) mouseEvent.getSource();
            var cardDes = cardClicked.getId();
            var carta = FabricaCartas.generarCarta(cardDes);

            myMove.setOrigen(ZonaKlondike.POZO2);
            myMove.setPosOrigen(-1);
            myMove.setEsCascada(false);
            myMove.setCartaOrigen(carta);

            ColorAdjust bNW = new ColorAdjust();
            bNW.setBrightness(-0.5);
            cardClicked.setEffect(bNW);
        }
    }

    class UpdateMovement implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            var cardClicked = (ImageView) mouseEvent.getSource();
            var pilaClicked = (Pane) cardClicked.getParent();
            var cardDes = cardClicked.getId();
            String cardinalZonaKlondike = pilaClicked.getId();

            var carta = FabricaCartas.generarCarta(cardDes);
            if (carta.estaOculta()) { return; }
            var zona = ZonaKlondike.values()[Integer.parseInt(cardinalZonaKlondike)];
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