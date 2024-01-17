package SolitarioBase;

import java.util.List;
import java.util.function.Function;

public class FabricaPilasEstado {
    public <T extends CardsStack> List<CardsStack> crearPilas(int cantidad, List<List<Card>> contenido, List<CardsStack> pilas, Function<List<Card>, T> crearPila) {
        for (int i = 0; i < cantidad; i++) { pilas.addLast(crearPila.apply(contenido.removeFirst())); }
        return pilas;
    }
}