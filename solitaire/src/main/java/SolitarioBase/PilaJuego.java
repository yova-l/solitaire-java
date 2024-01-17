package SolitarioBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PilaJuego implements PilaJuegoSolitario, Serializable {
    private final List<Card> pilaOcultas;
    private final List<Card> pilaVisibles;

    public PilaJuego(List<Card> cartasMazo, int cantidadAEsconder) {
        pilaOcultas = new Stack<>();
        pilaVisibles = new ArrayList<>();

        for (int i = 0; i < cantidadAEsconder; i++) {
            pilaOcultas.add(cartasMazo.removeLast());
        }
        for (Card carta : cartasMazo) {
            carta.voltear();
            pilaVisibles.add(carta);
        }
    }

    /* Contructor de un estado en particular */
    public PilaJuego(List<Card> cartasMazo){
        pilaOcultas = new Stack<>();
        pilaVisibles = new ArrayList<>();

        for (Card carta : cartasMazo) {
            if (carta.estaOculta()) {
                pilaOcultas.addLast(carta);
            } else {
                pilaVisibles.addLast(carta);
            }
        }

    }

    @Override
    public List<Card> obtenerCascada(Card carta, int posOrigen) {
        List<Card> resul = null;
        List<Card> finalRes = null;
        var numPpCascada = carta.verNum();
        var paloPpCascada = carta.verPalo();

        // Si el usuario no le importa el origen manda -1
        if (posOrigen == -1) { posOrigen = 0;}
        else { posOrigen -= pilaOcultas.size(); }

        for (int i = posOrigen; i < pilaVisibles.size(); i++) {
            var cartaActual = pilaVisibles.get(i);
            var numActual = cartaActual.verNum();
            var paloActual = cartaActual.verPalo();

            if (numActual.equals(numPpCascada) && paloActual.equals(paloPpCascada)) {
                resul = pilaVisibles.subList(i, pilaVisibles.size());
                finalRes = List.copyOf(resul);
                break;
            }
        }

        //Se quito la ultima de las visibles, si aun quedan ocultas la movemos a las visibles
        pilaVisibles.removeAll(resul);
        if (pilaVisibles.isEmpty() && !pilaOcultas.isEmpty()) {
            var cartaDesapilada = pilaOcultas.removeLast();
            cartaDesapilada.voltear();
            pilaVisibles.addLast(cartaDesapilada);
        }
        return finalRes;
    }

    @Override
    public void appendearCascada(List<Card> cascada) {
        pilaVisibles.addAll(cascada);
    }

    @Override
    public boolean estaVacia() {
        return pilaOcultas.isEmpty() && pilaVisibles.isEmpty();
    }

    @Override
    public List<String> generarDescripcion() {
        var res = Parsers.generarDescripcion(pilaOcultas, new ArrayList<>());
        return Parsers.generarDescripcion(pilaVisibles, res);
    }

    @Override
    public List<Card> getListaCompleta() {
        var res = List.copyOf(pilaOcultas);
        var res2 = List.copyOf(pilaVisibles);
        List<Card> resFin = new ArrayList<>();
        resFin.addAll(res);
        resFin.addAll(res2);
        return resFin;
    }

    public List<Card> getListaOcultas() { return List.copyOf(pilaOcultas); }

    public List<Card> getListaVisibles() { return List.copyOf(pilaVisibles); }
}
