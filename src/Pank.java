import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Pank {
    private List<Klient> kliendid;
    private List<Pangakonto> kontod;
    private List<Tehing> tehingud;

    public Pank() throws IOException {
        this.kliendid = new ArrayList<>();
        this.kontod = new ArrayList<>();
        this.tehingud = new ArrayList<>();
        loeKontod("kontod.txt");

    }

    public void loeKontod(String failinimi) throws IOException {
        File fail = new File(failinimi);
        Scanner scanner = new Scanner(fail, StandardCharsets.UTF_8);
        while (scanner.hasNextLine()){
            String rida = scanner.nextLine();
            String[] osad = rida.split("; ");

            if (osad.length == 6) {
                Klient klient = new Klient(osad[0]);

                if (!kliendid.contains(klient)) kliendid.add(klient);

                kontod.add(new Pangakonto(klient, Integer.parseInt(osad[1]),
                        Double.parseDouble(osad[2]), Double.parseDouble(osad[3]),
                        Double.parseDouble(osad[4]), Integer.parseInt(osad[5])));

            } else if (osad.length == 4) {
                Pangakonto saaja = null;
                Pangakonto saatja = null;

                for (Pangakonto konto : kontod) {
                    if (konto.getKontoNumber() == Integer.parseInt(osad[1])){
                        saaja = konto;
                    }
                    if (konto.getKontoNumber() == Integer.parseInt(osad[2])){
                        saatja = konto;
                    }
                }
                tehingud.add(new Tehing(Integer.parseInt(osad[0]), saaja, saatja, Double.parseDouble(osad[3])));
            }
        }
    }

    public void salvestaKontod(String failinimi) throws IOException {
        // kirjutab faili
        FileWriter kirjutaja = new FileWriter(failinimi);
        for (Pangakonto konto: kontod) {
            kirjutaja.write(
                    konto.getKlient() + "; " + konto.getKontoNumber() + "; " +
                            konto.getKontoJääk() + "; " + konto.getVäljaminekud() + "; " +
                            konto.getSissetulekud() + "; " + konto.getTehinguteArv() + "\n");
        }
        for (Tehing tehing : tehingud) {
            kirjutaja.write(
                    tehing.getTehinguNumber() + "; " + tehing.getSaaja().getKontoNumber() + "; " +
                            tehing.getSaatja().getKontoNumber() + "; " + tehing.getSaadetudSumma() + "\n");
        }
        kirjutaja.close();
    }

    public Pangakonto sisseLogimine(){
        System.out.println("\"Parim pank maailmas\"");
        System.out.println();
        System.out.print("Sinu sõbrad ");
        for (Klient klient: kliendid) {
            System.out.print(klient + ", ");
        }
        System.out.println();
        System.out.println("kasutavad seda panka, liitu ka!");
        System.out.println();
        // nagu mingi sotsiaalmeedia stiilis reklaam


        String sisestus = JOptionPane.showInputDialog(null,
                "Sisesta oma ees- ja perekonnanimi",
                "Logi sisse/ava konto",
                JOptionPane.QUESTION_MESSAGE);

        // kui sisestatud nimi juba eksisteerib, siis "logib sisse" sellesse kontosse
        for (Pangakonto konto : kontod) {
            if (konto.getKlient().toString().equals(sisestus)) {
                return konto;
            }
        }

        // muidu läheb edasi ja "loob konto"
        Klient kasutaja = new Klient(sisestus);
        // algne kontosumma
        sisestus = JOptionPane.showInputDialog(null,
                "Kui palju raha sinul on kontole panna?",
                "Ava konto",
                JOptionPane.QUESTION_MESSAGE);

        // suvaline arv kontonumbriks, kui tehtud number juba eksisteerib, siis teeb uue ja kontrollib uuesti
        Random random = new Random();
        int uusKontoNumber = random.nextInt(1000000000, Integer.MAX_VALUE);
        for (int i = 0; i < kontod.size(); i++) {
            if (kontod.get(i).getKontoNumber() == uusKontoNumber) {
                uusKontoNumber = random.nextInt(1000000000, Integer.MAX_VALUE);
                i = -1;
            }
        }
        // loob uue konto
        Pangakonto kasutajaKonto = new Pangakonto(kasutaja, uusKontoNumber, Double.parseDouble(sisestus));
        kontod.add(kasutajaKonto);

        return kasutajaKonto;
    }

    public void teeTehing(Pangakonto saatja){
        String saajaNumber = JOptionPane.showInputDialog(null,
                "Mis konto numbrile saadad?",
                "Saada raha",
                JOptionPane.QUESTION_MESSAGE);

        for (Pangakonto konto : kontod) {
            // enne järgmist akent otsib konto numbri juba üles
            if (konto.getKontoNumber() == Integer.parseInt(saajaNumber)){

                String saadab = JOptionPane.showInputDialog(null,
                        "Sinul raha: " + saatja.getKontoJääk() + "\nKui palju raha saadad?",
                        "Saada raha isikule " + konto.getKlient(),
                        JOptionPane.QUESTION_MESSAGE);

                double summa = Double.parseDouble(saadab);
                if (saatja.getKontoJääk() < summa) {
                    System.out.println("Kontol ei ole piisavalt vahendeid!");
                    return;
                }

                // loob vastava tehingu ja salvestab selle
                tehingud.add(new Tehing(tehingud.size(), konto, saatja, summa));
                System.out.println("Tehing edukalt sooritatud!");
                return;
            }
        }
        System.out.println("Kontonumbrit ei leitud!");
    }

    public void näitaKontod(){
        // väljastab selle vaid alguses, enne kliendi tegevusi, et oleks kergem aru saada panga klientidest
        System.out.println("Klientide andmed:");
        for (Pangakonto konto : kontod) {
            System.out.println(konto);
        }
    }
    public void kontoJääk(Pangakonto konto){
        System.out.println("Sinu konto jääk:");
        System.out.println(konto.getKontoJääk());
    }

    public void näitaTehinguid() {
        // kõik salvestatud tehingud
        System.out.println("Kõik tehingud");
        for (Tehing tehing : tehingud) {
            System.out.println(tehing);
        }
    }

    public void lisaRaha(Pangakonto konto) {
        // nagu automaati sisse panemine
        String sisestus = JOptionPane.showInputDialog(null,
                "Kui palju raha lisad?",
                "Raha sisse",
                JOptionPane.QUESTION_MESSAGE);
        double summa = Double.parseDouble(sisestus);
        konto.rahaJuurde(summa);
        System.out.println("Raha lisatud!");
    }

    public void väljastaRaha(Pangakonto konto) {
        // nagu automaadist välja võtmine
        String sisestus = JOptionPane.showInputDialog(null,
                "Kui palju raha võtad välja?",
                "Raha välja",
                JOptionPane.QUESTION_MESSAGE);
        double summa = Double.parseDouble(sisestus);
        konto.rahaVälja(summa);
        System.out.println("Raha väljastatud!");
    }
}
