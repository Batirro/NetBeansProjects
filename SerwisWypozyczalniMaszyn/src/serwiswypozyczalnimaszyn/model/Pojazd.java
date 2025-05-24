package serwiswypozyczalnimaszyn.model;

import java.io.Serializable;

/**
 * Abstrakcyjna klasa bazowa reprezentująca ogólny pojazd w systemie wypożyczalni.
 * Implementuje interfejs Serializable, aby umożliwić zapis i odczyt obiektów tej klasy.
 */
public abstract class Pojazd implements Serializable {
    private static final long serialVersionUID = 1L; // Wersja serializacji dla kompatybilności

    private String marka; // Marka pojazdu
    private String model; // Model pojazdu
    private String numerRejestracyjny; // Numer rejestracyjny pojazdu
    private double cenaZaDobe; // Cena wynajmu pojazdu za dobę
    private boolean czyDostepny; // Status dostępności pojazdu

    /**
     * Konstruktor klasy Pojazd.
     *
     * @param marka Marka pojazdu.
     * @param model Model pojazdu.
     * @param numerRejestracyjny Numer rejestracyjny pojazdu.
     * @param cenaZaDobe Cena wynajmu pojazdu za dobę.
     */
    public Pojazd(String marka, String model, String numerRejestracyjny, double cenaZaDobe) {
        this.marka = marka;
        this.model = model;
        this.numerRejestracyjny = numerRejestracyjny;
        this.cenaZaDobe = cenaZaDobe;
        this.czyDostepny = true; // Domyślnie nowy pojazd jest dostępny
    }

    /**
     * Zwraca markę pojazdu.
     * @return Marka pojazdu.
     */
    public String getMarka() {
        return marka;
    }

    /**
     * Ustawia markę pojazdu.
     * @param marka Nowa marka pojazdu.
     */
    public void setMarka(String marka) {
        this.marka = marka;
    }

    /**
     * Zwraca model pojazdu.
     * @return Model pojazdu.
     */
    public String getModel() {
        return model;
    }

    /**
     * Ustawia model pojazdu.
     * @param model Nowy model pojazdu.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Zwraca numer rejestracyjny pojazdu.
     * @return Numer rejestracyjny pojazdu.
     */
    public String getNumerRejestracyjny() {
        return numerRejestracyjny;
    }

    /**
     * Ustawia numer rejestracyjny pojazdu.
     * @param numerRejestracyjny Nowy numer rejestracyjny pojazdu.
     */
    public void setNumerRejestracyjny(String numerRejestracyjny) {
        this.numerRejestracyjny = numerRejestracyjny;
    }

    /**
     * Zwraca cenę wynajmu pojazdu za dobę.
     * @return Cena za dobę.
     */
    public double getCenaZaDobe() {
        return cenaZaDobe;
    }

    /**
     * Ustawia cenę wynajmu pojazdu za dobę.
     * @param cenaZaDobe Nowa cena za dobę.
     */
    public void setCenaZaDobe(double cenaZaDobe) {
        this.cenaZaDobe = cenaZaDobe;
    }

    /**
     * Sprawdza, czy pojazd jest dostępny.
     * @return true, jeśli pojazd jest dostępny, w przeciwnym razie false.
     */
    public boolean isCzyDostepny() {
        return czyDostepny;
    }

    /**
     * Ustawia status dostępności pojazdu.
     * @param czyDostepny true, jeśli pojazd ma być dostępny, false w przeciwnym razie.
     */
    public void setCzyDostepny(boolean czyDostepny) {
        this.czyDostepny = czyDostepny;
    }

    /**
     * Abstrakcyjna metoda zwracająca typ pojazdu.
     * Każda konkretna klasa pojazdu musi zaimplementować tę metodę.
     * @return Typ pojazdu jako String.
     */
    public abstract String getTyp();

    /**
     * Zwraca reprezentację tekstową obiektu Pojazd.
     * Przydatne do wyświetlania informacji o pojeździe, np. w listach.
     * @return String zawierający markę, model i numer rejestracyjny pojazdu.
     */
    @Override
    public String toString() {
        return marka + " " + model + " (" + numerRejestracyjny + ")";
    }
}