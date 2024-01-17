import Klondike.InicializadorPartidaKlondike;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InicializadorPartidaKlondikeTest {
    @Test
    public void testInicializarConDesc() {
        var descripcion = generarDescripcionRandom();
        var inicializador1 = new InicializadorPartidaKlondike(descripcion);
        var estado1 = inicializador1.obtenerEstado();
        var descFromEstado = estado1.generarDescripcion();
        verificarMismasDescripciones(descripcion, descFromEstado);
    }
    @Test
    public void testInicializarConSemilla() {
        var semilla = 8;
        var inicializador1 = new InicializadorPartidaKlondike(semilla);
        var inicializador2 = new InicializadorPartidaKlondike(semilla);
        var estado1 = inicializador1.obtenerEstado();
        var estado2 = inicializador2.obtenerEstado();
        var desc1 = estado1.generarDescripcion();
        var desc2 = estado2.generarDescripcion();
        verificarMismasDescripciones(desc1, desc2);
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