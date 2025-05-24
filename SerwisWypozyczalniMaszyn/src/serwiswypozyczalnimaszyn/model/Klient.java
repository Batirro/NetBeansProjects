package serwiswypozyczalnimaszyn.model;

import java.io.Serializable;

/**
 * Klasa reprezentująca klienta będącego firmą.
 * Identyfikowana przez numer NIP.
 * Implementuje interfejs Serializable, aby umożliwić zapis i odczyt obiektów tej klasy.
 */
public class Klient implements Serializable {
    private static final long serialVersionUID = 2L; // Zmieniona wersja serializacji

    private String nazwaFirmy; // Nazwa firmy klienta
    private String nip; // Numer Identyfikacji Podatkowej firmy
    private String numerTelefonu; // Numer telefonu kontaktowego firmy
    private String email; // Adres email kontaktowy firmy

    /**
     * Konstruktor klasy Klient (Firma).
     *
     * @param nazwaFirmy Nazwa firmy.
     * @param nip Numer NIP firmy.
     * @param numerTelefonu Numer telefonu kontaktowego.
     * @param email Adres email kontaktowy.
     */
    public Klient(String nazwaFirmy, String nip, String numerTelefonu, String email) {
        this.nazwaFirmy = nazwaFirmy;
        this.nip = nip;
        this.numerTelefonu = numerTelefonu;
        this.email = email;
    }

    /**
     * Zwraca nazwę firmy.
     * @return Nazwa firmy.
     */
    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    /**
     * Ustawia nazwę firmy.
     * @param nazwaFirmy Nowa nazwa firmy.
     */
    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }

    /**
     * Zwraca numer NIP firmy.
     * @return Numer NIP.
     */
    public String getNip() {
        return nip;
    }

    /**
     * Ustawia numer NIP firmy.
     * @param nip Nowy numer NIP.
     */
    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * Zwraca numer telefonu kontaktowego firmy.
     * @return Numer telefonu.
     */
    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    /**
     * Ustawia numer telefonu kontaktowego firmy.
     * @param numerTelefonu Nowy numer telefonu.
     */
    public void setNumerTelefonu(String numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    /**
     * Zwraca adres email kontaktowy firmy.
     * @return Adres email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia adres email kontaktowy firmy.
     * @param email Nowy adres email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Zwraca reprezentację tekstową obiektu Klient (Firma).
     * Przydatne do wyświetlania informacji o kliencie, np. w listach.
     * @return String zawierający nazwę firmy i NIP.
     */
    @Override
    public String toString() {
        return nazwaFirmy + " (NIP: " + nip + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Klient klient = (Klient) o;
        return nip != null ? nip.equals(klient.nip) : klient.nip == null;
    }

    @Override
    public int hashCode() {
        return nip != null ? nip.hashCode() : 0;
    }
}
