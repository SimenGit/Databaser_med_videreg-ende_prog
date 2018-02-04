import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class TestTrebuen implements Serializable{

    private static Trebuen[] sorter(Trebuen[] trib) {
        Arrays.sort(trib, new Comparator<Trebuen>() {
            @Override
            public int compare(Trebuen nr1, Trebuen nr2) {                          //implement method.
                if(nr1.finnInntekt() > nr2.finnInntekt()) {
                    return -1;
                } else if(nr1.finnInntekt() < nr2.finnInntekt()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return trib;
    }


    public static void main(String[] args) throws IOException {

        Staa staaTribune = new Staa("Staa1", 100, 250);
        Sitte sitteTribune = new Sitte("Sitte1", 50, 300, 5);
        VIP vipTribune = new VIP("VIP1", 30, 500, 3);
        Trebuen[] daList = {staaTribune, sitteTribune, vipTribune};


        //kjøper stå-biller:
        Billett[] kjopeStaa = staaTribune.kjopBilletter(3);
        System.out.println("har kjøpt 3 staabilletter:");
        for(int i = 1; i < kjopeStaa.length + 1; i++) {
            System.out.println("Billett " + i + ": " + staaTribune.toString());
        }


        //kjoper sitte-biller:
        System.out.println("\n\n");
        Billett[] kjopeSitte = sitteTribune.kjopBilletter(3);
        System.out.println("har kjøpt 3 sitteBilletter:");
        for(int i = 1; i < kjopeSitte.length + 1; i++) {
            System.out.println("Billett " + i + ": " + sitteTribune.toString());
        }

        //kjøper 3 VIP-biller:
        System.out.println("\n\n");
        System.out.println("har kjøpt 3 VIP-billetter");
        String[] kjoper = {"Heidi, Jonatan", "Erik", "Jonathan"};
        Billett[] kjopeVIP = vipTribune.kjopBillett(kjoper);
        for(Billett p : kjopeVIP) {
            System.out.println(p.toString());
        }


        //Skriver ut alle tribunene med info.
        System.out.println("\n\nPrinter ut alle tribunene: ");
        for(Trebuen o : daList) {
            System.out.println(o.toStringAlle() + "\n");
        }


        //Sorterer etter inntekt fra metode øverst:
        daList = sorter(daList);
        System.out.println("Sorterer tribunene etter inntekt (Mest inntekt først): \n");
        for(Trebuen o : daList) {
            System.out.println(o.toStringAlle() + "\n");
        }

        // 2. Lagre alle tribuneobjektene på fil. Les dem inn fra fil igjen og skriv ut.
        System.out.println("Tester lesing og skriving til fil: \n");

        try {
            FileOutputStream fos = new FileOutputStream("C:\\Program Files\\JetBrains\\IdeaProjects\\Ovinger\\src\\Oving9\\ey.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(staaTribune);
            oos.writeObject(sitteTribune);
            oos.writeObject(vipTribune);
            oos.close();

            FileInputStream fis = new FileInputStream("C:\\Program Files\\JetBrains\\IdeaProjects\\Ovinger\\src\\Oving9\\ey.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Trebuen result = (Trebuen) ois.readObject();
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
