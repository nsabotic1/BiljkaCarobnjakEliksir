package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BiljkaTest {
    @Test
    void toStringTest() {
        Biljka biljka = new AromaticnaBiljka("Aromaticna1", 5);
        assertEquals("Aroma: Aromaticna1 - 5", biljka.toString());
    }

    @Test
    void beanTest() {
        Biljka biljka1 = new AromaticnaBiljka("Aromaticna1", 5);
        Biljka biljka2 = new LjekovitaBiljka("Ljekovita1", 8);
        Biljka biljka3 = new OtrovnaBiljka("Otrovna1", 12);
        biljka1.setNaziv("A1");
        biljka2.setJacina(100);
        biljka3.setNaziv("Otrovna77");
        String result = "Aroma: A1 - 5" + "\n" +
                "Lijek: Ljekovita1 - 100" + "\n" +
                "Otrov: Otrovna77 - 12";
        assertAll(() -> {
            assertEquals("A1", biljka1.getNaziv());
            assertEquals(100, biljka2.getJacina());
            assertEquals("Otrovna77", biljka3.getNaziv());
            assertEquals(result, biljka1 + "\n" + biljka2 + "\n" + biljka3);
        });
    }

    @Test
    void invalidArgumentTest() {
        assertAll(
                () -> {
                    assertThrows(IllegalArgumentException.class, () -> new AromaticnaBiljka("", -1));
                },
                () -> {
                    try {
                        new AromaticnaBiljka("", -1);
                        fail();
                    } catch (Exception exception) {
                        assertEquals("Jacina mora biti nenegativan cijeli broj", exception.getMessage());
                    }
                }
        );
    }
}