package SolitarioBase;

public enum TipoSolitario {
    KLONDIKE,
    SPIDER;

    public boolean esKlondike() { return this.ordinal() == 0; }

    public boolean esSpider() { return this.ordinal() == 1; }
}
