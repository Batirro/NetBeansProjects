package po25_lab7_brutkowski_5;

public class Programista extends Pracownik {
    protected int lataDoswiadczenia;

    public Programista(String imie, String nazwisko, double pensjaPodstawowa, int lataDoswiadczenia) {
        super(imie, nazwisko, pensjaPodstawowa);
        this.lataDoswiadczenia = lataDoswiadczenia;
    }

     public int getLataDoswiadczenia() { return lataDoswiadczenia; }

    @Override
    public double obliczPremie() {
        if (lataDoswiadczenia < 2) return 500.0;
        if (lataDoswiadczenia < 5) return 2000.0;
        return 5000.0;
    }

    @Override
    public double obliczWynagrodzenieNetto() {
        double dochodBrutto = obliczDochodBrutto();
        double podatekStandardowy = obliczPodatekStandardowy();
        double podatekDoZaplaty = podatekStandardowy * 0.5;
        return dochodBrutto - podatekDoZaplaty;
    }

     @Override
    public String toString() {
         return String.format("Programista: %s %s (Pensja podst.: %.2f zł, Doświadczenie: %d lat)",
                imie, nazwisko, pensjaPodstawowa, lataDoswiadczenia);
    }
}