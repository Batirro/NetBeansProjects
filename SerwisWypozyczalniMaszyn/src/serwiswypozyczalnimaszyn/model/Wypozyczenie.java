package serwiswypozyczalnimaszyn.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klasa reprezentująca pojedyncze wypożyczenie pojazdu przez klienta (firmę).
 * Implementuje interfejs Serializable, aby umożliwić zapis i odczyt obiektów tej klasy.
 * Zawiera teraz również planowaną datę zwrotu.
 */
public class Wypozyczenie implements Serializable {
    private static final long serialVersionUID = 3L; // Zmieniona wersja serializacji

    private Klient klient; // Klient (firma), który wypożyczył pojazd
    private Pojazd pojazd; // Wypożyczony pojazd
    private Date dataWypozyczenia; // Data rozpoczęcia wypożyczenia
    private Date planowanaDataZwrotu; // Planowana data zwrotu pojazdu
    private Date dataZwrotu; // Rzeczywista data zakończenia/zwrotu wypożyczenia (może być null)
    private double kosztCalkowity; // Całkowity koszt wypożyczenia

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Konstruktor klasy Wypozyczenie.
     *
     * @param klient Klient (firma) dokonujący wypożyczenia.
     * @param pojazd Wypożyczany pojazd.
     * @param dataWypozyczenia Data rozpoczęcia wypożyczenia.
     * @param planowanaDataZwrotu Planowana data zwrotu pojazdu.
     */
    public Wypozyczenie(Klient klient, Pojazd pojazd, Date dataWypozyczenia, Date planowanaDataZwrotu) {
        this.klient = klient;
        this.pojazd = pojazd;
        this.dataWypozyczenia = dataWypozyczenia;
        this.planowanaDataZwrotu = planowanaDataZwrotu;
        this.dataZwrotu = null; // Początkowo data zwrotu jest nieustawiona
        this.kosztCalkowity = 0; // Początkowo koszt jest zerowy, obliczany przy zwrocie
    }

    /**
     * Zwraca klienta (firmę), który wypożyczył pojazd.
     * @return Obiekt Klient.
     */
    public Klient getKlient() {
        return klient;
    }

    /**
     * Ustawia klienta (firmę) dla wypożyczenia.
     * @param klient Nowy klient (firma).
     */
    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    /**
     * Zwraca wypożyczony pojazd.
     * @return Obiekt Pojazd.
     */
    public Pojazd getPojazd() {
        return pojazd;
    }

    /**
     * Ustawia pojazd dla wypożyczenia.
     * @param pojazd Nowy pojazd.
     */
    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    /**
     * Zwraca datę rozpoczęcia wypożyczenia.
     * @return Data wypożyczenia.
     */
    public Date getDataWypozyczenia() {
        return dataWypozyczenia;
    }

    /**
     * Ustawia datę rozpoczęcia wypożyczenia.
     * @param dataWypozyczenia Nowa data wypożyczenia.
     */
    public void setDataWypozyczenia(Date dataWypozyczenia) {
        this.dataWypozyczenia = dataWypozyczenia;
    }

    /**
     * Zwraca planowaną datę zwrotu pojazdu.
     * @return Planowana data zwrotu.
     */
    public Date getPlanowanaDataZwrotu() {
        return planowanaDataZwrotu;
    }

    /**
     * Ustawia planowaną datę zwrotu pojazdu.
     * @param planowanaDataZwrotu Nowa planowana data zwrotu.
     */
    public void setPlanowanaDataZwrotu(Date planowanaDataZwrotu) {
        this.planowanaDataZwrotu = planowanaDataZwrotu;
    }


    /**
     * Zwraca rzeczywistą datę zwrotu pojazdu.
     * @return Data zwrotu lub null, jeśli pojazd nie został zwrócony.
     */
    public Date getDataZwrotu() {
        return dataZwrotu;
    }

    /**
     * Ustawia rzeczywistą datę zwrotu pojazdu.
     * @param dataZwrotu Data zwrotu pojazdu.
     */
    public void setDataZwrotu(Date dataZwrotu) {
        this.dataZwrotu = dataZwrotu;
    }

    /**
     * Zwraca całkowity koszt wypożyczenia.
     * @return Koszt całkowity.
     */
    public double getKosztCalkowity() {
        return kosztCalkowity;
    }

    /**
     * Ustawia całkowity koszt wypożyczenia.
     * @param kosztCalkowity Nowy koszt całkowity.
     */
    public void setKosztCalkowity(double kosztCalkowity) {
        this.kosztCalkowity = kosztCalkowity;
    }

    /**
     * Oblicza koszt wypożyczenia na podstawie RZECZYWISTEJ daty zwrotu i ceny za dobę pojazdu.
     * Jeśli data zwrotu nie jest ustawiona, koszt pozostaje 0 lub taki jak był.
     * Koszt jest obliczany jako liczba dni (zaokrąglona w górę) pomnożona przez cenę za dobę.
     */
    public void obliczKoszt() {
        if (dataZwrotu != null && dataWypozyczenia != null && pojazd != null) {
            if (dataZwrotu.before(dataWypozyczenia)) {
                // Koszt nie powinien być ujemny, można ustawić na 0 lub koszt minimalny
                // Dla uproszczenia, jeśli data zwrotu jest błędna, nie obliczamy.
                System.err.println("Data zwrotu nie może być wcześniejsza niż data wypożyczenia przy obliczaniu kosztu.");
                return; // Nie zmieniaj kosztu, jeśli daty są nieprawidłowe
            }
            long diffInMillis = dataZwrotu.getTime() - dataWypozyczenia.getTime();
            long diffInDays = (diffInMillis / (1000 * 60 * 60 * 24));
            if (diffInMillis % (1000 * 60 * 60 * 24) > 0 || diffInDays == 0) {
                diffInDays++;
            }
            this.kosztCalkowity = diffInDays * pojazd.getCenaZaDobe();
        }
        // Jeśli dataZwrotu jest null, koszt nie jest (re)kalkulowany tutaj.
        // Koszt jest finalizowany tylko po faktycznym zwrocie.
    }

    /**
     * Zwraca reprezentację tekstową obiektu Wypozyczenie.
     * Używa nazwy firmy i NIP klienta. Pokazuje planowaną datę zwrotu, jeśli rzeczywista nie jest ustawiona.
     * @return String opisujący wypożyczenie.
     */
    @Override
    public String toString() {
        String dataWypStr = dataWypozyczenia != null ? DATE_FORMAT.format(dataWypozyczenia) : "BRAK DATY";
        String dataZwrPlanStr = planowanaDataZwrotu != null ? DATE_FORMAT.format(planowanaDataZwrotu) : "NIEZNANA";
        String statusZwrotu;

        if (dataZwrotu != null) {
            statusZwrotu = ", Zwrócono: " + DATE_FORMAT.format(dataZwrotu);
        } else {
            statusZwrotu = ", Plan. zwrot: " + dataZwrPlanStr + " (W TRAKCIE)";
        }

        String klientInfo = klient != null ? klient.getNazwaFirmy() + " (NIP: " + klient.getNip() + ")" : "BRAK KLIENTA";
        String pojazdInfo = pojazd != null ? pojazd.getMarka() + " " + pojazd.getModel() : "BRAK POJAZDU";

        return "Klient: " + klientInfo +
               ", Pojazd: " + pojazdInfo +
               ", Od: " + dataWypStr +
               statusZwrotu;
    }
}
