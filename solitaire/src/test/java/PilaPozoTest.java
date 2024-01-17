import SolitarioBase.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PilaPozoTest {
    @Test
    public void testCreacion() {
        var cartas = new ArrayList<Card>();
        var carta1 = new Carta(NumeroCarta.CINCO, Palo.CORAZON, true);
        var carta2 = new Carta(NumeroCarta.A, Palo.TREBOL, true);
        cartas.add(carta1);
        cartas.add(carta2);
        var pozo = new PilaPozo(cartas);

        assertFalse(pozo.estaVacia());
        var arrTope = pozo.obtenerCascada(carta2, -1);
        assertEquals(1, arrTope.size());
        var tope = arrTope.get(0);
        assertEquals(carta2, tope);
    }
    @Test
    public void testPozoVueltaCompleta() {
        var arrVacio = new ArrayList<Card>();
        var arrCarta = new ArrayList<Card>();
        var carta1 = new Carta(NumeroCarta.CINCO, Palo.CORAZON, false);
        arrCarta.add(carta1);
        var pozo = new PilaPozo(arrVacio);
        assertTrue(pozo.estaVacia());

        pozo.appendearCascada(arrCarta);
        assertFalse(pozo.estaVacia());

        var arrCartasDePozo = pozo.obtenerCascada(carta1, -1);
        var tope = arrCartasDePozo.get(0);
        assertEquals(carta1, tope);
    }
}