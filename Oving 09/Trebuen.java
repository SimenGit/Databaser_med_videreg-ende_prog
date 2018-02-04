import java.io.Serializable;

public abstract class Trebuen {

    private final String tribunenavn;
    private final int kapasitet;
    private final int pris;

    public Trebuen(String tribunenavn, int kapasitet, int pris) {
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

    public int getPris() {
        return pris;
    }

    abstract public int finnAntallSolgte();

    public abstract double finnInntekt();


    abstract public Billett[] kjopBilletter(int antallBilletter);


    abstract public Billett[] kjopBillett(String[] navneliste);

    public String toStringAlle() {
        return "Navn: " + getTribunenavn() + "\nKapasitet: " + getKapasitet() + "\nAntall solgte billetter: " + finnAntallSolgte() + "\nInntekt: " + finnInntekt();
    }
    public String toString() {
        return "Tribunenavn: " + getTribunenavn() + ",  " + "Pris: " + getPris();
    }


}


class Staa extends Trebuen implements Serializable {

    int antSolgte;
    public Staa(String tribunenavn, int kapasitet, int pris) {
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


class Sitte extends Trebuen implements Serializable {

    int[] antOpptatt;
    public Sitte(String tribunenavn, int kapasitet, int pris, int antRader) {
        super(tribunenavn, kapasitet, pris);
        antOpptatt = new int[antRader];
    }

    @Override
    public int finnAntallSolgte() {
        int res = 0;
        for(int i = 0; i < antOpptatt.length; i++) {
            res += antOpptatt[i];
        }
        return res;
    }

    private int finnLedigRad(int antBilletter) {
        int kapasitetPerRad = getKapasitet() / antOpptatt.length;

        if(kapasitetPerRad <= antBilletter) {
            return -1;
        }
        for(int i = 0; i < antOpptatt.length; i++) {
            if((antOpptatt[i] < kapasitetPerRad) && (kapasitetPerRad - antOpptatt[i]) >= antBilletter) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public double finnInntekt() {
        return  finnAntallSolgte() * getPris();
    }

    @Override
    public Billett[] kjopBilletter(int antallBilletter) {
        Billett[] billettListe = new Billett[antallBilletter];
        int ledig = finnLedigRad(antallBilletter);
        if((ledig != -1) && ((getKapasitet() - finnAntallSolgte()) >= antallBilletter)) {
            for(int i = 0; i < antallBilletter; i++) {
                int ledigRad = finnLedigRad(antallBilletter);
                antOpptatt[ledigRad]++;
                SitteplassBillett billetten = new SitteplassBillett(getTribunenavn(), getPris(), antOpptatt[ledigRad], ledigRad);
                billettListe[i] = billetten; }
        } else {
            return null;
        }
        return billettListe;
    }

    @Override
    public Billett[] kjopBillett(String[] billettListe) {
        return kjopBilletter(billettListe.length);
    }

}


class VIP extends Sitte implements Serializable{

    String[][] tilskuer;
    public VIP(String tribunenavn, int kapasitet, int pris, int rader) {
        super(tribunenavn, kapasitet, pris, rader);
        tilskuer = new String[rader][getKapasitet()/rader]; // [rad][plasser per rad]
    }

    @Override
    public Billett[] kjopBilletter(int antallBilletter) {
        return null;
    }

    public int[] finnLedig(int antPlasser) {

        int startplass = 100;
        int[] plassene = new int[2];
        for(int i = 0; i < tilskuer.length; i++) {
            int counter = tilskuer[i].length;
            for(int j = 0; j < antPlasser; j++) {
                if(tilskuer[i][j] == null) {
                    counter--;                      // teller ned til alle har fått en plass. dersom det ikke counter blir under 0 på en rad, sjekker den neste rad.
                    if(startplass == 100) {
                        startplass = j;
                        plassene[1] = startplass;
                    }
                }
            }
            if(counter >= 0) {
                plassene[0] = i;
                return plassene;                        // plassene[0] = rad.                  //plassene[1] = plass
            }
        }
        return null;
    }

    public int finnAntallSolgte() {
        int sold = 0;
        for(int i = 0; i < tilskuer.length; i++) {
            for(int j = 0; j < tilskuer[i].length; j++) {
                if(tilskuer[i][j] != null) {
                    sold++;
                }
            }
        }
        return sold;
    }


    public Billett[] kjopBillett(String[] navneliste) {

        Billett[] billettListe = new Billett[navneliste.length];
        if(getKapasitet() - finnAntallSolgte() >= navneliste.length) {
            for(int i = 0; i < navneliste.length; i++) {
                int[] ledigRad = finnLedig(navneliste.length);
                SitteplassBillett nyVIPbillett = new SitteplassBillett(getTribunenavn(), getPris(), ledigRad[0], ledigRad[1]);  // navn, pris, rad, plass
                billettListe[i] = nyVIPbillett;
                tilskuer[ledigRad[0]][ledigRad[1]] = navneliste[i];
            }
            return billettListe;
        }
        return null;
    }

    public double finnInntekt() {
        return finnAntallSolgte() * getPris();
    }
}

