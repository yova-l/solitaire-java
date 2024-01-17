package SolitarioBase;

public class Movimiento {
    private ZonaSolitario origen;
    private ZonaSolitario destino;
    private Card cartaOrigen;
    private Card cartaDestino;
    private boolean esCascada;
    private int posOrigen;
    private int posDestino;

    public Movimiento(ZonaSolitario origen, ZonaSolitario destino, Card cartaOrigen, Card cartaDestino, boolean esCascada, int posOrigen, int posDestino) {
        this.cartaDestino = cartaDestino;
        this.cartaOrigen = cartaOrigen;
        this.origen = origen;
        this.destino = destino;
        this.esCascada = esCascada;
        this.posOrigen = posOrigen;
        this.posDestino = posDestino;
    }

    public Movimiento() {}

    public ZonaSolitario getOrigen() { return origen; }
    public ZonaSolitario getDestino() { return destino; }
    public Card getCartaOrigen() { return cartaOrigen; }
    public Card getCartaDestino() { return cartaDestino; }
    public boolean getEsCascada() { return esCascada;}
    public int getPosOrigen() { return posOrigen; }
    public int getPosDestino() { return  posDestino; }
    public void setOrigen (ZonaSolitario origen) { this.origen = origen; }
    public void setDestino (ZonaSolitario destino) { this.destino = destino; }
    public void setCartaOrigen (Card cartaOrigen) { this.cartaOrigen = cartaOrigen; }
    public void setCartaDestino (Card cartaDestino) { this.cartaDestino = cartaDestino; }
    public void setEsCascada (boolean esCascada) { this.esCascada = esCascada; }
    public void setPosOrigen (int posOrigen) { this.posOrigen = posOrigen; }
    public void setPosDestino (int posDestino) { this.posDestino = posDestino; }

    public boolean isPilaOrigenSet() { return (this.origen != null); }
}