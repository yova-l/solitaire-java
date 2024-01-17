import SolitarioBase.Carta;
import SolitarioBase.Color;
import SolitarioBase.NumeroCarta;
import SolitarioBase.Palo;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartaTest {
    @Test
    public void testCrearCarta1() {
        //arrange
        Carta carta = new Carta(NumeroCarta.A, Palo.DIAMANTE,true);
        //act
        //assert
        assertEquals(NumeroCarta.A, carta.verNum());
        assertEquals(Palo.DIAMANTE, carta.verPalo());
        assertEquals(carta.verColor(), Color.ROJO);
        assertTrue(carta.estaOculta());
    }
    @Test
    public void testCrearCarta2() {
        //arrange
        Carta carta = new Carta(NumeroCarta.DOS, Palo.PICA,false);
        //act
        //assert
        assertEquals(NumeroCarta.DOS, carta.verNum());
        assertEquals(Palo.PICA, carta.verPalo());
        assertEquals(carta.verColor(), Color.NEGRO);
        assertFalse(carta.estaOculta());
    }
    @Test
    public void testCrearCarta3() {
        //arrange
        Carta carta = new Carta(NumeroCarta.K, Palo.CORAZON,true);
        //act
        //assert
        assertEquals(NumeroCarta.K, carta.verNum());
        assertEquals(Palo.CORAZON, carta.verPalo());
        assertEquals(carta.verColor(), Color.ROJO);
        assertTrue(carta.estaOculta());
    }
    @Test
    public void testCrearCarta4() {
        //arrange
        Carta carta = new Carta(NumeroCarta.CUATRO, Palo.TREBOL,false);
        //act
        //assert
        assertEquals(NumeroCarta.CUATRO, carta.verNum());
        assertEquals(Palo.TREBOL, carta.verPalo());
        assertEquals(carta.verColor(), Color.NEGRO);
        assertFalse(carta.estaOculta());
    }
    @Test
    public void testVolterCarta() {
        //arrange
        Carta carta = new Carta(NumeroCarta.CUATRO, Palo.TREBOL,false);
        //act
        carta.voltear();
        //assert
        assertTrue(carta.estaOculta());
    }
    @Test
    public void testGenerarDescripcion() {
        var carta1 = new Carta(NumeroCarta.A, Palo.CORAZON, true);
        var res = "0-0-true";
        assertEquals(res, carta1.generarDescripcion());
    }
}