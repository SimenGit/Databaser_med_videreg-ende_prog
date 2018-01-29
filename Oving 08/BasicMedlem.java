  private final int medlNr;
    private final Personalia pers;
    private final LocalDate innmeldtDato;
    private double poeng = 0;

    public BonusMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, double poeng) {
        this.medlNr = medlNr;
        this.pers = pers;
        this.innmeldtDato = innmeldtDato;
        this.poeng = poeng;
    }

    public int getMedlnr() {
        return medlNr;
    }

    public Personalia getPersonalia() {
        return pers;
    }

    public LocalDate getInnmeldt() {
        return innmeldtDato;
    }

    public double getPoeng() {
        return this.poeng;
    }

    public double finnKvalPoeng(LocalDate date2) {

       // LocalDate date1 = getInnmeldt();
        LocalDate date1 = LocalDate.now();
        int dagerMellom = Period.between(date1, date2).getDays();

        if(dagerMellom > 365) {
            return 0;
        }else{
            return getPoeng();
        }
    }

    public boolean okPassord(String passord) {
        return pers.okPassord(passord);
    }

    public void registrerPoeng(double nyPoeng) {
        poeng += nyPoeng;
    }
}
