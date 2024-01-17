package SolitarioBase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Estado implements Serializable {
    protected final List<CardsStack> pilasCartas;
    private final TipoSolitario tipoSolitaire;

    public Estado(List<CardsStack> pilasCartas, TipoSolitario type) {
        this.pilasCartas = pilasCartas;
        this.tipoSolitaire = type;
    }

    public TipoSolitario getTipoSolitaire() { return tipoSolitaire; }

    public void serializar(OutputStream os) throws IOException {
        ObjectOutputStream objectOutStream = new ObjectOutputStream(os);
        objectOutStream.writeObject(this);
        objectOutStream.flush();
    }

    public static Estado deserializar(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInStream = new ObjectInputStream(is);
        return (Estado) objectInStream.readObject();
    }

    public void actualizarEstado(Movimiento movimiento) {
        // PRECONDICION: el movimiento a realizar ES VALIDO
        var origen = movimiento.getOrigen();
        var destino = movimiento.getDestino();
        var pilaOriIndex = origen.ordinal();
        var pilaDesIndex = destino.ordinal();
        var pilaOrigen = pilasCartas.get(pilaOriIndex);
        var pilaDestino = pilasCartas.get(pilaDesIndex);
        var cartaOrigen = movimiento.getCartaOrigen();
        var posOrigen = movimiento.getPosOrigen();
        // Caso borde: lo checkea cada implementacion...
        var movidas = pilaOrigen.obtenerCascada(cartaOrigen, posOrigen);
        pilaDestino.appendearCascada(movidas);
    }

    public List<List<String>> generarDescripcion() {
        var res = new ArrayList<List<String>>();
        for (CardsStack pila : pilasCartas) {
            res.addLast(pila.generarDescripcion());
        }
        return res;
    }

    public abstract boolean esPartidaGanada();
}
