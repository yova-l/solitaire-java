package Klondike;

import SolitarioBase.CardsStack;
import SolitarioBase.Estado;
import SolitarioBase.Movimiento;
import SolitarioBase.TipoSolitario;

import java.io.Serializable;
import java.util.List;

public class EstadoKlondike extends Estado implements Serializable {
    public EstadoKlondike(List<CardsStack> pilasCartas) { super(pilasCartas, TipoSolitario.KLONDIKE); }

    @Override
    public void actualizarEstado(Movimiento movimiento) {
        // PRECONDICION: el movimiento a realizar ES VALIDO
        // Caso borde: no quedaron mas cartas en el POZO1
        var origen = movimiento.getOrigen();
        var destino = movimiento.getDestino();
        var pilaOriIndex = origen.ordinal();
        var pilaDesIndex = destino.ordinal();
        var pilaOrigen = pilasCartas.get(pilaOriIndex);
        var pilaDestino = pilasCartas.get(pilaDesIndex);
        var cartaOrigen = movimiento.getCartaOrigen();
        var posOrigen = movimiento.getPosOrigen();

        if (origen == ZonaKlondike.POZO1 && cartaOrigen == null) {
            while (!pilaDestino.estaVacia()) {
                var arrDesapilada = pilaDestino.obtenerCascada(null, posOrigen);
                pilaOrigen.appendearCascada(arrDesapilada);
            }
            return;
        }
        super.actualizarEstado(movimiento);
    }

    @Override
    public boolean esPartidaGanada(){
        // En Klondike Interpretamos la lectura del tablero como:
        // 0,1 (PilasPozo); 2,3,4,5 (PilasResultado); 6,..12 (PilasJuego)
        // El juego esta ganado si todas las pilas menos las resultado estan vacias
        for (int i = 0; i < pilasCartas.size(); i++) {
            if (i >= ZonaKlondike.PILARESULTADO1.ordinal() && i <= ZonaKlondike.PILARESULTADO4.ordinal()) {
                continue;
            }
            var pila = pilasCartas.get(i);
            if (!pila.estaVacia()) {
                return false;
            }
        }
        return true;
    }
}
