import SolitarioBase.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PilaJuegoTest {
    @Test
    public void testPilaJuego1MovBasico() {
        //arrange
        var designadasDelMazo = new ArrayList<Card>();
        var carta = new Carta(NumeroCarta.A, Palo.CORAZON,true);
        designadasDelMazo.addLast(carta);

        //act
        var pila1 = new PilaJuego(designadasDelMazo,0);

        //assert
        var arrCartaEnPila = pila1.obtenerCascada(carta, -1);
        var cartaObtenida = arrCartaEnPila.get(0);
        assertEquals(NumeroCarta.A, cartaObtenida.verNum());
        assertEquals(Palo.CORAZON, cartaObtenida.verPalo());
        assertEquals(1, arrCartaEnPila.size());
        assertFalse(cartaObtenida.estaOculta());
    }
    @Test
    public void testPilaJuego2MovBasico() {
        var designadasDelMazo = new ArrayList<Card>();
        var carta1 = new Carta(NumeroCarta.A, Palo.CORAZON,true);
        var carta2 = new Carta(NumeroCarta.DOS, Palo.TREBOL,true);
        designadasDelMazo.addLast(carta1);
        designadasDelMazo.addLast(carta2);

        var pila2 = new PilaJuego(designadasDelMazo,1);

        var arrCartaEnPila = pila2.obtenerCascada(carta1, -1);
        var carta1Obtenida = arrCartaEnPila.get(0);
        assertEquals(NumeroCarta.A, carta1Obtenida.verNum());
        assertEquals(Palo.CORAZON, carta1Obtenida.verPalo());
        assertEquals(1, arrCartaEnPila.size());
        assertFalse(carta1Obtenida.estaOculta());

        var arr2CartaEnPila = pila2.obtenerCascada(carta2, -1);
        var carta2Obtenida = arr2CartaEnPila.get(0);
        assertEquals(NumeroCarta.DOS, carta2Obtenida.verNum());
        assertEquals(Palo.TREBOL, carta2Obtenida.verPalo());
        assertEquals(1, arr2CartaEnPila.size());
        assertFalse(carta2Obtenida.estaOculta());
    }
    @Test
    public void testPilaJuego6Appends1AndPedirCascada() {
        var carta1 = new Carta(NumeroCarta.OCHO, Palo.CORAZON, true);
        var carta2 = new Carta(NumeroCarta.DOS, Palo.TREBOL, true);
        var carta3 = new Carta(NumeroCarta.A, Palo.TREBOL, true);
        var carta4 = new Carta(NumeroCarta.Q, Palo.DIAMANTE, true);
        var carta5 = new Carta(NumeroCarta.K, Palo.TREBOL, true);
        var carta6 = new Carta(NumeroCarta.TRES, Palo.TREBOL, true);
        var vectorCartas = new Carta[]{carta1, carta2, carta3, carta4, carta5, carta6};
        var designadasDelMazo = new ArrayList<Card>(); // Las del mazo vienen todas ocultas
        for (Carta carta : vectorCartas) {
            designadasDelMazo.addLast(carta);
        }
        var cartaCascadaIn1 = new Carta(NumeroCarta.SIETE, Palo.TREBOL, false);
        var cascadaIn = new ArrayList<Card>();
        cascadaIn.addLast(cartaCascadaIn1);

        // [3-tre-o, k-tre-o, q-dia-o, a-tre-o, 2-tre-o, 8-cor]
        var pila1 = new PilaJuego(designadasDelMazo, 5);
        // [3-tre-o, k-tre-o, q-dia-o, a-tre-o, 2-tre-o, 8-cor, 7-tre]
        pila1.appendearCascada(cascadaIn);

        var checkNuevaCascada = pila1.obtenerCascada(carta1, -1);
        // [3-tre-o, k-tre-o, q-dia-o, a-tre-o, 2-tre]
        var cartaCheck1 = checkNuevaCascada.get(0);
        var cartaCheck2 = checkNuevaCascada.get(1);
        assertEquals(carta1.verNum(), cartaCheck1.verNum());
        assertEquals(carta1.verPalo(), cartaCheck1.verPalo());
        assertFalse(cartaCheck1.estaOculta());
        assertEquals(cartaCascadaIn1.verPalo(), cartaCheck2.verPalo());
        assertEquals(cartaCascadaIn1.verNum(), cartaCheck2.verNum());
        assertFalse(cartaCheck2.estaOculta());
    }
    @Test
    public void testPilaJuego7VariosMovs() {
        var carta1 = new Carta(NumeroCarta.OCHO, Palo.CORAZON, true);
        var carta2 = new Carta(NumeroCarta.DOS, Palo.TREBOL, true);
        var carta3 = new Carta(NumeroCarta.A, Palo.TREBOL, true);
        var carta4 = new Carta(NumeroCarta.Q, Palo.DIAMANTE, true);
        var carta5 = new Carta(NumeroCarta.K, Palo.TREBOL, true);
        var carta6 = new Carta(NumeroCarta.TRES, Palo.TREBOL, true);
        var carta7 = new Carta(NumeroCarta.CUATRO, Palo.CORAZON, true);
        var vectorCartas = new Card[]{carta1, carta2, carta3, carta4, carta5, carta6, carta7};
        var designadasDelMazo = new ArrayList<Card>(); // Las del mazo vienen todas ocultas
        for (Card carta : vectorCartas) {
            designadasDelMazo.addLast(carta);
        }
        var cartaCascadaIn1 = new Carta(NumeroCarta.SIETE, Palo.TREBOL, false);
        var cartaCascadaIn2 = new Carta(NumeroCarta.SEIS, Palo.CORAZON, false);
        var cascadaIn = new ArrayList<Card>();
        cascadaIn.addLast(cartaCascadaIn1);
        cascadaIn.addLast(cartaCascadaIn2);

        // [4-cor-o, 3-tre-o, k-tre-o, q-dia-o, a-tre-o, 2-tre-o, 8-cor]
        var pila1 = new PilaJuego(designadasDelMazo, 6);
        // [4-cor-o, 3-tre-o, k-tre-o, q-dia-o, a-tre-o, 2-tre-o, 8-cor, 7-tre, seis-cor]
        pila1.appendearCascada(cascadaIn);

        // [4-cor-o, 3-tre-o, k-tre-o, q-dia-o, a-tre-o, 2-tre-o, 8-cor]
        var checkCascada1 = pila1.obtenerCascada(cartaCascadaIn1, -1); // [7-tre, seis-cor]
        var carta1Cascada1 = checkCascada1.get(0);
        var carta2Cascada1 = checkCascada1.get(1);
        assertEquals(cartaCascadaIn1.verNum(), carta1Cascada1.verNum());
        assertEquals(cartaCascadaIn1.verPalo(), carta1Cascada1.verPalo());
        assertFalse(carta1Cascada1.estaOculta());
        assertEquals(cartaCascadaIn2.verPalo(), carta2Cascada1.verPalo());
        assertEquals(cartaCascadaIn2.verNum(), carta2Cascada1.verNum());
        assertFalse(carta2Cascada1.estaOculta());

        // [4-cor-o, 3-tre-o, k-tre-o, q-dia-o, a-tre-o, 2-tre]
        var checkCascada2 = pila1.obtenerCascada(carta1, -1); // [8-cor]
        var carta1Cascada2 = checkCascada2.get(0);
        assertEquals(carta1.verNum(), carta1Cascada2.verNum());
        assertEquals(carta1.verPalo(), carta1Cascada2.verPalo());
        assertFalse(carta1Cascada2.estaOculta());

        // [4-cor-o, 3-tre-o, k-tre-o, q-dia-o, a-tre]
        var checkCascada3 = pila1.obtenerCascada(carta2, -1); //[2-tre]
        var carta1Cascada3 = checkCascada3.get(0);
        assertEquals(carta2.verNum(), carta1Cascada3.verNum());
        assertEquals(carta2.verPalo(), carta1Cascada3.verPalo());
        assertFalse(carta1Cascada3.estaOculta());
    }
}