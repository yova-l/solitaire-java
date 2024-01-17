package Spider;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZonaSpiderTest {

    @Test
    public void testingZones() {
        var misValues = ZonaSpider.values();
        ZonaSpider[] listaManual = {ZonaSpider.POZO, ZonaSpider.PILAJUEGO1, ZonaSpider.PILAJUEGO2, ZonaSpider.PILAJUEGO3,
                ZonaSpider.PILAJUEGO4, ZonaSpider.PILAJUEGO5, ZonaSpider.PILAJUEGO6, ZonaSpider.PILAJUEGO7, ZonaSpider.PILAJUEGO8,
                ZonaSpider.PILAJUEGO9, ZonaSpider.PILAJUEGO10};

        for (int i = 0; i < listaManual.length; i++) {
            assertEquals(misValues[i], listaManual[i]);
        }

        assertEquals(misValues[0].getDefaultOcultas(), 50);
        assertEquals(misValues[0].getDefaultVisibles(), 0);

        for (int i = 1; i < 5; i++) {
            assertEquals(misValues[i].getDefaultOcultas(), 5);
            assertEquals(misValues[i].getDefaultVisibles(), 1);
        }

        for (int i = 5; i < misValues.length; i++) {
            assertEquals(misValues[i].getDefaultOcultas(), 4);
            assertEquals(misValues[i].getDefaultVisibles(), 1);
        }
    }
}