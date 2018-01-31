import java.time.*;

public class BasicMedlem extends BonusMedlem {

    public BasicMedlem(int medlNr, Personalia personalia, LocalDate innmeldt, double poeng) {
        super(medlNr, personalia, innmeldt, poeng);
    }
    @Override
    public String toString() {
        Personalia pers = getPersonalia();
        return pers.getFornavn() + " " + pers.getEtternavn() + ", medlnr: " + getMedlnr() + ", poeng: " + getPoeng() + ", innmldato: " + getInnmeldt() + ", MEDLEMSTYPE: BASIC";
    }
}
