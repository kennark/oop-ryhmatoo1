public class Välismakse extends Tehing{
    private double teenustasu;
    public Välismakse(int tehinguNumber, Pangakonto saaja, Pangakonto saatja, double saadetudSumma) {
        super(tehinguNumber, saaja, saatja, saadetudSumma);
        this.teenustasu = 5;
    }

    public void teostaMakse() {
        saaja.rahaJuurde(saadetudSumma);
        saatja.rahaVälja(saadetudSumma + teenustasu);
    }

    public double getTeenustasu() {
        return teenustasu;
    }

    @Override
    public String maksetüüp() {
        return "Välismakse";
    }

    @Override
    public String toString() {
        return super.toString() + " Teenustasu: " + teenustasu +
                ", kogusumma: " + (getSaadetudSumma()+teenustasu) + ". " +
                saatja.getKlient().getRiik() + " -> " + saaja.getKlient().getRiik();
    }
}
