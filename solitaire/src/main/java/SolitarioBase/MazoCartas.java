package SolitarioBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MazoCartas {
    private final List<Card> cartas;

    public MazoCartas(int semilla, int cantidadJuegos, boolean mismoPalo) {
        cartas = new ArrayList<>();
        var palo = mismoPalo ? Palo.PICA : Palo.DIAMANTE;
        for (int i = 0; i < cantidadJuegos ; i++) {
            if (!mismoPalo) {
                palo = switchearPalo(palo);
            }
            for (NumeroCarta numero : NumeroCarta.values()) {
                var desc = String.format("%d-%d-%b",numero.ordinal(), palo.ordinal(), true);
                var nueva_carta = FabricaCartas.generarCarta(desc);
                cartas.add(nueva_carta);
            }
        }
        Random rnd = new Random(semilla);
        Collections.shuffle(cartas, rnd);
    }

    private Palo switchearPalo(Palo palo){
        if (palo.ordinal() == 3) {
            return Palo.CORAZON;
        }
        return Palo.values()[palo.ordinal() + 1];
    }

    public List<Card> obtenerCartas(int cantidad) {
        var conjuntoCartas = new ArrayList<Card>();
        for (int i = 0 ; i < cantidad ; i = i + 1) { conjuntoCartas.addLast(cartas.removeLast()); }
        return conjuntoCartas;
    }
}
