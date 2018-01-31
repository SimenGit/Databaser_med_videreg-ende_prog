import java.time.*;
import java.util.ArrayList;
import java.util.Random;

public class Medlemsarkiv {

    private ArrayList<BonusMedlem> bonusListe = new ArrayList<BonusMedlem>();

    public Medlemsarkiv() {
    }

    public double finnPoeng(int medlNr, String passord) {

        for (int i = 0; i < bonusListe.size(); i++) {
            if (bonusListe.get(i).getMedlnr() == medlNr && bonusListe.get(i).okPassord(passord)) {
                return bonusListe.get(i).getPoeng();
            }
        }
        return -1.0;
    }

    public boolean registrerPoeng(int medlNr, double nyPoeng) {

        for(int i = 0; i < bonusListe.size(); i++) {
            if(bonusListe.get(i).getMedlnr() == medlNr) {
                if(bonusListe.get(i) instanceof GullMedlem) {
                    GullMedlem m1 = (GullMedlem) bonusListe.get(i);
                    m1.registrerPoeng(nyPoeng);
                    return true;
                }
                if(bonusListe.get(i) instanceof SoelvMedlem) {
                    SoelvMedlem m2 = (SoelvMedlem) bonusListe.get(i);
                    m2.registrerPoeng(nyPoeng);
                    return true;
                }else {
                    bonusListe.get(i).registrerPoeng(nyPoeng);
                    return true;
                }
            }
        }
        return false;
    }

    public int nyMedlem(Personalia personalia, LocalDate innmeldt) throws IllegalArgumentException {

        BasicMedlem nyttMedlem = new BasicMedlem(lagMedlNr(), personalia, innmeldt, 0);
        bonusListe.add(nyttMedlem);
        return nyttMedlem.getMedlnr();
    }

    private int lagMedlNr() {
        Random random = new Random();
        int tall = random.nextInt(10000) + 10000;
        boolean run = true;

        while(run) {
            boolean ledig = true;
            for(int i = 0; i < bonusListe.size(); i++) {
                if(bonusListe.get(i).getMedlnr() == tall) {
                    ledig = false;
                }
            }
            if(!ledig) {
                tall = random.nextInt(10000) + 10000;
            }else{
                run = false;
            }
        }
        return tall;
    }

    public boolean sjekkMedlemmer() {
        boolean change = false;
        for(int i = 0; i < bonusListe.size(); i++) {
            if(bonusListe.get(i) instanceof BasicMedlem) {
                BasicMedlem b1 = (BasicMedlem)(bonusListe.get(i));
                if(b1.getPoeng() >= 75000) {
                    GullMedlem nyGull = new GullMedlem(b1.getMedlnr(), b1.getPersonalia(), b1.getInnmeldt(), b1.getPoeng());
                    bonusListe.set(i, nyGull);
                    change = true;
                }else{
                    if(b1.getPoeng() >= 25000) {
                        SoelvMedlem nySoelv = new SoelvMedlem(b1.getMedlnr(), b1.getPersonalia(), b1.getInnmeldt(), b1.getPoeng());
                        bonusListe.set(i, nySoelv);
                        change = true;
                    }
                }
            }else if (bonusListe.get(i) instanceof SoelvMedlem) {
                SoelvMedlem b2 = (SoelvMedlem)(bonusListe.get(i));
                if(b2.getPoeng() >= 75000) {
                   GullMedlem nyGull = new GullMedlem(b2.getMedlnr(), b2.getPersonalia(), b2.getInnmeldt(), b2.getPoeng());
                   bonusListe.set(i, nyGull);
                   change = true;
                }
            }
        }
        return change;
    }
    @Override
    public String toString() {
        String print = "Medlemmer: ";
        for(int i = 0; i < bonusListe.size(); i++){
            print += "\n" + bonusListe.get(i).toString();
        }
        return print;
    }
}
