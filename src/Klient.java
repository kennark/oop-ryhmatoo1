public class Klient {
    private final String nimi;
    private String riik;

    public Klient(String nimi, String riik) {
        this.nimi = nimi;
        this.riik = riik;
    }
    public Klient(String nimi) {
        this.nimi = nimi;
        this.riik = "Eesti"; // kui riiki ei anta, siis vaikimisi on riigiks Eesti
    }

    public String getNimi(){
        return nimi;
    }
    public String getRiik() {
        return riik;
    }

    @Override
    public String toString() {
        return nimi;
    }
}
