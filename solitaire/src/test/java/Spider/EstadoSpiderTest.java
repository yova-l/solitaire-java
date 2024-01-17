package Spider;

import SolitarioBase.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EstadoSpiderTest {

    @Test
    public void testEstadoMovInvalidoA() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);
        assertFalse(juegoSpider.juegoGanado());

        var cartaOri = FabricaCartas.generarCarta("9-1-true"); // pilajuego1
        var cartaDes = FabricaCartas.generarCarta("2-1-false"); // pilajuego2
        var origen = ZonaSpider.PILAJUEGO1;
        var destino = ZonaSpider.PILAJUEGO2;
        var posOrigen = 5;
        var posDestino = 5;
        var esCascada = true;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);

        juegoSpider.hacerMovimiento(movimiento);

        assertFalse(juegoSpider.juegoGanado());
        verificarMismasDescripciones(estado.generarDescripcion(), juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoMovValidoA() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);
        assertFalse(juegoSpider.juegoGanado());

        var cartaOri = FabricaCartas.generarCarta("1-1-false"); // pilajuego4
        var cartaDes = FabricaCartas.generarCarta("2-1-false"); // pilajuego2
        var origen = ZonaSpider.PILAJUEGO4;
        var destino = ZonaSpider.PILAJUEGO2;
        var posOrigen = 5;
        var posDestino = 5;
        var esCascada = false;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);
        var descripcionTrasMovEsperada = new ArrayList<List<String>>();
        String[] pozo = {"2-1-true", "10-1-true", "6-1-true", "3-1-true", "6-1-true", "10-1-true", "9-1-true", "6-1-true", "0-1-true", "2-1-true", "8-1-true", "6-1-true", "6-1-true", "10-1-true", "12-1-true", "8-1-true", "3-1-true", "12-1-true", "4-1-true", "10-1-true"};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-true", "2-1-false", "1-1-false"};
        String[] pj3B = {"11-1-true", "12-1-true", "2-1-true", "4-1-true", "8-1-true", "2-1-false"};
        String[] pj4B = {"6-1-true", "11-1-true", "2-1-true", "3-1-true", "5-1-false"};
        String[] pj5B = {"8-1-true", "1-1-true", "5-1-true", "4-1-true", "0-1-false"};
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false"};
        String[] pj7B = {"3-1-true", "9-1-true", "8-1-true", "1-1-true", "5-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj10B = {"7-1-true", "11-1-true", "4-1-true", "4-1-true", "4-1-false"};
        String[][] arrFixedB = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixedB) {
            descripcionTrasMovEsperada.addLast(Arrays.asList(arrf));
        }
        juegoSpider.hacerMovimiento(movimiento);

        assertFalse(juegoSpider.juegoGanado());
        verificarMismasDescripciones(descripcionTrasMovEsperada, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoRepartirPozoYSeguirPidiedendo() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);

        var movimiento = new Movimiento(ZonaSpider.POZO, null, null, null, false, -1, -1);
        var descripcionTrasMovEsperada = new ArrayList<List<String>>();
        String[] pozo = {};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false", "10-1-false", "2-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-true", "2-1-false","4-1-false", "0-1-false"};
        String[] pj3B = {"11-1-true", "12-1-true", "2-1-true", "4-1-true", "8-1-true", "2-1-false","12-1-false", "6-1-false"};
        String[] pj4B = {"6-1-true", "11-1-true", "2-1-true", "3-1-true", "5-1-true", "1-1-false", "3-1-false", "9-1-false"};
        String[] pj5B = {"8-1-true", "1-1-true", "5-1-true", "4-1-true", "0-1-false", "8-1-false", "10-1-false"};
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false", "12-1-false", "6-1-false"};
        String[] pj7B = {"3-1-true", "9-1-true", "8-1-true", "1-1-true", "5-1-false", "10-1-false", "3-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false", "6-1-false", "6-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false", "6-1-false", "10-1-false"};
        String[] pj10B = {"7-1-true", "11-1-true", "4-1-true", "4-1-true", "4-1-false", "8-1-false", "2-1-false"};
        String[][] arrFixedB = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixedB) {
            descripcionTrasMovEsperada.addLast(Arrays.asList(arrf));
        }
        juegoSpider.hacerMovimiento(movimiento);
        juegoSpider.hacerMovimiento(movimiento); // quedo poco vacio
        juegoSpider.hacerMovimiento(movimiento); // no hace nada

        assertFalse(juegoSpider.juegoGanado());
        verificarMismasDescripciones(descripcionTrasMovEsperada, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoEscaleraCompletada() {
        var descripcion = generarDescripcionCasiEscalera();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);

        var cartaOri = FabricaCartas.generarCarta("0-1-false"); // pilajuego5
        var cartaDes = FabricaCartas.generarCarta("1-1-false"); // pilajuego3
        var origen = ZonaSpider.PILAJUEGO5;
        var destino = ZonaSpider.PILAJUEGO3;
        var posOrigen = 1;
        var posDestino = 13;
        var esCascada = false;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);
        var descripcionTrasMovEsperada = new ArrayList<List<String>>();
        String[] pozo = {"2-1-true", "10-1-true", "6-1-true", "3-1-true", "6-1-true", "10-1-true", "9-1-true", "6-1-true", "0-1-true", "2-1-true", "8-1-true", "6-1-true", "6-1-true", "10-1-true", "12-1-true", "8-1-true", "3-1-true", "12-1-true", "4-1-true", "10-1-true"};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-false"};
        String[] pj3B = {"8-1-true", "2-1-false"}; // mov aca
        String[] pj4B = {"11-1-true", "2-1-true", "4-1-true", "2-1-true", "5-1-false"};
        String[] pj5B = {"1-1-false"}; // mov aca
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false"};
        String[] pj7B = {"3-1-true", "8-1-true", "1-1-true", "5-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "9-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj10B = {"11-1-true", "4-1-true", "4-1-true", "4-1-false"};
        String[][] arrFixed = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixed) {
            descripcionTrasMovEsperada.addLast(Arrays.asList(arrf));
        }

        juegoSpider.hacerMovimiento(movimiento);

        assertFalse(juegoSpider.juegoGanado());
        verificarMismasDescripciones(descripcionTrasMovEsperada, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoSacarUltimaDePilaJuego() {
        var descripcion = new ArrayList<List<String>>();
        String[] pozo = {"2-1-true", "10-1-true", "6-1-true", "3-1-true", "6-1-true", "10-1-true", "9-1-true", "6-1-true", "0-1-true", "2-1-true", "8-1-true", "6-1-true", "6-1-true", "10-1-true", "12-1-true", "8-1-true", "3-1-true", "12-1-true", "4-1-true", "10-1-true"};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-false"};
        String[] pj3B = {"8-1-true", "2-1-false"}; // aca
        String[] pj4B = {"11-1-true", "2-1-true", "4-1-true", "2-1-true", "5-1-false"};
        String[] pj5B = {"1-1-false"}; // mov aca
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false"};
        String[] pj7B = {"3-1-true", "8-1-true", "1-1-true", "5-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "9-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj10B = {"11-1-true", "4-1-true", "4-1-true", "4-1-false"};
        String[][] arrFixed = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);

        var cartaOri = FabricaCartas.generarCarta("1-1-false"); // pilajuego5
        var cartaDes = FabricaCartas.generarCarta("2-1-false"); // pilajuego3
        var origen = ZonaSpider.PILAJUEGO5;
        var destino = ZonaSpider.PILAJUEGO3;
        var posOrigen = 0;
        var posDestino = 1;
        var esCascada = false;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);
        var descripcionTrasMovEsperada = new ArrayList<List<String>>();
        String[] pozoo = {"2-1-true", "10-1-true", "6-1-true", "3-1-true", "6-1-true", "10-1-true", "9-1-true", "6-1-true", "0-1-true", "2-1-true", "8-1-true", "6-1-true", "6-1-true", "10-1-true", "12-1-true", "8-1-true", "3-1-true", "12-1-true", "4-1-true", "10-1-true"};
        String[] pj1BB = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false"};
        String[] pj2BB = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-false"};
        String[] pj3BB = {"8-1-true", "2-1-false","1-1-false"}; // aca
        String[] pj4BB = {"11-1-true", "2-1-true", "4-1-true", "2-1-true", "5-1-false"};
        String[] pj5BB = {}; // mov aca
        String[] pj6BB = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false"};
        String[] pj7BB = {"3-1-true", "8-1-true", "1-1-true", "5-1-false"};
        String[] pj8BB = {"5-1-true", "11-1-true", "7-1-true", "9-1-false"};
        String[] pj9BB = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj10BB = {"11-1-true", "4-1-true", "4-1-true", "4-1-false"};
        String[][] arrFixedd = {pozoo, pj1BB, pj2BB, pj3BB, pj4BB, pj5BB, pj6BB, pj7BB, pj8BB, pj9BB, pj10BB};
        for (String[] arrff : arrFixedd) {
            descripcionTrasMovEsperada.addLast(Arrays.asList(arrff));
        }

        juegoSpider.hacerMovimiento(movimiento);
        verificarMismasDescripciones(descripcionTrasMovEsperada, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoMoverCascada() {
        var descripcion = generarDescripcionCasiEscalera();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);

        var cartaOri = FabricaCartas.generarCarta("4-1-false"); // pilajuego3
        var cartaDes = FabricaCartas.generarCarta("5-1-false"); // pilajuego4
        var origen = ZonaSpider.PILAJUEGO3;
        var destino = ZonaSpider.PILAJUEGO4;
        var posOrigen = 10;
        var posDestino = 13;
        var esCascada = true;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);
        var descripcionTrasMovEsperada = new ArrayList<List<String>>();
        String[] pozo = {"2-1-true", "10-1-true", "6-1-true", "3-1-true", "6-1-true", "10-1-true", "9-1-true", "6-1-true", "0-1-true", "2-1-true", "8-1-true", "6-1-true", "6-1-true", "10-1-true", "12-1-true", "8-1-true", "3-1-true", "12-1-true", "4-1-true", "10-1-true"};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-false"};
        String[] pj3B = {"8-1-true", "2-1-true", "12-1-false", "11-1-false","10-1-false",  "9-1-false", "8-1-false", "7-1-false","6-1-false","5-1-false"};
        String[] pj4B = {"11-1-true", "2-1-true", "4-1-true", "2-1-true", "5-1-false", "4-1-false","3-1-false","2-1-false", "1-1-false"};
        String[] pj5B = {"1-1-true", "0-1-false"}; // mov aca
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false"};
        String[] pj7B = {"3-1-true", "8-1-true", "1-1-true", "5-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "9-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj10B = {"11-1-true", "4-1-true", "4-1-true", "4-1-false"};
        String[][] arrFixed = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }

        juegoSpider.hacerMovimiento(movimiento);
        verificarMismasDescripciones(descripcionTrasMovEsperada, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoMovInvalidoDescendentePorMasDeUno() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);
        assertFalse(juegoSpider.juegoGanado());

        var cartaOri = FabricaCartas.generarCarta("0-1-false"); // pilajuego5
        var cartaDes = FabricaCartas.generarCarta("2-0-false"); // pilajuego2
        var origen = ZonaSpider.PILAJUEGO5;
        var destino = ZonaSpider.PILAJUEGO2;
        var posOrigen = 4;
        var posDestino = 5;
        var esCascada = false;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);

        juegoSpider.hacerMovimiento(movimiento);

        assertFalse(juegoSpider.juegoGanado());
        verificarMismasDescripciones(descripcion, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoQuieroPozo() {
        var descripcion = generarDescripcionRandom();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);

        var movimiento = new Movimiento(ZonaSpider.POZO, null, null, null, false, -1, -1);
        var descripcionTrasMovEsperada = new ArrayList<List<String>>();
        String[] pozo = {};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false", "10-1-false", "2-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-true", "2-1-false","4-1-false", "0-1-false"};
        String[] pj3B = {"11-1-true", "12-1-true", "2-1-true", "4-1-true", "8-1-true", "2-1-false","12-1-false", "6-1-false"};
        String[] pj4B = {"6-1-true", "11-1-true", "2-1-true", "3-1-true", "5-1-true", "1-1-false", "3-1-false", "9-1-false"};
        String[] pj5B = {"8-1-true", "1-1-true", "5-1-true", "4-1-true", "0-1-false", "8-1-false", "10-1-false"};
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false", "12-1-false", "6-1-false"};
        String[] pj7B = {"3-1-true", "9-1-true", "8-1-true", "1-1-true", "5-1-false", "10-1-false", "3-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false", "6-1-false", "6-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false", "6-1-false", "10-1-false"};
        String[] pj10B = {"7-1-true", "11-1-true", "4-1-true", "4-1-true", "4-1-false", "8-1-false", "2-1-false"};
        String[][] arrFixedB = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixedB) {
            descripcionTrasMovEsperada.addLast(Arrays.asList(arrf));
        }
        juegoSpider.hacerMovimiento(movimiento);
        juegoSpider.hacerMovimiento(movimiento); // quedo poco vacio

        assertFalse(juegoSpider.juegoGanado());
        verificarMismasDescripciones(descripcionTrasMovEsperada, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoJuegoGanado() {
        var descripcion = generarDescripcionCasiGanar();
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);

        var cartaOri = FabricaCartas.generarCarta("0-1-false"); // pilajuego5
        var cartaDes = FabricaCartas.generarCarta("1-1-false"); // pilajuego3
        var origen = ZonaSpider.PILAJUEGO5;
        var destino = ZonaSpider.PILAJUEGO3;
        var posOrigen = 0;
        var posDestino = 11;
        var esCascada = false;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);
        var descripcionTrasMovEsperada = new ArrayList<List<String>>();
        String[] pozo = {};
        String[] pj1B = {};
        String[] pj2B = {};
        String[] pj3B = {};
        String[] pj4B = {};
        String[] pj5B = {};
        String[] pj6B = {};
        String[] pj7B = {};
        String[] pj8B = {};
        String[] pj9B = {};
        String[] pj10B = {};
        String[][] arrFixed = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixed) {
            descripcionTrasMovEsperada.addLast(Arrays.asList(arrf));
        }

        juegoSpider.hacerMovimiento(movimiento);

        assertTrue(juegoSpider.juegoGanado());
        verificarMismasDescripciones(descripcionTrasMovEsperada, juegoSpider.getEstado().generarDescripcion());
    }

    @Test
    public void testEstadoNoPuedoPorPilaTrabada() {
        var descripcion = new ArrayList<List<String>>();
        String[] pozo = {};
        String[] pj1B = {};
        String[] pj2B = {};
        String[] pj3B = {"12-1-false", "11-1-false","10-1-false",  "9-1-false", "8-1-false", "7-1-false","6-1-false","5-1-false","4-1-false","3-1-false","2-1-false", "1-1-false"};
        String[] pj4B = {};
        String[] pj5B = {"0-1-false","11-1-false"};
        String[] pj6B = {};
        String[] pj7B = {};
        String[] pj8B = {};
        String[] pj9B = {};
        String[] pj10B = {};
        String[][] arrFixed = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }
        var inicializador = new InicializadorPartidaSpider(descripcion);
        var estado = inicializador.obtenerEstado();
        var juegoSpider = new JuegoSpider(estado);

        var cartaOri = FabricaCartas.generarCarta("0-1-false"); // pilajuego5
        var cartaDes = FabricaCartas.generarCarta("1-1-false"); // pilajuego3
        var origen = ZonaSpider.PILAJUEGO5;
        var destino = ZonaSpider.PILAJUEGO3;
        var posOrigen = 0;
        var posDestino = 11;
        var esCascada = true;
        var movimiento = new Movimiento(origen, destino, cartaOri, cartaDes, esCascada, posOrigen, posDestino);

        juegoSpider.hacerMovimiento(movimiento);

        assertFalse(juegoSpider.juegoGanado());
        verificarMismasDescripciones(descripcion, juegoSpider.getEstado().generarDescripcion());
    }

    private static ArrayList<List<String>> generarDescripcionCasiGanar() {
        var descripcion = new ArrayList<List<String>>();
        String[] pozo = {};
        String[] pj1B = {};
        String[] pj2B = {};
        String[] pj3B = {"12-1-false", "11-1-false","10-1-false",  "9-1-false", "8-1-false", "7-1-false","6-1-false","5-1-false","4-1-false","3-1-false","2-1-false", "1-1-false",}; // dasdsa
        String[] pj4B = {};
        String[] pj5B = {"0-1-false"}; // mov aca
        String[] pj6B = {};
        String[] pj7B = {};
        String[] pj8B = {};
        String[] pj9B = {};
        String[] pj10B = {};
        String[][] arrFixed = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }
        return descripcion;
    }

    private static ArrayList<List<String>> generarDescripcionRandom() {
        /*  semilla 5 genera:
        String[] pozo = {"2-1-true", "10-1-true", "6-1-true", "3-1-true", "6-1-true", "10-1-true", "9-1-true", "6-1-true", "0-1-true", "2-1-true", "8-1-true", "6-1-true", "6-1-true", "10-1-true", "12-1-true", "8-1-true", "3-1-true", "12-1-true", "4-1-true", "10-1-true"};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-true", "2-1-false"};
        String[] pj3B = {"11-1-true", "12-1-true", "2-1-true", "4-1-true", "8-1-true", "2-1-false"};
        String[] pj4B = {"6-1-true", "11-1-true", "2-1-true", "3-1-true", "5-1-true", "1-1-false"};
        String[] pj5B = {"8-1-true", "1-1-true", "5-1-true", "4-1-true", "0-1-false"};
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false"};
        String[] pj7B = {"3-1-true", "9-1-true", "8-1-true", "1-1-true", "5-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj10B = {"7-1-true", "11-1-true", "4-1-true", "4-1-true", "4-1-false"};
        */
        var descripcion = new ArrayList<List<String>>();
        var mazo = new MazoCartas(5, 8, true);

        var pozo = mazo.obtenerCartas(20);
        var pj1 = mazo.obtenerCartas(6);
        var pj2 = mazo.obtenerCartas(6);
        var pj3 = mazo.obtenerCartas(6);
        var pj4 = mazo.obtenerCartas(6);
        var pj5 = mazo.obtenerCartas(5);
        var pj6 = mazo.obtenerCartas(5);
        var pj7 = mazo.obtenerCartas(5);
        var pj8 = mazo.obtenerCartas(5);
        var pj9 = mazo.obtenerCartas(5);
        var pj10 = mazo.obtenerCartas(5);

        var pasoPozo = false;
        List<Card>[] arrFixed = new List[]{pozo, pj1, pj2, pj3, pj4, pj5, pj6, pj7, pj8, pj9, pj10};
        for (List<Card> listaCartas : arrFixed) {
            if (!pasoPozo) {
                pasoPozo = true;
                continue;
            }
            listaCartas.getLast().voltear();
        }

        var pozoCS = new PilaPozo(pozo);
        var pj1CS = new PilaJuego(pj1);
        var pj2CS = new PilaJuego(pj2);
        var pj3CS = new PilaJuego(pj3);
        var pj4CS = new PilaJuego(pj4);
        var pj5CS = new PilaJuego(pj5);
        var pj6CS = new PilaJuego(pj6);
        var pj7CS = new PilaJuego(pj7);
        var pj8CS = new PilaJuego(pj9);
        var pj9CS = new PilaJuego(pj9);
        var pj10CS = new PilaJuego(pj10);

        CardsStack[] listCardsSta = {pozoCS, pj1CS, pj2CS, pj3CS, pj4CS,pj5CS,pj6CS,pj7CS,pj8CS,pj9CS,pj10CS};
        for (CardsStack cS : listCardsSta) {
            descripcion.addLast(cS.generarDescripcion());
        }
        return descripcion;
    }

    private static ArrayList<List<String>> generarDescripcionCasiEscalera() {
        var descripcion = new ArrayList<List<String>>();
        String[] pozo = {"2-1-true", "10-1-true", "6-1-true", "3-1-true", "6-1-true", "10-1-true", "9-1-true", "6-1-true", "0-1-true", "2-1-true", "8-1-true", "6-1-true", "6-1-true", "10-1-true", "12-1-true", "8-1-true", "3-1-true", "12-1-true", "4-1-true", "10-1-true"};
        String[] pj1B = {"9-1-true", "9-1-true", "8-1-true", "6-1-true", "3-1-true", "11-1-false"};
        String[] pj2B = {"1-1-true", "7-1-true", "5-1-true", "7-1-true", "9-1-false"};
        String[] pj3B = {"8-1-true", "2-1-true", "12-1-false", "11-1-false","10-1-false",  "9-1-false", "8-1-false", "7-1-false","6-1-false","5-1-false","4-1-false","3-1-false","2-1-false", "1-1-false"}; // dasdsa
        String[] pj4B = {"11-1-true", "2-1-true", "4-1-true", "2-1-true", "5-1-false"};
        String[] pj5B = {"1-1-true", "0-1-false"}; // mov aca
        String[] pj6B = {"1-1-true", "1-1-true", "5-1-true", "12-1-true", "7-1-false"};
        String[] pj7B = {"3-1-true", "8-1-true", "1-1-true", "5-1-false"};
        String[] pj8B = {"5-1-true", "11-1-true", "7-1-true", "9-1-false"};
        String[] pj9B = {"5-1-true", "11-1-true", "7-1-true", "10-1-true", "9-1-false"};
        String[] pj10B = {"11-1-true", "4-1-true", "4-1-true", "4-1-false"};
        String[][] arrFixed = {pozo, pj1B, pj2B, pj3B, pj4B, pj5B, pj6B, pj7B, pj8B, pj9B, pj10B};
        for (String[] arrf : arrFixed) {
            descripcion.addLast(Arrays.asList(arrf));
        }
        return descripcion;
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