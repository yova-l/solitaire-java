package SolitarioBase;

import java.util.ArrayList;
import java.util.List;

public class Parsers {
    public static List<String> generarDescripcion(List<? extends Descriptable> listElem, List<String> res) {
        for (Descriptable elem : listElem) {
            var descElem = elem.generarDescripcion();
            res.addLast(descElem);
        }
        return res;
    }

    public static List<List<Card>> generarArrCards(List<List<String>> descripList, List<List<Card>> res) {
        for (List<String> pilaDesc : descripList) {
            var cartasPila = new ArrayList<Card>();
            for (String descripcionCarta : pilaDesc) {
                var carta = FabricaCartas.generarCarta(descripcionCarta);
                cartasPila.addLast(carta);
            }
            res.addLast(cartasPila);
        }
        return res;
    }
}
