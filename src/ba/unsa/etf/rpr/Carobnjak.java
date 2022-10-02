package ba.unsa.etf.rpr;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Carobnjak {
    private final ArrayList<Eliksir> eliksiri;
    private final ArrayList<Biljka> biljke;

    public Carobnjak() {
        eliksiri = new ArrayList<>();
        biljke = new ArrayList<>();
    }

    public static ArrayList<Eliksir> napraviEliksireIzBiljaka(ArrayList<Biljka> biljke) {
        return biljke.stream().map(biljka -> new Eliksir(biljka.getNaziv(), new ArrayList<>(Collections.singleton(biljka)))).collect(Collectors.toCollection(ArrayList::new));
    }

    public void dodajBiljku(Biljka biljka) {
        biljke.add(biljka);
    }

    public void dodajEliksir(Eliksir eliksir) throws ZabranjenEliksirIzuzetak {
        if (eliksiri.contains(eliksir)) {
            throw new ZabranjenEliksirIzuzetak("Taj Eliksir vec postoji");
        }
        eliksiri.add(eliksir);
    }

    public ArrayList<Biljka> dajBiljke(Predicate<Biljka> inclusionFunction) {
        return biljke.stream().filter(inclusionFunction).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Eliksir> dajEliksire(Predicate<Eliksir> inclusionFunction) {
        return eliksiri.stream().filter(inclusionFunction).collect(Collectors.toCollection(ArrayList::new));
    }

    public Eliksir napraviEliksir(String naziv, Predicate<Biljka> inclusionFunction) throws ZabranjenEliksirIzuzetak {
        ArrayList<Biljka> upotrebljeneBiljke = biljke.stream().filter(inclusionFunction).collect(Collectors.toCollection(ArrayList::new));
        Eliksir eliksir = new Eliksir(naziv, upotrebljeneBiljke);
        biljke.removeAll(upotrebljeneBiljke);
        dodajEliksir(eliksir);
        return eliksir;
    }

    public Set<Eliksir> dajEliksirePoNazivuAbecedno() {
        return new TreeSet<>(eliksiri);
    }

    public Eliksir dajKoktel(String naziv, Predicate<Eliksir> inclusionFunction) {
        ArrayList<Eliksir> odabraniEliksiri = eliksiri.stream()
                .filter(eliksir -> !eliksir.getTipEliksira().equals(Eliksir.TipEliksira.BOOSTER))
                .filter(inclusionFunction).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Biljka> biljke = odabraniEliksiri.stream().map(eliksir -> {
            if (eliksir.getTipEliksira().equals(Eliksir.TipEliksira.LIJEK)) {
                return new LjekovitaBiljka(eliksir.getNaziv(), eliksir.getJacina());
            } else if (eliksir.getTipEliksira().equals(Eliksir.TipEliksira.PARFEM)) {
                return new AromaticnaBiljka(eliksir.getNaziv(), eliksir.getJacina());
            } else {
                return new OtrovnaBiljka(eliksir.getNaziv(), eliksir.getJacina());
            }
        }).collect(Collectors.toCollection(ArrayList::new));
        Eliksir koktel = new Eliksir(naziv, biljke);
        eliksiri.removeAll(odabraniEliksiri);
        eliksiri.add(koktel);
        return koktel;
    }

    public Map<Eliksir.TipEliksira, ArrayList<Eliksir>> dajEliksirePoTipovima() {
        return Map.of(
                Eliksir.TipEliksira.LIJEK, new ArrayList<>(eliksiri.stream().filter(eliksir -> eliksir.getTipEliksira().equals(Eliksir.TipEliksira.LIJEK)).collect(Collectors.toList())),
                Eliksir.TipEliksira.OTROV, new ArrayList<>(eliksiri.stream().filter(eliksir -> eliksir.getTipEliksira().equals(Eliksir.TipEliksira.OTROV)).collect(Collectors.toList())),
                Eliksir.TipEliksira.PARFEM, new ArrayList<>(eliksiri.stream().filter(eliksir -> eliksir.getTipEliksira().equals(Eliksir.TipEliksira.PARFEM)).collect(Collectors.toList())),
                Eliksir.TipEliksira.BOOSTER, new ArrayList<>(eliksiri.stream().filter(eliksir -> eliksir.getTipEliksira().equals(Eliksir.TipEliksira.BOOSTER)).collect(Collectors.toList()))
        );
    }

    public String konzumirajEliksir(Eliksir eliksir) {
        if (!eliksiri.contains(eliksir)) {
            throw new IllegalArgumentException("Eliksir nema na stanju");
        }
        String result = eliksir.konzumiraj();
        eliksiri.remove(eliksir);
        return result;
    }

    public String konzumirajEliksire(ArrayList<Eliksir> eliksiri) {
        if (!eliksiri.containsAll(eliksiri)) {
            throw new IllegalArgumentException("Eliksir nema na stanju");
        }
        String result = eliksiri.stream().map(Eliksir::konzumiraj).collect(Collectors.joining());
        this.eliksiri.removeAll(eliksiri);
        return result;
    }

    public String konzumirajSve() {
        String result = biljke.stream().sorted().map(Biljka::konzumiraj).collect(Collectors.joining());
        biljke.clear();
        result += eliksiri.stream().sorted().map(Eliksir::konzumiraj).collect(Collectors.joining());
        eliksiri.clear();
        return result;
    }
}
