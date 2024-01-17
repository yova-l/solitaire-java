package SolitarioBase;

import java.io.Serializable;

public class Carta implements Card, Serializable {
    private final NumeroCarta numero;
    private final Palo palo;
    private boolean estaOculta;

    public Carta(NumeroCarta numero, Palo palo, boolean estaOculta) {
        this.numero = numero;
        this.palo = palo;
        this.estaOculta = estaOculta;
    }

    @Override
    public NumeroCarta verNum() { return numero; }

    @Override
    public Color verColor() {
        return palo.getColor();
    }

    @Override
    public Palo verPalo() { return palo; }

    @Override
    public boolean estaOculta() {
        return estaOculta;
    }

    @Override
    public boolean esA() { return numero == NumeroCarta.A;  }

    @Override
    public boolean esK() { return numero == NumeroCarta.K;  }

    @Override
    public void voltear() { estaOculta = !estaOculta; }

    @Override
    public boolean esMenorConsecutivo(Card cartaDebajo) {
        var valorTop = this.verNum().ordinal();
        var valorDebajo = cartaDebajo.verNum().ordinal();
        return valorTop == ( valorDebajo + 1);
    }

    @Override
    public boolean esIntercalado(Card otraCarta) { return this.palo.getColor() != otraCarta.verPalo().getColor(); }

    @Override
    public boolean esMismoPalo(Card otraCarta) { return this.palo == otraCarta.verPalo(); }

    @Override
    public String generarDescripcion() { return String.format("%d-%d-%b",numero.ordinal(), palo.ordinal(),estaOculta); }
}
