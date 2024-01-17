package Klondike;

import SolitarioBase.ZonaSolitario;

public enum ZonaKlondike implements ZonaSolitario {
    POZO1 (24, 0),
    POZO2 (0, 0),
    PILARESULTADO1 (0, 0),
    PILARESULTADO2 (0, 0),
    PILARESULTADO3 (0, 0),
    PILARESULTADO4 (0, 0),
    PILAJUEGO1 (0, 1),
    PILAJUEGO2 (1, 1),
    PILAJUEGO3 (2, 1),
    PILAJUEGO4 (3, 1),
    PILAJUEGO5 (4, 1),
    PILAJUEGO6 (5, 1),
    PILAJUEGO7 (6, 1);

    private final int defaultOcultas;
    private final int defaultVisibles;

    ZonaKlondike(int defaultOcultas, int defaultVisibles) {
        this.defaultOcultas = defaultOcultas;
        this.defaultVisibles = defaultVisibles;
    }

    @Override
    public int getDefaultOcultas() { return defaultOcultas; }

    @Override
    public int getDefaultVisibles() { return defaultVisibles; }

    public boolean esPilaPozo1() { return this.ordinal() == 0; }

    public boolean esPilaPozo2() { return this.ordinal() == 1; }

    public boolean esPilaResultado() { return this.ordinal() > 1 && this.ordinal() < 6; }

    public boolean esPilaJuego() {  return this.ordinal() > 5; }
}
