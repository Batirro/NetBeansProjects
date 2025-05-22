package serwiswypozyczalnimaszyn.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Wypozyczenie {
    private int id;
    private Klient klient;
    private Pojazd pojazd;
    private LocalDate dataRozpoczecia;
    private LocalDate planowanaDataZakonczenia; // Nowe pole
    private LocalDate faktycznaDataZakonczenia; // Zmieniona nazwa z dataZakonczenia
    private boolean aktywne;
    private double kosztCalkowity;

    private static final DateTimeFormatter FORMATTER_DATY = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Konstruktor klasy Wypozyczenie.
     *
     * @param id Unikalny identyfikator wypożyczenia.
     * @param klient Klient dokonujący wypożyczenia.
     * @param pojazd Wypożyczany pojazd.
     * @param dataRozpoczecia Data rozpoczęcia wypożyczenia.
     * @param planowanaDataZakonczenia Planowana data zwrotu pojazdu.
     */
    public Wypozyczenie(int id, Klient klient, Pojazd pojazd, LocalDate dataRozpoczecia, LocalDate planowanaDataZakonczenia) {
        this.id = id;
        this.klient = klient;
        this.pojazd = pojazd;
        this.dataRozpoczecia = dataRozpoczecia;
        this.planowanaDataZakonczenia = planowanaDataZakonczenia; // Ustawienie planowanej daty
        this.faktycznaDataZakonczenia = null; // Faktyczna data jest null na początku
        this.aktywne = true;
        this.kosztCalkowity = 0.0;
    }

    // Gettery i Settery
    public int getId() { return id; }
    public Klient getKlient() { return klient; }
    public Pojazd getPojazd() { return pojazd; }
    public LocalDate getDataRozpoczecia() { return dataRozpoczecia; }
    public LocalDate getPlanowanaDataZakonczenia() { return planowanaDataZakonczenia; } // Getter dla planowanej daty
    public LocalDate getFaktycznaDataZakonczenia() { return faktycznaDataZakonczenia; } // Getter dla faktycznej daty
    public boolean isAktywne() { return aktywne; }
    public double getKosztCalkowity() { return kosztCalkowity; }

    public void setPlanowanaDataZakonczenia(LocalDate planowanaDataZakonczenia) {
        // Możliwość zmiany planowanej daty, jeśli wypożyczenie jest aktywne
        if (this.aktywne) {
            this.planowanaDataZakonczenia = planowanaDataZakonczenia;
        }
    }


    /**
     * Kończy wypożyczenie ustawiając faktyczną datę zwrotu i oblicza jego koszt.
     * @param faktycznaDataZwrotu Data faktycznego zwrotu pojazdu.
     */
    public void zakonczWypozyczenie(LocalDate faktycznaDataZwrotu) {
        if (this.aktywne) {
            this.faktycznaDataZakonczenia = faktycznaDataZwrotu;
            this.aktywne = false;
            obliczKosztWypozyczenia();
        }
    }

    /**
     * Oblicza koszt całkowity wypożyczenia na podstawie liczby dni między datą rozpoczęcia
     * a faktyczną datą zakończenia oraz dziennej stawki pojazdu.
     * Minimalny okres wypożyczenia to 1 dzień.
     */
    private void obliczKosztWypozyczenia() {
        if (pojazd != null && faktycznaDataZakonczenia != null && dataRozpoczecia != null) {
            if (faktycznaDataZakonczenia.isBefore(dataRozpoczecia)) {
                this.kosztCalkowity = 0.0;
                System.err.println("Błąd: Faktyczna data zakończenia wcześniejsza niż data rozpoczęcia dla wypożyczenia ID: " + id);
                return;
            }
            long liczbaDni = ChronoUnit.DAYS.between(dataRozpoczecia, faktycznaDataZakonczenia) + 1;
            if (liczbaDni <= 0) {
                liczbaDni = 1;
            }
            this.kosztCalkowity = liczbaDni * pojazd.getDziennaStawka();
        } else {
            this.kosztCalkowity = 0.0;
        }
    }

    @Override
    public String toString() {
        String planowanaDataZakonStr = (planowanaDataZakonczenia == null) ? "N/A" : planowanaDataZakonczenia.format(FORMATTER_DATY);
        String faktycznaDataZakonStr = (faktycznaDataZakonczenia == null) ? "N/A" : faktycznaDataZakonczenia.format(FORMATTER_DATY);
        String kosztStr = (kosztCalkowity > 0 || !aktywne) ? String.format("%.2f PLN", kosztCalkowity) : "N/A (aktywne)";

        return "ID Wypożyczenia: " + id +
                ", Klient: [" + klient.getNazwaFirmy() + "]" +
                ", Pojazd: [" + pojazd.getNazwaModelu() + "]" +
                ", Od: " + dataRozpoczecia.format(FORMATTER_DATY) +
                ", Plan. Do: " + planowanaDataZakonStr + // Dodano planowaną datę
                ", Fakt. Do: " + faktycznaDataZakonStr + // Zmieniono etykietę
                ", Status: " + (aktywne ? "Aktywne" : "Zakończone") +
                ", Koszt: " + kosztStr;
    }
}