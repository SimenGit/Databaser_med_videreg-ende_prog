import java.time.*;

public class SoelvMedlem extends BonusMedlem {

    public SoelvMedlem(int medlNr, Personalia personalia, LocalDate innmeldtDato, double poeng) {
        super(medlNr, personalia, innmeldtDato, poeng);
    }

    @Override
    public void registrerPoeng(double antPoeng) {
        setPoeng(antPoeng * 1.2);
    }
    @Override
    public String toString() {
        Personalia pers = getPersonalia();
        return pers.getFornavn() + " " + pers.getEtternavn() + ", medlnr: " + getMedlnr() + ", poeng: " + getPoeng() + ", innmldato: " + getInnmeldt() + ", MEDLEMSTYPE: SØLV";
    }
}
