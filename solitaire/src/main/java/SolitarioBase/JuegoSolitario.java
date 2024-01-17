package SolitarioBase;

public abstract class JuegoSolitario {
    protected Estado estado;

    public JuegoSolitario(Estado estado){ this.estado = estado; }

    protected abstract boolean esMovimientoValido(Movimiento movimiento);

    public void hacerMovimiento(Movimiento movimiento) { if (esMovimientoValido(movimiento)) { estado.actualizarEstado(movimiento); } }

    public Estado getEstado() { return this.estado; }

    public void resetEstado() { estado = null; }

    public boolean juegoGanado() { return estado.esPartidaGanada(); }

    public static boolean sonVisibles(Card cartaOrigen, Card cartaDestino) { return !cartaOrigen.estaOculta() || !cartaDestino.estaOculta(); }
}
