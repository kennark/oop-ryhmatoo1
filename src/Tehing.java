public class Tehing {
    private final int tehinguNumber;
    private final Pangakonto saaja;
    private final Pangakonto saatja;
    private final double saadetudSumma;

    public Tehing(int tehinguNumber, Pangakonto saaja, Pangakonto saatja, double saadetudSumma) {
        this.tehinguNumber = tehinguNumber;
        this.saaja = saaja;
        this.saatja = saatja;
        this.saadetudSumma = saadetudSumma;

        saaja.rahaJuurde(saadetudSumma);
        saatja.rahaVälja(saadetudSumma);
    }

    public int getTehinguNumber() {
        return tehinguNumber;
    }

    public Pangakonto getSaaja() {
        return saaja;
    }

    public Pangakonto getSaatja() {
        return saatja;
    }

    public double getSaadetudSumma() {
        return saadetudSumma;
    }

    @Override
    public String toString() {
        return "Tehing nr " + (tehinguNumber+1) + ": " + saatja.getKlient() +
                " saatis isikule " + saaja.getKlient() + " " + saadetudSumma + " eurot.";
    }
}
