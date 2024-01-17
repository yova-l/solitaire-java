package Spider;

import SolitarioBase.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InicializadorPartidaSpiderTest {
    @Test
    public void testInicializarConDesc() {
        var descripcion = generarDescripcionRandom();
        var inicializador1 = new InicializadorPartidaSpider(descripcion);
        var estado1 = inicializador1.obtenerEstado();
        var descFromEstado = estado1.generarDescripcion();
        verificarMismasDescripciones(descripcion, descFromEstado);
    }

    @Test
    public void testInicializarConSemilla() {
        var semilla = 8;
        var inicializador1 = new InicializadorPartidaSpider(semilla);
        var inicializador2 = new InicializadorPartidaSpider(semilla);
        var estado1 = inicializador1.obtenerEstado();
        var estado2 = inicializador2.obtenerEstado();
        var desc1 = estado1.generarDescripcion();
        var desc2 = estado2.generarDescripcion();
        verificarMismasDescripciones(desc1, desc2);
    }

    private static ArrayList<List<String>> generarDescripcionRandom() {
        var descripcion = new ArrayList<List<String>>();
        var mazo = new MazoCartas(5, 8, false);

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