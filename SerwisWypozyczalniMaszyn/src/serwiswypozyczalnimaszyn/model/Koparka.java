package serwiswypozyczalnimaszyn.model;

/**
 * Klasa reprezentująca Koparkę, dziedzicząca po klasie Pojazd.
 * Specyficzne atrybuty dla koparki to głębokość kopania, pojemność łyżki i zasięg ramienia.
 */
public class Koparka extends Pojazd {
    private static final long serialVersionUID = 2L; // Zmieniona wersja serializacji

    private double glebokoscKopania_m; // Maksymalna głębokość kopania w metrach
    private double pojemnoscLyzeczki_m3; // Pojemność łyżeczki w metrach sześciennych
    private double zasiegRamienia_m; // Zasięg ramienia w metrach

    /**
     * Konstruktor klasy Koparka.
     *
     * @param marka Marka koparki.
     * @param model Model koparki.
     * @param numerRejestracyjny Numer rejestracyjny koparki.
     * @param cenaZaDobe Cena wynajmu koparki za dobę.
     * @param glebokoscKopania_m Maksymalna głębokość kopania w metrach.
     * @param pojemnoscLyzeczki_m3 Pojemność łyżeczki w m^3.
     * @param zasiegRamienia_m Zasięg ramienia w metrach.
     */
    public Koparka(String marka, String model, String numerRejestracyjny, double cenaZaDobe, double glebokoscKopania_m, double pojemnoscLyzeczki_m3, double zasiegRamienia_m) {
        super(marka, model, numerRejestracyjny, cenaZaDobe);
        this.glebokoscKopania_m = glebokoscKopania_m;
        this.pojemnoscLyzeczki_m3 = pojemnoscLyzeczki_m3;
        this.zasiegRamienia_m = zasiegRamienia_m;
    }

    /**
     * Zwraca maksymalną głębokość kopania.
     * @return Głębokość kopania w metrach.
     */
    public double getGlebokoscKopania_m() {
        return glebokoscKopania_m;
    }

    /**
     * Ustawia maksymalną głębokość kopania.
     * @param glebokoscKopania_m Nowa maksymalna głębokość kopania w metrach.
     */
    public void setGlebokoscKopania_m(double glebokoscKopania_m) {
        this.glebokoscKopania_m = glebokoscKopania_m;
    }

    /**
     * Zwraca pojemność łyżeczki.
     * @return Pojemność łyżeczki w m^3.
     */
    public double getPojemnoscLyzeczki_m3() {
        return pojemnoscLyzeczki_m3;
    }

    /**
     * Ustawia pojemność łyżeczki.
     * @param pojemnoscLyzeczki_m3 Nowa pojemność łyżeczki w m^3.
     */
    public void setPojemnoscLyzeczki_m3(double pojemnoscLyzeczki_m3) {
        this.pojemnoscLyzeczki_m3 = pojemnoscLyzeczki_m3;
    }

    /**
     * Zwraca zasięg ramienia.
     * @return Zasięg ramienia w metrach.
     */
    public double getZasiegRamienia_m() {
        return zasiegRamienia_m;
    }

    /**
     * Ustawia zasięg ramienia.
     * @param zasiegRamienia_m Nowy zasięg ramienia w metrach.
     */
    public void setZasiegRamienia_m(double zasiegRamienia_m) {
        this.zasiegRamienia_m = zasiegRamienia_m;
    }

    /**
     * Zwraca typ pojazdu jako "Koparka".
     * @return String "Koparka".
     */
    @Override
    public String getTyp() {
        return "Koparka";
    }

    /**
     * Zwraca reprezentację tekstową obiektu Koparka, zawierającą również jej specyficzne atrybuty.
     * @return String opisujący koparkę.
     */
    @Override
    public String toString() {
        return super.toString() + " - Gł. kopania: " + glebokoscKopania_m + "m, Łyżka: " + pojemnoscLyzeczki_m3 + "m³, Zasięg: " + zasiegRamienia_m + "m";
    }
}
