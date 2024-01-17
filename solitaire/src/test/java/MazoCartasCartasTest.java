import SolitarioBase.Card;
import SolitarioBase.MazoCartas;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MazoCartasCartasTest {
    @Test
    public void testMazoSemilla() {
        //arrange
        int semilla = 2;
        var mazo1 = new MazoCartas(semilla,4,true);
        var mazo2 = new MazoCartas(semilla,4,true);
        int cantidad = 10;

        //act
        var waste1 = mazo1.obtenerCartas(cantidad);
        var waste2 = mazo2.obtenerCartas(cantidad);

        //assert
        for (int i = 0; i < cantidad; i++) {
            var carta1 = waste1.get(i);
            var carta2 = waste2.get(i);
            assertEquals(carta1.verPalo(), carta2.verPalo());
            assertEquals(carta1.verNum(), carta2.verNum());
            assertEquals(carta1.verColor(), carta2.verColor());
        }
    }
    @Test
    public void testCartasUnicasKlondike() {
        //arrange
        int semilla = 2;
        var mazo1 = new MazoCartas(semilla,4,false);
        var arrCartas = mazo1.obtenerCartas(52);
        var contadorUnicos = new HashMap<String, Integer>();

        //act
        for (Card carta : arrCartas) {
            var key = String.format("%s-%s",carta.verNum(), carta.verPalo());
            if (!contadorUnicos.containsKey(key)) {
                contadorUnicos.put(key, 1);
            } else {
                var newVal = contadorUnicos.get(key) + 1;
                contadorUnicos.put(key, newVal);
            }
        }

        //assert
        for (int cantidad : contadorUnicos.values()) {
            assertEquals(cantidad, 1);
        }
    }
    @Test(expected = NoSuchElementException.class)
    public void testSonCincuentayDos() {
        //arrange
        int semilla = 2;
        var mazo1 = new MazoCartas(semilla,4,false);
        //act
        mazo1.obtenerCartas(52);
        //assert
        mazo1.obtenerCartas(1);
    }
    @Test
    public void testSemillasDiferentes() {
        //arrange
        int semilla1 = 26;
        int semilla2 = 32;
        var mazo1 = new MazoCartas(semilla1,4,false);
        var mazo2 = new MazoCartas(semilla2,4,false);
        var cantidad = 52;
        var arrCartas1 = mazo1.obtenerCartas(cantidad);
        var arrCartas2 = mazo2.obtenerCartas(cantidad);
        var cantIguales = 0;

        //act
        for (int i = 0; i < cantidad; i++) {
            var carta1 = arrCartas1.get(i);
            var carta2 = arrCartas2.get(i);
            if (carta1.verPalo() == carta2.verPalo() && carta1.verNum() == carta2.verNum()) {
                cantIguales++;
            }
        }

        //assert
        assertNotEquals(cantIguales, cantidad);
    }
}