package SolitarioBase;

import java.util.List;

public interface PilaJuegoSolitario extends CardsStack {
    List<Card> getListaOcultas();
    List<Card> getListaVisibles();
    List<Card> getListaCompleta();
}
