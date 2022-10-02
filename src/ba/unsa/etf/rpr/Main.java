package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
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
        System.out.println(biljke.size()); //iznos treba biti 120
        System.out.println(biljke.get(0).getNaziv().contains("A1")); //provjera da li je dodana aromaticna biljka
    }
}
