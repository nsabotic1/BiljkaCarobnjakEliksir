package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CarobnjakTest {
    @Test
    void dodajBiljkeTest() {
        Carobnjak carobnjak = new Carobnjak();
        for (int i = 10; i < 100; i++) {
            carobnjak.dodajBiljku(new AromaticnaBiljka("A1" + i, i));
        }
        for (int i = 10; i < 20; i++) {
            carobnjak.dodajBiljku(new LjekovitaBiljka("LJ1" + i, i));
        }
        for (int i = 10; i < 30; i++) {
            carobnjak.dodajBiljku(new OtrovnaBiljka("O1" + i, i));
        }
        ArrayList<Biljka> biljke = carobnjak.dajBiljke(b -> true);
        assertAll(() -> {
                    assertEquals(120, biljke.size());
                },
                () -> {
                    assertTrue(biljke.get(0).getNaziv().contains("A"));
                }
        );
    }

    @Test
    void konzumirajEliksirTest() {
        Carobnjak carobnjak = new Carobnjak();
        try {
           Eliksir eliksir1 = new Eliksir("E1", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 1))));
            Eliksir eliksir2 = new Eliksir("E2", new ArrayList<>(List.of(new LjekovitaBiljka("LJ2", 2))));
            Eliksir eliksir3 = new Eliksir("E3", new ArrayList<>(List.of(new OtrovnaBiljka("O3", 3))));
            carobnjak.dodajEliksir(eliksir1);
            carobnjak.dodajEliksir(eliksir2);
            carobnjak.dodajEliksir(eliksir3);

            String result = "";
            result += carobnjak.konzumirajEliksir(eliksir1);
            result += carobnjak.konzumirajEliksir(eliksir2);
            result += carobnjak.konzumirajEliksir(eliksir3);

            assertEquals("{Miris +1}{Zdravlje +2}{Zdravlje -3}", result);
        } catch (ZabranjenEliksirIzuzetak e) {
            System.out.println("Baca se izuzetak kada ne bi trebao");
            fail();
        }
    }

    @Test
    void konzumirajEliksireTest() {
        Carobnjak carobnjak = new Carobnjak();
        try {
            Eliksir eliksir1 = new Eliksir("E1", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 1))));
            Eliksir eliksir2 = new Eliksir("E2", new ArrayList<>(List.of(new LjekovitaBiljka("LJ2", 2))));
            Eliksir eliksir3 = new Eliksir("E3", new ArrayList<>(List.of(new OtrovnaBiljka("O3", 3))));
            carobnjak.dodajEliksir(eliksir1);
            carobnjak.dodajEliksir(eliksir2);
            carobnjak.dodajEliksir(eliksir3);
            ArrayList<Eliksir> eliksiri = new ArrayList<>(List.of(eliksir1, eliksir2, eliksir3));
            String result = carobnjak.konzumirajEliksire(eliksiri);
            assertEquals("{Miris +1}{Zdravlje +2}{Zdravlje -3}", result);
        } catch (ZabranjenEliksirIzuzetak e) {
            System.out.println("Baca se izuzetak kada ne bi trebao");
            fail();
        }
    }

   @Test
    void napraviEliksirTest() {
        try {
            Carobnjak carobnjak = new Carobnjak();
            for (int i = 10; i < 20; i++) {
                carobnjak.dodajBiljku(new AromaticnaBiljka("A1" + i, i));
            }
            for (int i = 10; i < 20; i++) {
                carobnjak.dodajBiljku(new LjekovitaBiljka("LJ1" + i, i));
            }
            for (int i = 10; i < 20; i++) {
                carobnjak.dodajBiljku(new OtrovnaBiljka("O1" + i, i));
            }
            carobnjak.napraviEliksir("TOP", e -> true);
            long brojBiljaka = carobnjak.dajBiljke(b -> true).size();
            long brojEliksira = carobnjak.dajEliksire(e -> true).size();
            assertAll(
                    () -> {
                        assertEquals(0, brojBiljaka);
                    },
                    () -> {
                        assertEquals(1, brojEliksira);
                    },
                    () -> {
                        assertEquals(Eliksir.TipEliksira.BOOSTER, carobnjak.dajEliksire(e -> true).get(0).getTipEliksira());
                    }
            );
        } catch (ZabranjenEliksirIzuzetak e) {
            System.out.println("Baca se izuzetak kada ne bi trebao");
            fail();
        }
    }

    @Test
    void dajEliksirePoNazivuTest() {
        Carobnjak carobnjak = new Carobnjak();
        char slovo = 'O';
        for (int i = 1; i < 10; i++) {
            try {
                carobnjak.dodajEliksir(new Eliksir(slovo + "T", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 10)))));
                slovo--;
            } catch (ZabranjenEliksirIzuzetak e) {
                System.out.println("Svi eliksiri su uredu, madjutim, baci se izuzetak");
                fail();
            }
        }
        List<Eliksir> eliksiri = carobnjak.dajEliksirePoNazivuAbecedno().stream().toList();
        String result = "";
        for (Eliksir eliksir : eliksiri) {
            result += eliksir.getNaziv() + "\n";
        }
        assertEquals("GT" + "\n" +
                "HT" + "\n" +
                "IT" + "\n" +
                "JT" + "\n" +
                "KT" + "\n" +
                "LT" + "\n" +
                "MT" + "\n" +
                "NT" + "\n" +
                "OT" + "\n", result);
    }

    @Test
    void dajKoktelTest() {
        Carobnjak carobnjak = new Carobnjak();
        char slovo = 'O';
        for (int i = 1; i < 10; i++) {
            try {
                carobnjak.dodajEliksir(new Eliksir(slovo + "TA", new ArrayList<>(List.of(new AromaticnaBiljka("A1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TLJ", new ArrayList<>(List.of(new LjekovitaBiljka("LJ1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TO", new ArrayList<>(List.of(new OtrovnaBiljka("O1", i)))));
                slovo--;
            } catch (ZabranjenEliksirIzuzetak e) {
                System.out.println("Svi eliksiri su uredu, madjutim, baci se izuzetak");
                fail();
            }
        }
        Eliksir koktel = carobnjak.dajKoktel("koktel1", eliksir -> eliksir.getJacina() % 2 == 0 || eliksir.getJacina() % 3 == 0);
        long brojEliksira = carobnjak.dajEliksire(eliksir -> true).size();
        boolean isKoktelThere = carobnjak.dajEliksire(eliksir -> true).contains(koktel);
        assertAll(() -> assertEquals(10, brojEliksira), () -> assertEquals("koktel1", koktel.getNaziv()), () -> assertTrue(isKoktelThere));
    }

    @Test
    void dajEliksirePoTipovimaTest() {
        Carobnjak carobnjak = new Carobnjak();
        char slovo = 'O';
        for (int i = 1; i < 10; i++) {
            try {
                carobnjak.dodajEliksir(new Eliksir(slovo + "TA", new ArrayList<>(List.of(new AromaticnaBiljka("A1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TLJ", new ArrayList<>(List.of(new LjekovitaBiljka("LJ1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TO", new ArrayList<>(List.of(new OtrovnaBiljka("O1", i)))));
                slovo--;
            } catch (ZabranjenEliksirIzuzetak e) {
                System.out.println("Svi eliksiri su uredu, madjutim, baci se izuzetak");
                fail();
            }
        }
        carobnjak.dajKoktel("koktel", eliksir -> eliksir.getJacina() % 2 == 0 || eliksir.getJacina() % 3 == 0);
        Map<Eliksir.TipEliksira, ArrayList<Eliksir>> eliksiri = carobnjak.dajEliksirePoTipovima();
        assertAll(
                () -> assertEquals(3, eliksiri.get(Eliksir.TipEliksira.PARFEM).size()),
                () -> assertEquals(3, eliksiri.get(Eliksir.TipEliksira.LIJEK).size()),
                () -> assertEquals(3, eliksiri.get(Eliksir.TipEliksira.OTROV).size()),
                () -> assertEquals(1, eliksiri.get(Eliksir.TipEliksira.BOOSTER).size())
        );
    }

    @Test
    void napraviEliksireIzBiljakaTest() {
        ArrayList<Biljka> biljke = new ArrayList<>();
        for (int i = 10; i < 100; i++) {
            biljke.add(new AromaticnaBiljka("A1" + i, i));
        }
        for (int i = 10; i < 20; i++) {
            biljke.add(new LjekovitaBiljka("LJ1" + i, i));
        }
        for (int i = 10; i < 30; i++) {
            biljke.add(new OtrovnaBiljka("O1" + i, i));
        }
        ArrayList<Eliksir> eliksirs = Carobnjak.napraviEliksireIzBiljaka(biljke);
        assertAll(
                () -> assertEquals(120, eliksirs.size()),
                () -> assertEquals("A110", biljke.get(0).getNaziv()),
                () -> assertEquals("LJ118", biljke.get(98).getNaziv()),
                () -> assertEquals("O119", biljke.get(109).getNaziv())
        );
    }

    @Test
    void zabranjenEliksirTest() {
        Carobnjak carobnjak = new Carobnjak();
        char slovo = 'O';
        for (int i = 1; i < 10; i++) {
            try {
                carobnjak.dodajEliksir(new Eliksir(slovo + "TA", new ArrayList<>(List.of(new AromaticnaBiljka("A1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TLJ", new ArrayList<>(List.of(new LjekovitaBiljka("LJ1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TO", new ArrayList<>(List.of(new OtrovnaBiljka("O1", i)))));
                slovo--;
            } catch (ZabranjenEliksirIzuzetak e) {
                System.out.println("Svi eliksiri su uredu, madjutim, baci se izuzetak");
                fail();
            }
        }
        String message = "";
        try {
            carobnjak.dodajEliksir(new Eliksir("OTA", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 5)))));
            fail();
        } catch (Exception exception) {
            message += exception.getMessage();
        }
        String exceptionMessage = message;
        assertAll(
                () -> assertDoesNotThrow(() -> carobnjak.dodajEliksir(new Eliksir("zzz", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 1)))))),
                () -> assertThrows(ZabranjenEliksirIzuzetak.class,
                        () -> carobnjak.dodajEliksir(new Eliksir("OTA", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 5)))))
                ),
                () -> assertEquals("Taj Eliksir vec postoji", exceptionMessage)
        );
    }

    @Test
    void konzumirajNepostojeceTest() {
        Carobnjak carobnjak = new Carobnjak();
        char slovo = 'O';
        for (int i = 1; i < 10; i++) {
            try {
                carobnjak.dodajEliksir(new Eliksir(slovo + "TA", new ArrayList<>(List.of(new AromaticnaBiljka("A1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TLJ", new ArrayList<>(List.of(new LjekovitaBiljka("LJ1", i)))));
                carobnjak.dodajEliksir(new Eliksir(slovo + "TO", new ArrayList<>(List.of(new OtrovnaBiljka("O1", i)))));
                slovo--;
            } catch (ZabranjenEliksirIzuzetak e) {
                System.out.println("Svi eliksiri su uredu, madjutim, baci se izuzetak");
                fail();
            }
        }
        String message = "";
        try {
            carobnjak.konzumirajEliksir(new Eliksir("zzz", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 5)))));
            fail();
        } catch (Exception exception) {
            message += exception.getMessage();
        }
        String exceptionMessage = message;
        assertAll(
                () -> assertDoesNotThrow(() -> carobnjak.konzumirajEliksir(new Eliksir("OTA", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 1)))))),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> carobnjak.konzumirajEliksir(new Eliksir("zzz", new ArrayList<>(List.of(new AromaticnaBiljka("A1", 5)))))
                ),
                () -> assertEquals("Eliksir nema na stanju", exceptionMessage)
        );
    }

    @Test
    void konzumirajSveTest() {
        Carobnjak carobnjak = new Carobnjak();
        for (int i = 10; i < 14; i++) {
            Biljka biljka = new LjekovitaBiljka("LJ1" + i, i);
            Biljka biljka2 = new AromaticnaBiljka("AJ1" + i, i);
            Biljka biljka3 = new OtrovnaBiljka("OJ1" + i, i);
            carobnjak.dodajBiljku(biljka);
            carobnjak.dodajBiljku(biljka2);
            carobnjak.dodajBiljku(biljka3);
            try {
                carobnjak.dodajEliksir(new Eliksir("E" + i, new ArrayList<>(Collections.singletonList(biljka))));
            } catch (ZabranjenEliksirIzuzetak e) {
                fail();
            }
        }
        String result = carobnjak.konzumirajSve();
        assertEquals("[AROMA +10][AROMA +11][AROMA +12][AROMA +13][ZDRAVLJE +10][ZDRAVLJE +11][ZDRAVLJE +12]" +
                "[ZDRAVLJE +13][ZDRAVLJE -10][ZDRAVLJE -11][ZDRAVLJE -12][ZDRAVLJE -13]" +
                "{Zdravlje +10}{Zdravlje +11}{Zdravlje +12}{Zdravlje +13}", result);
    }

    @Test
    void konzumabilnoTest() {
        ArrayList<Konzumabilno> konzumabilnos = new ArrayList<>();
        Biljka biljka = new LjekovitaBiljka("B100", 100);
        Biljka biljka2 = new AromaticnaBiljka("A120", 90);
        Biljka biljka3 = new OtrovnaBiljka("OO11", 11);
        Eliksir eliksir = new Eliksir("E77", new ArrayList<>(Collections.singletonList(biljka2)));
        Eliksir eliksir1 = new Eliksir("E77", new ArrayList<>(List.of(
                biljka, biljka2, biljka3
        )));
        Eliksir eliksir2 = new Eliksir("B30", new ArrayList<>(List.of(
                new LjekovitaBiljka("LJ10", 10),
                new OtrovnaBiljka("O10", 10),
                new AromaticnaBiljka("A10", 10)
        )));
        konzumabilnos.add(biljka);
        konzumabilnos.add(biljka2);
        konzumabilnos.add(biljka3);
        konzumabilnos.add(eliksir);
        konzumabilnos.add(eliksir1);
        konzumabilnos.add(eliksir2);
        String result = konzumabilnos.stream().map(Konzumabilno::konzumiraj).collect(Collectors.joining());
        assertEquals("[ZDRAVLJE +100][AROMA +90][ZDRAVLJE -11]{Miris +90}{Zdravlje +100}{Sve +30}", result);
    }
}