package po25_lab7_brutkowski_5;

public abstract class Pracownik {
    protected String imie;
    protected String nazwisko;
    protected double pensjaPodstawowa;
    protected final double STAWKA_PODATKU = 0.17;

    public Pracownik(String imie, String nazwisko, double pensjaPodstawowa) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pensjaPodstawowa = pensjaPodstawowa;
    }

    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public double getPensjaPodstawowa() { return pensjaPodstawowa; }

    public abstract double obliczPremie();

    public double obliczDochodBrutto() {
        return pensjaPodstawowa + obliczPremie();
    }

    protected double obliczPodatekStandardowy() {
        return obliczDochodBrutto() * STAWKA_PODATKU;
    }

    public abstract double obliczWynagrodzenieNetto();

    @Override
    public String toString() {
        return String.format("%s %s (Pensja podstawowa: %.2f zł)",
                imie, nazwisko, pensjaPodstawowa);
    }

    public String wyswietlSzczegolyWynagrodzenia() {
        double premia = obliczPremie();
        double netto = obliczWynagrodzenieNetto();
        return String.format("%s\n  Premia: %.2f zł\n  Wynagrodzenie Netto: %.2f zł",
                toString(), premia, netto);
    }
}