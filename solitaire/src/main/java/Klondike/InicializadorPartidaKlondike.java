package Klondike;

import SolitarioBase.*;
import java.util.ArrayList;
import java.util.List;

public class InicializadorPartidaKlondike {
    private final EstadoKlondike estado;

    public InicializadorPartidaKlondike(int semilla) {
        var mazo = new MazoCartas(semilla, 4, false);
        var fabrica = new FabricaPilas(mazo);
        List<CardsStack> pilas = new ArrayList<>();
        pilas = fabrica.crearPilas(1, pilas, ZonaKlondike.POZO1.getDefaultOcultas(), PilaPozo::new);
        pilas = fabrica.crearPilas(5, pilas, 0, PilaSimple::new);
        for (int i = 0; i < 7; i++) { pilas = fabrica.crearPilas( 1, pilas, i+1, i); }
        this.estado = new EstadoKlondike(pilas);
    }

    public InicializadorPartidaKlondike(List<List<String>> desc) {
        /*
        Se dispone de una lista de listas cuyas sublistas se interpretan como las distintas pilas.
        Cada elemento de las sublistas constan del "SolitarioBase.NumeroCarta-SolitarioBase.Palo-EstaOculta" (en ordinal). Ej:
          [ ["11-2-true", "3-3-false"] ]
          En este caso, la primera columna del juego tiene una carta J de TREBOL que esta OCULTA, seguida de
          una carta TRES de DIAMANTE que esta VISIBLE.
         */
        var arrCartas = Parsers.generarArrCards(desc, new ArrayList<>());
        List<CardsStack> pilas = new ArrayList<>();
        var fabricaPilasEstado = new FabricaPilasEstado();
        pilas = fabricaPilasEstado.crearPilas(1, arrCartas, pilas, PilaPozo::new);
        pilas = fabricaPilasEstado.crearPilas(5, arrCartas, pilas, PilaSimple::new);
        pilas = fabricaPilasEstado.crearPilas(7, arrCartas, pilas, PilaJuego::new);
        this.estado = new EstadoKlondike(pilas);
    }

    public EstadoKlondike obtenerEstado(){
        return estado;
    }
}
