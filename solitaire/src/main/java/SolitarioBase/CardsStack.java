package SolitarioBase;

import java.util.List;
public interface CardsStack {
    List<Card> obtenerCascada(Card carta, int posOrigen);
    void appendearCascada(List<Card> arrCartas);
    boolean estaVacia();
    List<String> generarDescripcion();
}
