public class Klient {
    private final String nimi;

    public Klient(String nimi) {
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return nimi;
    }
}
