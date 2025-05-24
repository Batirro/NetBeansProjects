package serwiswypozyczalnimaszyn.model;

/**
 * Klasa reprezentująca Wywrotkę, dziedzicząca po klasie Pojazd.
 * Specyficzne atrybuty dla wywrotki to ładowność, typ napędu i pojemność skrzyni.
 */
public class Wywrotka extends Pojazd {
    private static final long serialVersionUID = 2L; // Zmieniona wersja serializacji

    private double ladownosc_tony; // Ładowność wywrotki w tonach
    private String typNapedu; // Typ napędu (np. 4x2, 6x4, 8x4)
    private double pojemnoscSkrzyni_m3; // Pojemność skrzyni ładunkowej w metrach sześciennych

    /**
     * Konstruktor klasy Wywrotka.
     *
     * @param marka Marka wywrotki.
     * @param model Model wywrotki.
     * @param numerRejestracyjny Numer rejestracyjny wywrotki.
     * @param cenaZaDobe Cena wynajmu wywrotki za dobę.
     * @param ladownosc_tony Ładowność wywrotki w tonach.
     * @param typNapedu Typ napędu wywrotki.
     * @param pojemnoscSkrzyni_m3 Pojemność skrzyni ładunkowej w m^3.
     */
    public Wywrotka(String marka, String model, String numerRejestracyjny, double cenaZaDobe, double ladownosc_tony, String typNapedu, double pojemnoscSkrzyni_m3) {
        super(marka, model, numerRejestracyjny, cenaZaDobe);
        this.ladownosc_tony = ladownosc_tony;
        this.typNapedu = typNapedu;
        this.pojemnoscSkrzyni_m3 = pojemnoscSkrzyni_m3;
    }

    /**
     * Zwraca ładowność wywrotki.
     * @return Ładowność w tonach.
     */
    public double getLadownosc_tony() {
        return ladownosc_tony;
    }

    /**
     * Ustawia ładowność wywrotki.
     * @param ladownosc_tony Nowa ładowność w tonach.
     */
    public void setLadownosc_tony(double ladownosc_tony) {
        this.ladownosc_tony = ladownosc_tony;
    }

    /**
     * Zwraca typ napędu wywrotki.
     * @return Typ napędu.
     */
    public String getTypNapedu() {
        return typNapedu;
    }

    /**
     * Ustawia typ napędu wywrotki.
     * @param typNapedu Nowy typ napędu.
     */
    public void setTypNapedu(String typNapedu) {
        this.typNapedu = typNapedu;
    }

    /**
     * Zwraca pojemność skrzyni ładunkowej.
     * @return Pojemność skrzyni w m^3.
     */
    public double getPojemnoscSkrzyni_m3() {
        return pojemnoscSkrzyni_m3;
    }

    /**
     * Ustawia pojemność skrzyni ładunkowej.
     * @param pojemnoscSkrzyni_m3 Nowa pojemność skrzyni w m^3.
     */
    public void setPojemnoscSkrzyni_m3(double pojemnoscSkrzyni_m3) {
        this.pojemnoscSkrzyni_m3 = pojemnoscSkrzyni_m3;
    }

    /**
     * Zwraca typ pojazdu jako "Wywrotka".
     * @return String "Wywrotka".
     */
    @Override
    public String getTyp() {
        return "Wywrotka";
    }

    /**
     * Zwraca reprezentację tekstową obiektu Wywrotka, zawierającą również jej specyficzne atrybuty.
     * @return String opisujący wywrotkę.
     */
    @Override
    public String toString() {
        return super.toString() + " - Ładowność: " + ladownosc_tony + "t, Napęd: " + typNapedu + ", Skrzynia: " + pojemnoscSkrzyni_m3 + "m³";
    }
}
