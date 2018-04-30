
import java.util.Arrays;
import java.util.Comparator;

public class TestKlient {

    private static Tribune[] sorter(Tribune[] trib) {
        Arrays.sort(trib, new Comparator<Tribune>() {
            @Override
            public int compare(Tribune nr1, Tribune nr2) {
                if (nr1.getInntekt() > nr2.getInntekt()) {
                    return -1;
                } else if (nr1.getInntekt() < nr2.getInntekt()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return trib;
    }


    public static void main(String[] args) {

        Staa staaTribune = new Staa("Staa1", 100, 250);
        Sitte sitteTribune = new Sitte("Sitte1", 50, 300, 5);
        VIP vipTribune = new VIP("VIP1", 30, 500, 3);
        Tribune[] tribuneListe = {staaTribune, sitteTribune, vipTribune};


        String[] navneListe = {"Eivind", "Henrik", "Ole"};


        Billett[] staaBilletter = staaTribune.kjopBillett(5);
        for(Billett rad : staaBilletter) {
            System.out.println(rad.toString());
        }

        Billett[] sitteplassBilletter = sitteTribune.kjopBillett(5);
        for(Billett rad : sitteplassBilletter) {
            System.out.println(rad.toString());
        }

        Billett[] vipBilletter = vipTribune.fullforKjop(navneListe);
        for(Billett rad : vipBilletter) {
            System.out.println(rad.toString());
        }

        for(Tribune trib : tribuneListe) {
            System.out.println(trib.toString());
        }
    }
}
