package Spider;

import SolitarioBase.*;

import java.io.Serializable;
import java.util.List;

public class EstadoSpider extends Estado implements Serializable {

    public EstadoSpider(List<CardsStack> pilasCartas) { super(pilasCartas, TipoSolitario.SPIDER); }

    @Override
    public boolean esPartidaGanada(){
        for (CardsStack pila : pilasCartas) { if (!pila.estaVacia()) { return false; } }
        return true;
    }

    private boolean quitarEscaleraCompleta() {
        // Quita la primera escalera que encuentre si es que encuentra.
        var target = FabricaCartas.generarCarta(String.format("%d-%d-%b", NumeroCarta.K.ordinal(), Palo.PICA.ordinal(), false));
        for (int i = 1; i < 11; i++) {
            var pila = (PilaJuegoSolitario) pilasCartas.get(i);
            var listaCartas = pila.getListaVisibles();
            var counter = 0;
            for (int j = 0; j < (listaCartas.size()-1); j++ ) {
                var cartaA = listaCartas.get(j);
                var cartaB = listaCartas.get(j+1);
                if (cartaA.esMenorConsecutivo(cartaB)) {
                    counter++;
                    if (counter == 12) {
                        //Siempre que una pila se completa buscamos la cascada desde la K en adelante y la flusheamos
                        pila.obtenerCascada(target, -1);
                        return true;
                    }
                    continue;
                }
                counter = 0;
            }
        }
        return false;
    }

    public boolean pozoEstaVacio() { return pilasCartas.get(0).estaVacia(); }

    public boolean estaBloqueada(ZonaSolitario zonaOri, int posOrigen) {
        // PRE: la zona spider ES UNA PILAJUEGO
        var pila = (PilaJuegoSolitario) pilasCartas.get(zonaOri.ordinal());
        var listaCartas = pila.getListaVisibles();

        posOrigen -= pila.getListaOcultas().size();

        for (int i = posOrigen; i < (listaCartas.size()-1); i++) {
            var cartaA = listaCartas.get(i);
            var cartaB = listaCartas.get(i+1);
            var bMenorQueA = cartaA.esMenorConsecutivo(cartaB);
            if (!bMenorQueA) { return true; }
        }
        return false;
    }

    @Override
    public void actualizarEstado(Movimiento movimiento) {
        // PRECONDICION: el movimiento a realizar ES VÁLIDO
        var origen = (ZonaSpider) movimiento.getOrigen();
        var destino = (ZonaSpider) movimiento.getDestino();

        if (JuegoSpider.quiereCartasPozo(origen, destino)) {
            for (int i = 0; i < 10; i++) {
                // obtengo un arreglo con una carta del pozo y se la apilo forzadamente a las pilaJuego
                var cartaARepartir = pilasCartas.get(ZonaSpider.POZO.ordinal()).obtenerCascada(null, -1);
                pilasCartas.get(i+1).appendearCascada(cartaARepartir);
            }
        }
        else {
            super.actualizarEstado(movimiento);
        }
        boolean escaleraQuitada = quitarEscaleraCompleta();
        while (escaleraQuitada) { escaleraQuitada = quitarEscaleraCompleta(); }
    }
}

