package SolitarioBase;

public class FabricaCartas {
    public static Card generarCarta(String descripcion) {
        //Descripcion = "<Numero>-<Palo>-<EstaOculta>" / "ordinal,ordinal, bool" Ej: "11-0-false"
        var elementosCarta = descripcion.split("-");
        var num = NumeroCarta.values()[(Integer.parseInt(elementosCarta[0]))];
        var palo = Palo.values()[Integer.parseInt(elementosCarta[1])];
        var estaOculta = Boolean.parseBoolean(elementosCarta[2]);
        return new Carta(num, palo, estaOculta);
    }
}

