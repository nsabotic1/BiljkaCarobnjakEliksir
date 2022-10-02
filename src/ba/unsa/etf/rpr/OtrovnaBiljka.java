package ba.unsa.etf.rpr;

public class OtrovnaBiljka extends Biljka{

    public OtrovnaBiljka(String otrovna1, int i) {
        super.setNaziv(otrovna1);
        super.setJacina(i);
    }

    @Override
    public String toString() {
        String pomocni="Otrov: "+ getNaziv()+ " - " +getJacina();

        return pomocni;
    }
    @Override
    public String konzumiraj() {
        //AROMA +100]
        String pomocni="["+"ZDRAVLJE"+" -"+getJacina()+"]";
        return pomocni;
    }
}
