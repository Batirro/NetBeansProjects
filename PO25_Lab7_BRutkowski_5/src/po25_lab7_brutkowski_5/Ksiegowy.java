package po25_lab7_brutkowski_5;

public class Ksiegowy extends Pracownik {

    public Ksiegowy(String imie, String nazwisko, double pensjaPodstawowa) {
        super(imie, nazwisko, pensjaPodstawowa);
    }

    @Override
    public double obliczPremie() {
        return getPensjaPodstawowa() * 0.10;
    }

    @Override
    public double obliczWynagrodzenieNetto() {
        double dochodBrutto = obliczDochodBrutto();
        double podatek = obliczPodatekStandardowy();
        return dochodBrutto - podatek;
    }

     @Override
    public String toString() {
        return "Księgowy: " + super.toString();
    }
}