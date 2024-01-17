import SolitarioBase.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PilaSimpleTest {
    @Test
    public void testCreacionVacia() {
        var pilaRes1 = new PilaSimple(new ArrayList<Card>());
        assertTrue(pilaRes1.estaVacia());
    }
    @Test
    public void testAgregarCartas() {
        var carta1 = new Carta(NumeroCarta.A, Palo.CORAZON, false);
        var pilaRes1 = new PilaSimple(new ArrayList<Card>());
        var arrCarta = new ArrayList<Card>();
        arrCarta.add(carta1);

        pilaRes1.appendearCascada(arrCarta);
        assertFalse(pilaRes1.estaVacia());

        var arrTope = pilaRes1.obtenerCascada(carta1, -1);
        assertEquals(1, arrTope.size());
        var tope = arrTope.get(0);
        assertEquals(tope, carta1);
        assertTrue(pilaRes1.estaVacia());
    }
}