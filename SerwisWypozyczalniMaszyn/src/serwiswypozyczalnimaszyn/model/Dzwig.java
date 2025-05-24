package serwiswypozyczalnimaszyn.model;

/**
 * Klasa reprezentująca Dźwig, dziedzicząca po klasie Pojazd.
 * Specyficzne atrybuty dla dźwigu to maksymalny udźwig, długość wysięgnika i maksymalna wysokość podnoszenia.
 */
public class Dzwig extends Pojazd {
    private static final long serialVersionUID = 2L; // Zmieniona wersja serializacji

    private double maksymalnyUdzwig_tony; // Maksymalny udźwig dźwigu w tonach
    private double dlugoscWysiegnika_m; // Długość wysięgnika w metrach
    private double maksymalnaWysokoscPodnoszenia_m; // Maksymalna wysokość podnoszenia w metrach

    /**
     * Konstruktor klasy Dzwig.
     *
     * @param marka Marka dźwigu.
     * @param model Model dźwigu.
     * @param numerRejestracyjny Numer rejestracyjny dźwigu.
     * @param cenaZaDobe Cena wynajmu dźwigu za dobę.
     * @param maksymalnyUdzwig_tony Maksymalny udźwig dźwigu w tonach.
     * @param dlugoscWysiegnika_m Długość wysięgnika w metrach.
     * @param maksymalnaWysokoscPodnoszenia_m Maksymalna wysokość podnoszenia w metrach.
     */
    public Dzwig(String marka, String model, String numerRejestracyjny, double cenaZaDobe, double maksymalnyUdzwig_tony, double dlugoscWysiegnika_m, double maksymalnaWysokoscPodnoszenia_m) {
        super(marka, model, numerRejestracyjny, cenaZaDobe);
        this.maksymalnyUdzwig_tony = maksymalnyUdzwig_tony;
        this.dlugoscWysiegnika_m = dlugoscWysiegnika_m;
        this.maksymalnaWysokoscPodnoszenia_m = maksymalnaWysokoscPodnoszenia_m;
    }

    /**
     * Zwraca maksymalny udźwig dźwigu.
     * @return Maksymalny udźwig w tonach.
     */
    public double getMaksymalnyUdzwig_tony() {
        return maksymalnyUdzwig_tony;
    }

    /**
     * Ustawia maksymalny udźwig dźwigu.
     * @param maksymalnyUdzwig_tony Nowy maksymalny udźwig w tonach.
     */
    public void setMaksymalnyUdzwig_tony(double maksymalnyUdzwig_tony) {
        this.maksymalnyUdzwig_tony = maksymalnyUdzwig_tony;
    }

    /**
     * Zwraca długość wysięgnika.
     * @return Długość wysięgnika w metrach.
     */
    public double getDlugoscWysiegnika_m() {
        return dlugoscWysiegnika_m;
    }

    /**
     * Ustawia długość wysięgnika.
     * @param dlugoscWysiegnika_m Nowa długość wysięgnika w metrach.
     */
    public void setDlugoscWysiegnika_m(double dlugoscWysiegnika_m) {
        this.dlugoscWysiegnika_m = dlugoscWysiegnika_m;
    }

    /**
     * Zwraca maksymalną wysokość podnoszenia.
     * @return Maksymalna wysokość podnoszenia w metrach.
     */
    public double getMaksymalnaWysokoscPodnoszenia_m() {
        return maksymalnaWysokoscPodnoszenia_m;
    }

    /**
     * Ustawia maksymalną wysokość podnoszenia.
     * @param maksymalnaWysokoscPodnoszenia_m Nowa maksymalna wysokość podnoszenia w metrach.
     */
    public void setMaksymalnaWysokoscPodnoszenia_m(double maksymalnaWysokoscPodnoszenia_m) {
        this.maksymalnaWysokoscPodnoszenia_m = maksymalnaWysokoscPodnoszenia_m;
    }

    /**
     * Zwraca typ pojazdu jako "Dźwig".
     * @return String "Dźwig".
     */
    @Override
    public String getTyp() {
        return "Dźwig";
    }

    /**
     * Zwraca reprezentację tekstową obiektu Dzwig, zawierającą również jego specyficzne atrybuty.
     * @return String opisujący dźwig.
     */
    @Override
    public String toString() {
        return super.toString() + " - Udźwig: " + maksymalnyUdzwig_tony + "t, Wysięgnik: " + dlugoscWysiegnika_m + "m, Podnoszenie: " + maksymalnaWysokoscPodnoszenia_m + "m";
    }
}
