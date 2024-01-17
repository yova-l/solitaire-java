package SolitarioBase;

import java.util.List;
import java.util.function.Function;

public class FabricaPilas {
    private final MazoCartas mazo;

    public FabricaPilas(MazoCartas mazo) {
        this.mazo = mazo;
    }

    public List<CardsStack> crearPilas(int cantidadPilas, List<CardsStack> pilas, int cantidadTotal, int cantidadAEsconder) {
        for (int i = 0; i < cantidadPilas; i++) { pilas.addLast(new PilaJuego(mazo.obtenerCartas(cantidadTotal), cantidadAEsconder)); }
        return pilas;
    }

    public <T extends CardsStack> List<CardsStack> crearPilas(int cantidadPilas, List<CardsStack> pilas, int cantCartas, Function<List<Card>, T> crearPila) {
        for (int i = 0; i < cantidadPilas; i++) { pilas.addLast(crearPila.apply(mazo.obtenerCartas(cantCartas))); }
        return pilas;
    }
}
