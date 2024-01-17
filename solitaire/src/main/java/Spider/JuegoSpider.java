package Spider;

import SolitarioBase.JuegoSolitario;
import SolitarioBase.Movimiento;

public class JuegoSpider extends JuegoSolitario {
    public JuegoSpider(EstadoSpider estadoSpider){ super(estadoSpider); }

    public static boolean quiereCartasPozo(ZonaSpider origen, ZonaSpider destino) {
        return origen.esPilaPozo() && destino == null;
    }

    private boolean esMovEntrePilasJuego(ZonaSpider origen, ZonaSpider destino) {
        return !origen.esPilaPozo() && !destino.esPilaPozo();
    }

    @Override
    protected boolean esMovimientoValido(Movimiento movimiento){
        var cartaOrigen = movimiento.getCartaOrigen();
        var cartaDestino = movimiento.getCartaDestino();
        var origen = (ZonaSpider)movimiento.getOrigen();
        var destino = (ZonaSpider)movimiento.getDestino();
        var indexOrigen = movimiento.getPosOrigen();

        if (quiereCartasPozo(origen, destino) && !((EstadoSpider)(super.estado)).pozoEstaVacio()) {
            return true;
        } else if (esMovEntrePilasJuego(origen, destino)) {

            var estaBloqueda = ((EstadoSpider)super.estado).estaBloqueada(origen, indexOrigen);

            if (JuegoSolitario.sonVisibles(cartaOrigen, cartaDestino) && !estaBloqueda) {
                return cartaDestino == null || cartaDestino.esMenorConsecutivo(cartaOrigen); }
            }
        return false;
    }

}
