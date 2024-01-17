package Spider;

import SolitarioBase.FabricaCartas;
import SolitarioBase.Card;
import SolitarioBase.Movimiento;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovimientoSpiderTest {

    @Test
    public void testMovDesapilar() {
        var origen = ZonaSpider.POZO;
        ZonaSpider destino = null;
        var cartaOrigen = FabricaCartas.generarCarta("1-1-true");
        Card cartaDestino = null;
        var posOrigen = 0;
        var posDestino = 2;
        var mov = new Movimiento(origen, destino, cartaOrigen, cartaDestino, false, posOrigen, posDestino);

        assertEquals(mov.getOrigen(), origen);
        assertEquals(mov.getDestino(), destino);
        assertEquals(mov.getCartaOrigen(), cartaOrigen);
        assertEquals(mov.getCartaDestino(), cartaDestino);
        assertEquals(mov.getEsCascada(), false);
        assertEquals(mov.getPosOrigen(), posOrigen);
        assertEquals(mov.getPosDestino(), posDestino);
    }
}