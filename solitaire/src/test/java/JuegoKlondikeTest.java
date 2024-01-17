import Klondike.InicializadorPartidaKlondike;
import Klondike.JuegoKlondike;
import Klondike.ZonaKlondike;
import SolitarioBase.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JuegoKlondikeTest {
    @Test
    public void testJuegoKlondikeInicializarSemilla() {
        var semilla = 9;
        var inicializador = new InicializadorPartidaKlondike(semilla);
        var juego = new JuegoKlondike(inicializador.obtenerEstado());
        assertFalse(juego.juegoGanado());
    }
    @Test
    public void testJuegoKlondikeInicializarDescripcion() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaKlondike(descripcion);
        var juego = new JuegoKlondike(inicializador.obtenerEstado());
        assertFalse(juego.juegoGanado());
        assertEquals(descripcion, juego.getEstado().generarDescripcion());
    }
    @Test
    public void testEjecutarMovimientoValido() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaKlondike(descripcion);
        var juego = new JuegoKlondike(inicializador.obtenerEstado());
        var cartaOrigen = new Carta(NumeroCarta.DIEZ, Palo.TREBOL, false);
        var cartaDestino = new Carta(NumeroCarta.J, Palo.DIAMANTE, false);
        int posOri = 3;
        int posDes = 0;
        var movimiento = new Movimiento(ZonaKlondike.PILAJUEGO4, ZonaKlondike.PILAJUEGO2, cartaOrigen, cartaDestino, false, posOri, posDes);
        juego.hacerMovimiento(movimiento);
        assertFalse(juego.juegoGanado());
        var estado = juego.getEstado();
        assertNotEquals(estado.generarDescripcion(), descripcion);
    }
    @Test
    public void testEjecutarMovimientoInvalido() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaKlondike(descripcion);
        var juego = new JuegoKlondike(inicializador.obtenerEstado());
        var cartaOrigen = new Carta(NumeroCarta.DOS, Palo.CORAZON, false);
        var movimiento = new Movimiento(ZonaKlondike.PILAJUEGO6, ZonaKlondike.PILARESULTADO1, cartaOrigen, null, false, 0,0);
        juego.hacerMovimiento(movimiento);
        var estado = juego.getEstado();
        assertEquals(estado.generarDescripcion(), descripcion);
        assertFalse(juego.juegoGanado());
    }
    @Test
    public void testEjecutarMovimientoGanador() {
        var descripcion = generarDescripcionCasiWin();
        var inicializador = new InicializadorPartidaKlondike(descripcion);
        var juego = new JuegoKlondike(inicializador.obtenerEstado());
        var cartaOrigen = new Carta(NumeroCarta.K, Palo.DIAMANTE, false);
        var cartaDestino = new Carta(NumeroCarta.Q, Palo.DIAMANTE, false);
        var movimiento = new Movimiento(ZonaKlondike.POZO2, ZonaKlondike.PILARESULTADO4, cartaOrigen, cartaDestino, false,0,0);
        assertFalse(juego.juegoGanado());
        juego.hacerMovimiento(movimiento);
        assertTrue(juego.juegoGanado());
        var estado = juego.getEstado();
        assertNotEquals(estado.generarDescripcion(), descripcion);
    }
    @Test
    public void testEjecutarVariosMovimientosDesapiloPozoCompleto() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaKlondike(descripcion);
        var juego = new JuegoKlondike(inicializador.obtenerEstado());

        vaciarElPozo(juego);

        var descPostDesapiladaEsperada = generarDescripcionRandomPozoFullDesapilado();
        var descPostDesapiladaReal = juego.getEstado().generarDescripcion();
        verificarMismasDescripciones(descPostDesapiladaReal, descPostDesapiladaEsperada);
    }
    @Test
    public void testEjecutarVariosMovimientosDesapiloPozoCompletoYUnaMasQuedaIgual() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaKlondike(descripcion);
        var juego = new JuegoKlondike(inicializador.obtenerEstado());

        // Desapilo toodo el pozo y doy la vuelta
        vaciarElPozo(juego);
        var movimiento = new Movimiento(ZonaKlondike.POZO1, ZonaKlondike.POZO2, null, null, false,0,0);
        juego.hacerMovimiento(movimiento);

        var descPostDesapiladaReal = juego.getEstado().generarDescripcion();
        verificarMismasDescripciones(descPostDesapiladaReal, descripcion);
    }

    private static ArrayList<List<String>> generarDescripcionRandom() {
        var descripcion = new ArrayList<List<String>>();
        String[] pozo = {"0-0-true","1-3-true","1-1-true","7-1-true","0-2-true",
                "4-1-true","10-2-true","11-3-true","4-0-true","2-0-true",
                "8-1-true","11-1-true","11-2-true","5-3-true","8-3-true",
                "3-2-true","7-2-true", "11-0-true","6-2-true","6-0-true",
                "5-2-true","9-3-true", "0-3-true","5-0-true"};
        String[] waste = {};
        String[] pr1 = {};
        String[] pr2 = {};
        String[] pr3 = {};
        String[] pr4 = {};
        String[] pj1 = {"10-0-false"};
        String[] pj2 = {"5-2-true","10-3-false"};
        String[] pj3 = {"8-0-true","9-0-true","2-2-false"};
        String[] pj4 = {"7-3-true","4-2-true","7-0-true","9-2-false"};
        String[] pj5 = {"3-3-true","0-1-true","3-0-true","4-3-true","10-1-false"};
        String[] pj6 = {"3-1-true","9-1-true","8-2-true","6-3-true","2-1-true","1-0-false"};
        String[] pj7 = {"12-2-true","6-1-true","12-3-true","12-0-true","1-2-true","2-3-true","12-1-false"};

        String[][] arrFixed = {pozo, waste, pr1, pr2, pr3, pr4, pj1, pj2, pj3, pj4, pj5, pj6, pj7};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }
        return descripcion;
    }
    private static ArrayList<List<String>> generarDescripcionRandomPozoFullDesapilado() {
        var descripcion = new ArrayList<List<String>>();
        String[] pozo = {};
        String[] waste = {"5-0-false","0-3-false","9-3-false","5-2-false","6-0-false",
                          "6-2-false","11-0-false","7-2-false","3-2-false","8-3-false",
                          "5-3-false","11-2-false","11-1-false","8-1-false","2-0-false",
                          "4-0-false","11-3-false","10-2-false","4-1-false","0-2-false",
                          "7-1-false","1-1-false","1-3-false","0-0-false"};
        String[] pr1 = {};
        String[] pr2 = {};
        String[] pr3 = {};
        String[] pr4 = {};
        String[] pj1 = {"10-0-false"};
        String[] pj2 = {"5-2-true","10-3-false"};
        String[] pj3 = {"8-0-true","9-0-true","2-2-false"};
        String[] pj4 = {"7-3-true","4-2-true","7-0-true","9-2-false"};
        String[] pj5 = {"3-3-true","0-1-true","3-0-true","4-3-true","10-1-false"};
        String[] pj6 = {"3-1-true","9-1-true","8-2-true","6-3-true","2-1-true","1-0-false"};
        String[] pj7 = {"12-2-true","6-1-true","12-3-true","12-0-true","1-2-true","2-3-true","12-1-false"};

        String[][] arrFixed = {pozo, waste, pr1, pr2, pr3, pr4, pj1, pj2, pj3, pj4, pj5, pj6, pj7};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }
        return descripcion;
    }
    private static ArrayList<List<String>> generarDescripcionCasiWin() {
        var descripcion = new ArrayList<List<String>>();
        String[] pozo = {};

        String[] waste = {"12-3-false"};
        String[] pr1 = {"0-2-false","1-2-false","2-2-false","3-2-false","4-2-false","5-2-false","6-2-false","7-2-false","8-2-false","9-2-false","10-2-false","11-2-false","12-2-false"};
        String[] pr2 = {"0-0-false","1-0-false","2-0-false","3-0-false","4-0-false","5-0-false","6-0-false","7-0-false","8-0-false","9-0-false","10-0-false","11-0-false","12-0-false"};
        String[] pr3 = {"0-1-false","1-1-false","2-1-false","3-1-false","4-1-false","5-1-false","6-1-false","7-1-false","8-1-false","9-1-false","10-1-false","11-1-false","12-1-false"};
        String[] pr4 = {"0-3-false","1-3-false","2-3-false","3-3-false","4-3-false","5-3-false","6-3-false","7-3-false","8-3-false","9-3-false","10-3-false","11-3-false"};
        String[] pj1 = {};
        String[] pj2 = {};
        String[] pj3 = {};
        String[] pj4 = {};
        String[] pj5 = {};
        String[] pj6 = {};
        String[] pj7 = {};

        String[][] arrFixed = {pozo, waste, pr1, pr2, pr3, pr4, pj1, pj2, pj3, pj4, pj5, pj6, pj7};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }
        return descripcion;
    }
    private static void vaciarElPozo(JuegoKlondike juego) {
        Carta cartaDestino = null;

        var arr_Origen = List.of("5-0-true", "0-3-true","9-3-true","5-2-true","6-0-true" ,"6-2-true","11-0-true","7-2-true"
        ,"3-2-true","8-3-true","5-3-true","11-2-true","11-1-true","8-1-true","2-0-true","4-0-true","11-3-true","10-2-true","4-1-true"
        ,"0-2-true","7-1-true","1-1-true","1-3-true","0-0-true");
        var wrapper = new ArrayList<List<String>>();
        wrapper.addLast(arr_Origen);

        var arrCards = Parsers.generarArrCards(wrapper, new ArrayList<List<Card>>()).get(0);
        Movimiento movimiento;
        for(Card carta : arrCards) {
            movimiento = new Movimiento(ZonaKlondike.POZO1, ZonaKlondike.POZO2, carta, cartaDestino, false,0,0);
            juego.hacerMovimiento(movimiento);
        }
    }
    private static void verificarMismasDescripciones(List<List<String>> desc1, List<List<String>> desc2) {
        for (int i = 0; i < desc1.size(); i++) {
            var pilaA = desc1.get(i);
            var pilaB = desc2.get(i);
            for (int j = 0; j < pilaA.size(); j++) {
                var strA = pilaA.get(j);
                var strB = pilaB.get(j);
                assertEquals(strA, strB);
            }
        }
    }
}

