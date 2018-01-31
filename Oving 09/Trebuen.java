public abstract class Trebuen {

    private final String tribunenavn;
    private final int kapasitet;
    private final double pris;

    public Trebuen(String tribunenavn, int kapasitet, double pris) {
        this.tribunenavn = tribunenavn;
        this.kapasitet = kapasitet;
        this.pris = pris;
    }

    public String getTribunenavn() {
        return tribunenavn;
    }

    public int getKapasitet() {
        return kapasitet;
    }

    public double getPris() {
        return pris;
    }

    abstract public int finnAntallSolgte();

    public double finnInntekt() {
        return (finnAntallSolgte() * getPris());
    }

    abstract public Billett[] kjopBilletter(int antallBilletter);

    abstract public Billett[] kjopBillett(String[] navneliste);

    public String toString() {
        return "Navn: " + getTribunenavn() + "\nKapasitet: " + getKapasitet() + "\nAntall solgte billetter: " + finnAntallSolgte() + "\nInntekt: " + finnInntekt();
    }
}


class Staa extends Trebuen {

    int antSolgte;
    public Staa(String tribunenavn, int kapasitet, double pris) {
        super(tribunenavn, kapasitet, pris);
    }

    @Override
    public int finnAntallSolgte() {
        return antSolgte;
    }

    @Override
    public double finnInntekt() {
        return antSolgte * getPris();
    }

    @Override
    public Billett[] kjopBilletter(int antallBilletter) {
        Billett[] billettListe = new Billett[antallBilletter];
        if(getKapasitet() - antSolgte >= antallBilletter) {
            for(int i = 0; i < billettListe.length; i++) {
                StaaplassBillett plass = new StaaplassBillett(getTribunenavn(), getPris());
                billettListe[i] = plass;
                antSolgte++;
            }
        }else{
            return null;
        }
        return billettListe;
    }

    @Override
    public Billett[] kjopBillett(String[] billettListe) {
        return kjopBilletter(billettListe.length);

    }
}


class Sitte extends Trebuen {

    int[] antOpptatt;
    public Sitte(String tribunenavn, int kapasitet, double pris) {
        super(tribunenavn, kapasitet, pris);
    }

}

class VIP extends Trebuen {

    String[][] tilskuer;
    public VIP(String tribunenavn, int kapasitet, double pris) {
        super(tribunenavn, kapasitet, pris);
    }

}

/* public class Staa extends Tribune {

    private int antSolgteBilletter = 0;

    public Staa(String tribunenavn, int kapasitet, double pris){
        super(tribunenavn, kapasitet, pris);
    }

    @Override
    public int finnAntallSolgteBilletter() {
        return antSolgteBilletter;
    }

    @Override
    public double finnInntekt() {
        return (antSolgteBilletter * getPris());
    }

    @Override
    public Billett[] kjopBilletter(int antallBilletter) {
        Billett[] billettListe = new Billett[antallBilletter];
        if((getKapasitet() - antSolgteBilletter) >= antallBilletter) {
            for(int i = 0; i < antallBilletter; i++) {
                StaaplassBillett staaBillett = new StaaplassBillett(getTribunenavn(), getPris());
                billettListe[i] = staaBillett;
                antSolgteBilletter++;
            }
        } else {
            return null;
        }
        return billettListe;
    }

    @Override
    public Billett[] kjopBilletter(String[] navneliste) {
        return kjopBilletter(navneliste.length);
    }
} */
