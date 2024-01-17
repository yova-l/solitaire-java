package SolitarioBase;

public interface Card extends Descriptable {
    NumeroCarta verNum();
    Color verColor();
    Palo verPalo();
    boolean estaOculta();
    boolean esA();
    boolean esK();
    boolean esMenorConsecutivo(Card cartaDestino);
    boolean esIntercalado(Card otraCarta);
    boolean esMismoPalo(Card otraCarta);
    void voltear();

}
