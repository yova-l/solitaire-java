package Spider;

import SolitarioBase.ZonaSolitario;

public enum ZonaSpider implements ZonaSolitario {
    POZO (50,0),
    PILAJUEGO1 (5,1),
    PILAJUEGO2 (5,1),
    PILAJUEGO3 (5,1),
    PILAJUEGO4 (5,1),
    PILAJUEGO5 (4,1),
    PILAJUEGO6 (4,1),
    PILAJUEGO7 (4,1),
    PILAJUEGO8 (4,1),
    PILAJUEGO9 (4,1),
    PILAJUEGO10 (4,1);

    private final int defaultOcultas;
    private final int defaultVisibles;

    ZonaSpider(int defaultOcultas, int defaultVisibles) {
        this.defaultOcultas = defaultOcultas;
        this.defaultVisibles = defaultVisibles;
    }

    @Override
    public int getDefaultOcultas() { return defaultOcultas; }

    @Override
    public int getDefaultVisibles() { return defaultVisibles; }

    public boolean esPilaPozo() {  return this.ordinal() == 0; }

}
