package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Objects;

public class Eliksir implements Konzumabilno, Comparable<Eliksir> {
    private  TipEliksira tip;
    private  String naziv;
    private final int jacina;
    private final ArrayList<Biljka> biljke;

    public int compareTo(Eliksir eliksir2) {
        return getNaziv().compareTo(eliksir2.getNaziv());
    }
    enum TipEliksira{BOOSTER, LIJEK, OTROV, PARFEM};


    public Eliksir(String naziv, ArrayList<Biljka> biljke) {
        if(naziv.isEmpty()) throw new IllegalArgumentException("Ne moze se kreirati eliksir bez naziva");
        if(biljke.isEmpty()) throw new IllegalArgumentException("Ne moze se kreirati eliksir bez biljaka");
        this.naziv = naziv;
        this.biljke = biljke;
        int jArome=0,jOtrovne=0,jLijeka=0;
        for(Biljka b:biljke){
            if(b.getClass().getSimpleName().equals("AromaticnaBiljka")) jArome+=b.getJacina();
            if(b.getClass().getSimpleName().equals("LjekovitaBiljka")) jLijeka+=b.getJacina();
            if(b.getClass().getSimpleName().equals("OtrovnaBiljka")) jOtrovne+=b.getJacina();

        }
        if(jArome==jLijeka && jArome==jOtrovne){
            jacina=jArome+jLijeka+jOtrovne;
            tip= TipEliksira.BOOSTER;
        }
        else if(jArome>jLijeka && jArome>jOtrovne){
            jacina=jArome;
            tip= TipEliksira.PARFEM;
        }
        else if(jOtrovne>jLijeka && jOtrovne>jArome){
            jacina=jOtrovne;
            tip= TipEliksira.OTROV;
        }
        else {
            jacina = jLijeka;
            tip= TipEliksira.LIJEK;
        }
    }

    public String getNaziv() {
        return naziv;
    }

    public int getJacina() {
        return jacina;
    }

    public ArrayList<Biljka> getBiljke() {
        return biljke;
    }


    public TipEliksira getTipEliksira(){
     return tip;

    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Eliksir eliksir = (Eliksir) o;

        return Objects.equals(naziv, eliksir.naziv);
    }


    @Override
    public String konzumiraj() {
        String pomocni=new String();
        if(tip.toString().equals("BOOSTER")) pomocni= "{Sve +"+ getJacina()+"}";
        if(tip.toString().equals("LIJEK")) pomocni= "{Zdravlje +"+getJacina()+"}";
        if(tip.toString().equals("OTROV")) pomocni= "{Zdravlje -"+getJacina()+"}";
        if(tip.toString().equals("PARFEM")) pomocni="{Miris +"+getJacina()+"}";


        return pomocni;
    }


}
