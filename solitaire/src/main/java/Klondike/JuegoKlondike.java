package Klondike;

import SolitarioBase.Card;
import SolitarioBase.JuegoSolitario;
import SolitarioBase.Movimiento;

public class JuegoKlondike extends JuegoSolitario {
    public JuegoKlondike(EstadoKlondike estadoKlondike){ super(estadoKlondike); }

    @Override
    protected boolean esMovimientoValido(Movimiento movimiento){
        var cartaOrigen = movimiento.getCartaOrigen();
        var cartaDestino = movimiento.getCartaDestino();
        var origen = (ZonaKlondike)movimiento.getOrigen();
        var destino = (ZonaKlondike)movimiento.getDestino();
        var esCascada = movimiento.getEsCascada();

        if (destino.esPilaResultado()) {
            if (origen.esPilaResultado()){
                return cartaOrigen.esA() && cartaDestino == null;
            }
            var quiereColocarPrimEnPilaRes = (cartaDestino == null) && cartaOrigen.esA();
            return quiereColocarPrimEnPilaRes || esMovimientoValidoPilaResultado(cartaOrigen, cartaDestino, esCascada);

        } else if (destino.esPilaJuego()) {
            if (origen.esPilaJuego()) {
                if (cartaDestino == null) { return cartaOrigen.esK();}
                return (cartaOrigen.esIntercalado(cartaDestino) && cartaDestino.esMenorConsecutivo(cartaOrigen));
            }
            var quiereColocarEnColumnaVacia = cartaDestino == null && cartaOrigen.esK();
            return quiereColocarEnColumnaVacia || cartaOrigen.esIntercalado(cartaDestino) && cartaDestino.esMenorConsecutivo(cartaOrigen) ;

        } else if(destino.esPilaPozo1() || destino.esPilaPozo2()) {
            return destino.ordinal() == 1 && origen.ordinal() == 0;
        } else {
            return false;
        }
    }

    private boolean esMovimientoValidoPilaResultado(Card cartaOrigen, Card cartaDestino, boolean esCascada ){
        return !(cartaDestino == null) && cartaOrigen.esMenorConsecutivo(cartaDestino) && cartaOrigen.esMismoPalo(cartaDestino) && !esCascada;
    }

}
