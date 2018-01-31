import java.time.*;
import static javax.swing.JOptionPane.*;

public class BonusKlient {

    public static void main(String[] args) {

        String[] choices = {"Registrer medlem", "Registrer poeng", "Finn antall Poeng", "Sjekk medlemmer for oppgradering", "Print alle medlemmer", "Avslutt"};


        Medlemsarkiv flygemann = new Medlemsarkiv();
        int stick = 0;
        while(stick != 1) {
            int valg = showOptionDialog(null, "Hva vil du gjøre?", "SimenTengs", YES_NO_OPTION, INFORMATION_MESSAGE, null, choices, choices[0]);
            switch (valg) {
                case 0: // registrer medlem
                    LocalDate naa = LocalDate.now();
                   // String fornavn, String etternavn, String ePostadr, String passord
                    String fornavn = showInputDialog("Fornavn:");
                    String etternavn = showInputDialog("Etternavn:");
                    String epost = showInputDialog("Email:");
                    String passord = showInputDialog("Passord:");

                    Personalia personalia = new Personalia(fornavn, etternavn, epost, passord);
                    int ja = flygemann.nyMedlem(personalia, naa);
                    showMessageDialog(null, "Registrert. \n\n Medlemsnr: " + ja);
                    break;

                case 1:
                    int medlNr = Integer.parseInt(showInputDialog("Skriv inn medlemsnummeret til personen du vil registrere poeng til."));
                    double nyPoeng = Double.parseDouble(showInputDialog("Hvor mange poeng vil registrere?"));
                    flygemann.registrerPoeng(medlNr, nyPoeng);
                    showMessageDialog(null,"Registrert.");
                    break;

                case 2: // finn antall poeng

                    int medlnr = Integer.parseInt(showInputDialog("Skriv inn medlemsnummer:"));
                    String pass = showInputDialog("Skriv inn passord:");
                    double result = flygemann.finnPoeng(medlnr, pass);
                    if(result == -1) {
                        showMessageDialog(null, "Noe gikk galt.");
                    }else{
                        showMessageDialog(null, "Medlemmet har: " + result + " poeng.");
                    }
                    break;

                case 3: // sjekkMedlemmerforOppgradering
                    boolean jamann = flygemann.sjekkMedlemmer();
                    if(jamann) {
                        showMessageDialog(null, "Medlem oppgradert.");
                    }else{
                        showMessageDialog(null, "Ingen medlemmer å oppgradere.");
                    }
                    break;

                case 4:
                    showMessageDialog(null, flygemann.toString());

                case 5:
                    stick += 1;
                    break;
            }
        }
    }
}
