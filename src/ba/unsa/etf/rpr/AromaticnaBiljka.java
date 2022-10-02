package ba.unsa.etf.rpr;

public class AromaticnaBiljka extends Biljka{

    public AromaticnaBiljka(String aromaticna1, int i) {
        super.setNaziv(aromaticna1);
        super.setJacina(i);
    }

    @Override
    public String toString() {
        String pomocni="Aroma: "+ getNaziv()+ " - " +getJacina();
        return pomocni;
    }

    @Override
    public String konzumiraj() {
        //AROMA +100]
        String pomocni="["+"AROMA"+" +"+getJacina()+"]";
        return pomocni;
    }

}
