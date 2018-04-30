public abstract class Tribune {

    private final String tribunenavn;
    private final int kapasitet;
    private final int pris;

    public Tribune(String tribunenavn, int kapasitet, int pris) {
        this.tribunenavn = tribunenavn;
        this.kapasitet = kapasitet;
        this.pris = pris;
    }

    public abstract int getAntSolgte();
    public abstract double getInntekt();
    public abstract Billett[] kjopBillett(int antBilletter);
    public abstract Billett[] fullforKjop(String[] billettliste);

    public int getPris() {
        return pris;
    }
    public int getKapasitet() {
        return kapasitet;
    }
    public String getTribunenavn() {
        return tribunenavn;
    }
    public String toString() {
        return "Tribunenavn: " + getTribunenavn() + ",  " + "Pris: " + getPris();
    }
}
