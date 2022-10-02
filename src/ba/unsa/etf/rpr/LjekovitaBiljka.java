package ba.unsa.etf.rpr;

public class LjekovitaBiljka extends Biljka{

    public LjekovitaBiljka(String ljekovita1, int i) {

        super.setNaziv(ljekovita1);
        super.setJacina(i);
    }

    @Override
    public String toString() {
        String pomocni="Lijek: "+ getNaziv()+ " - " +getJacina();

        return pomocni;
    }
    @Override
    public String konzumiraj() {
        //AROMA +100]
        String pomocni="["+"ZDRAVLJE"+" +"+getJacina()+"]";
        return pomocni;
    }


}
