import javax.swing.*;
import java.io.IOException;

public class Peaklass {

    public static void main(String[] args) throws IOException {
        Pank pank = new Pank();

        Pangakonto kasutajaKonto = pank.sisseLogimine();
        System.out.println("Tere tulemast, " + kasutajaKonto.getKlient());
        pank.näitaKontod();

        label:
        while (true) {
            String sisestus = JOptionPane.showInputDialog(null,
                    """
                            Tegevused:
                            "lisa" - lisa raha kontole juurde
                            "sula" - võta raha automaadi abil välja
                            "saada" - saada teisele inimesele rahasumma
                            "jääk" - vaata oma konto jääki
                            "kokkuvõte" - vaata oma konto maksete kokkuvõtet
                            "tehingud" - vaata pangas tehtud tehinguid
                            "sulge" - sulge rakendus""",
                    "Vali tegevus",
                    JOptionPane.QUESTION_MESSAGE);
            if (sisestus != null) {
                switch (sisestus) {
                    case "lisa":
                        pank.lisaRaha(kasutajaKonto);
                        break;
                    case "sula":
                        pank.väljastaRaha(kasutajaKonto);
                        break;
                    case "saada":
                        pank.teeTehing(kasutajaKonto);
                        break;
                    case "jääk":
                        pank.kontoJääk(kasutajaKonto);
                        break;
                    case "kokkuvõte":
                        System.out.println(kasutajaKonto.bilanss());
                        break;
                    case "tehingud":
                        pank.näitaTehinguid();
                        break;
                    case "sulge":
                        break label;
                    default:
                        System.out.println("Vale käsklus!");
                        break;
                }
            } else break;
            System.out.println();
        }

        pank.salvestaKontod("kontod.txt");
    }
}
