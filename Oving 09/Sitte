class Sitte extends Tribune {

    private int[] antOpptatt;  // tabellstørrelse: antall rader

    public Sitte(String tribunenavn, int kapasitet, int pris, int rader) {
        super(tribunenavn, kapasitet, pris);
        antOpptatt = new int[rader];
    }


    @Override
    public int getAntSolgte() {
        int res = 0;
        for(int i = 0; i < antOpptatt.length; i++) {
            res += antOpptatt[i];
        }
        return res;
    }

    @Override
    public double getInntekt() {
        return getAntSolgte() * getPris();
    }

    /**
     * For alle tribunetyper gjelder at hvis man ønsker seg flere billetter enn det som kan effektueres,
     * får man ingen. På sittetribuner begrenses dette av at alle billetter som selges i en bestilling, skal være på samme rad
     */


    //Hjelpemetode for å finne ledig rad for ønsket antall billetter.
    private int finnLedigRad(int antallBilletter) {
        int kapasitetPerRad = getKapasitet() / antOpptatt.length;
        if(antallBilletter > kapasitetPerRad) {
            return -1;
        }
        for(int i = 0; i < antOpptatt.length; i++) {
            if(antOpptatt[i] <= kapasitetPerRad && antallBilletter <= kapasitetPerRad - antOpptatt[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Billett[] kjopBillett(int antBilletter) {
        Billett[] billettListe = new Billett[antBilletter];
        int ledig = finnLedigRad(antBilletter);
        if((ledig != -1) && ((getKapasitet() - getAntSolgte()) >= antBilletter)) {
            for(int i = 0; i < antBilletter; i++) {
                int ledigRad = finnLedigRad(antBilletter);
                antOpptatt[ledigRad]++;
                SitteplassBillett billetten = new SitteplassBillett(getTribunenavn(), getPris(), antOpptatt[ledigRad], ledigRad);
                billettListe[i] = billetten;
            }
        } else {
            return null;
        }
        return billettListe;
    }

    @Override
    public Billett[] fullforKjop(String[] billettliste) {
        return kjopBillett(billettliste.length);
    }
}
