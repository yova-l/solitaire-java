package SolitarioBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PilaPozo implements CardsStack, Serializable {
    private final List<Card> listaCartas;
    public PilaPozo(List<Card> listaCartas) {
        this.listaCartas = listaCartas;
    }

    @Override
    public List<Card> obtenerCascada(Card carta, int posOrigen) {
        var exTope = listaCartas.removeLast();
        exTope.voltear();
        var res = new ArrayList<Card>();
        res.add(exTope);
        return res;
    }

    @Override
    public void appendearCascada(List<Card> arrCartas) {
        var carta = arrCartas.get(0);
        carta.voltear();
        listaCartas.addAll(arrCartas);
    }

    @Override
    public boolean estaVacia() { return listaCartas.isEmpty(); }

    @Override
    public List<String> generarDescripcion() { return Parsers.generarDescripcion(listaCartas, new ArrayList<>()); }
}
