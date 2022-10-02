package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliksirTest {

    @Test
    void simpleEliksirTest() {
        Eliksir eliksir = new Eliksir("E321", new ArrayList<>(Collections.singletonList(new AromaticnaBiljka("A44", 44))));
        assertAll(() -> {
                    assertEquals("E321", eliksir.getNaziv());
                },
                () -> {
                    assertEquals(44, eliksir.getJacina());
                },
                () -> {
                    assertEquals(Eliksir.TipEliksira.PARFEM, eliksir.getTipEliksira());
                }
        );
    }

    @Test
    void simpleEliksirKonzumirajTest() {
        Eliksir eliksir = new Eliksir("E321", new ArrayList<>(Collections.singletonList(new AromaticnaBiljka("A44", 44))));
        String result = eliksir.konzumiraj();
        assertEquals("{Miris +44}", result);
    }

    @Test
    void equalsTest() {
        Eliksir eliksir = new Eliksir("E321", new ArrayList<>(Collections.singletonList(new AromaticnaBiljka("A44", 44))));
        Eliksir eliksir2 = new Eliksir("E321", new ArrayList<>(Collections.singletonList(new OtrovnaBiljka("O3", 3))));
        Eliksir eliksir3 = new Eliksir("E322", new ArrayList<>(Collections.singletonList(new OtrovnaBiljka("O3", 3))));
        assertAll(() -> {
                    assertEquals(eliksir, eliksir2);
                },
                () -> {
                    assertNotEquals(eliksir2, eliksir3);
                }
        );
    }

    @Test
    void compareTest() {
        Eliksir eliksir = new Eliksir("E321", new ArrayList<>(Collections.singletonList(new AromaticnaBiljka("A44", 44))));
        Eliksir eliksir2 = new Eliksir("E321", new ArrayList<>(Collections.singletonList(new OtrovnaBiljka("O3", 3))));
        Eliksir eliksir3 = new Eliksir("E322", new ArrayList<>(Collections.singletonList(new OtrovnaBiljka("O3", 3))));
        assertAll(() -> {
                    assertEquals(0, eliksir.compareTo(eliksir2));
                },
                () -> {
                    assertTrue(eliksir2.compareTo(eliksir3) < 0);
                }
        );
    }

    @Test
    void invalidArgumentsTest() {
        assertAll(
                () -> {
                    assertDoesNotThrow(() -> {
                        new Eliksir("A", new ArrayList<>(List.of(new AromaticnaBiljka("A", 1))));
                    });
                },
                () -> {
                    assertThrows(IllegalArgumentException.class, () -> new Eliksir("S", new ArrayList<>()));
                },
                () -> {
                    assertThrows(IllegalArgumentException.class, () -> new Eliksir("", new ArrayList<>(
                                    List.of(new AromaticnaBiljka("A", 400)))));
                },
                () -> {
                    try {
                        new Eliksir("S", new ArrayList<>());
                        fail();
                    } catch (Exception exception) {
                        assertEquals("Ne moze se kreirati eliksir bez biljaka", exception.getMessage());
                    }
                },
                () -> {
                    try {
                        new Eliksir("", new ArrayList<>(
                                List.of(new AromaticnaBiljka("A", 400))));
                        fail();
                    } catch (Exception exception) {
                        assertEquals("Ne moze se kreirati eliksir bez naziva", exception.getMessage());
                    }
                }
        );
    }
}