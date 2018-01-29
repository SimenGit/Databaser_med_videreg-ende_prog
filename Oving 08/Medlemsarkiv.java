import java.time.*;
import java.util.ArrayList;

public class Medlemsarkiv {

    private ArrayList<BonusMedlem> bonusListe= new ArrayList<BonusMedlem>();


/* finnPoeng() skal ta medlemsnummer og passord som argument og returnere antall poeng denne kunden har spart opp. Returner en negativ verdi hvis medlem med dette nr ikke fins, eller passord er ugyldig.

        registrerPoeng() skal ta medlemsnummer og antall poeng som argument og sørge for at riktig antall poeng blir registrert for dette medlemmet. Returner false dersom medlem med dette nr ikke fins.

        nyMedlem() skal ha følgende metodehode:

public int nyMedlem(Personalia pers, LocalDate innmeldt) */

    public Medlemsarkiv() {
    }

    public double finnPoeng(int medlNr, String passord) {

        for (int i = 0; i < bonusListe.size(); i++) {
            if (bonusListe.get(i).getMedlnr() == medlNr && bonusListe.get(i).okPassord(passord)) {
                return bonusListe.get(i).getPoeng();
            }
        }
        return -1;
    }

