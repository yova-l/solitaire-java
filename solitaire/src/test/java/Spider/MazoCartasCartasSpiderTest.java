package Spider;

import SolitarioBase.MazoCartas;
import SolitarioBase.Card;
import SolitarioBase.NumeroCarta;
import org.junit.Test;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MazoCartasCartasSpiderTest {
    @Test
    public void testMazoSemilla() {
        //arrange
        int semilla = 2;
        var mazo1 = new MazoCartas(semilla, 8, false);
        var mazo2 = new MazoCartas(semilla, 8, false);
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
    public void testMazoSpiderCompleto() {
        //arrange
        int semilla = 2;
        var mazo1 = new MazoCartas(semilla, 8, false);
        var arrCartas = mazo1.obtenerCartas(104);
        var contador = new HashMap<NumeroCarta, Integer>();

        //act
        for (Card carta : arrCartas) {
            var key = carta.verNum();
            if (!contador.containsKey(key)) {
                contador.put(key, 1);
            } else {
                var newVal = contador.get(key) + 1;
                contador.put(key, newVal);
            }
        }

        //assert
        for (int cantidad : contador.values()) {
            assertEquals(cantidad, 8);
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testNiMasNiMenos() {
        //arrange
        int semilla = 2;
        var mazo1 = new MazoCartas(semilla, 8, false);
        //act
        mazo1.obtenerCartas(104);
        //assert
        mazo1.obtenerCartas(1);
    }

    @Test
    public void testSemillasDiferentes() {
        //arrange
        int semilla1 = 26;
        int semilla2 = 32;
        var mazo1 = new MazoCartas(semilla1, 8, false);
        var mazo2 = new MazoCartas(semilla2, 8, false);
        var cantidad = 104;
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