package ba.unsa.etf.rpr;

import java.util.Objects;

public abstract class Biljka implements Konzumabilno, Comparable<Biljka>{
    private String naziv;
    private int jacina;
   //posto nije trazen konstruktor, necemo ga stavljati i u naslijedjenim klasama se onda radi
    //drugacije u njihovom konstruktoru


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getJacina() {
        return jacina;
    }

    public void setJacina(int jacina) {
        if(jacina<0) throw new IllegalArgumentException ("Jacina mora biti nenegativan cijeli broj");
        this.jacina = jacina;
    }
    public abstract String toString();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Biljka biljka = (Biljka) o;

        return Objects.equals(naziv, biljka.naziv);
    }

    @Override
    public int compareTo(Biljka o) {
        return getNaziv().compareTo(o.getNaziv());
    }

}
