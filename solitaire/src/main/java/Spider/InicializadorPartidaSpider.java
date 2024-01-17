package Spider;

import SolitarioBase.*;
import java.util.ArrayList;
import java.util.List;

public class InicializadorPartidaSpider {
    private final EstadoSpider estado;

    public InicializadorPartidaSpider(int semilla) {
        var fabrica = new FabricaPilas(new MazoCartas(semilla, 8, true));
        List<CardsStack> pilas = new ArrayList<>();
        pilas = fabrica.crearPilas( 1, pilas, ZonaSpider.POZO.getDefaultOcultas(), PilaPozo::new);
        pilas = fabrica.crearPilas(4, pilas, 6, 5);
        pilas = fabrica.crearPilas(6, pilas, 5, 4);
        this.estado = new EstadoSpider(pilas);
    }

    public InicializadorPartidaSpider(List<List<String>> desc) {
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
        pilas = fabricaPilasEstado.crearPilas(10, arrCartas, pilas, PilaJuego::new);
        this.estado = new EstadoSpider(pilas);
    }

    public EstadoSpider obtenerEstado(){ return estado; }
}
