package serwiswypozyczalnimaszyn.logika;

import serwiswypozyczalnimaszyn.model.Klient;
import serwiswypozyczalnimaszyn.model.Pojazd;
import serwiswypozyczalnimaszyn.model.Wypozyczenie;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Główna klasa logiki biznesowej aplikacji.
 * Zarządza listami klientów, pojazdów i wypożyczeń.
 * Odpowiada za operacje dodawania, usuwania, modyfikowania danych
 * oraz za ich zapis i odczyt z plików.
 * Dodano obsługę planowanej daty zwrotu i sprawdzanie dostępności pojazdu w okresie.
 */
public class ZarzadzanieWypozyczalnia {

    private List<Klient> klienci; // Lista wszystkich klientów
    private List<Pojazd> pojazdy; // Lista wszystkich pojazdów
    private List<Wypozyczenie> wypozyczenia; // Lista wszystkich wypożyczeń

    private static final String PLIK_KLIENCI = "klienci.dat";
    private static final String PLIK_POJAZDY = "pojazdy.dat";
    private static final String PLIK_WYPOZYCZENIA = "wypozyczenia.dat";

    public ZarzadzanieWypozyczalnia() {
        klienci = new ArrayList<>();
        pojazdy = new ArrayList<>();
        wypozyczenia = new ArrayList<>();
        wczytajDane();
    }

    // Metody zarządzania klientami (bez zmian)
    public void dodajKlienta(Klient klient) {
        if (klient != null) {
            klienci.add(klient);
        }
    }

    public void usunKlienta(Klient klient) {
        if (klient != null) {
            klienci.remove(klient);
        }
    }

    public List<Klient> getKlienci() {
        return new ArrayList<>(klienci);
    }

    // Metody zarządzania pojazdami (bez zmian w kontekście tej prośby)
    public void dodajPojazd(Pojazd pojazd) {
        if (pojazd != null) {
            pojazdy.add(pojazd);
        }
    }

    public void usunPojazd(Pojazd pojazd) {
        if (pojazd != null) {
            boolean czyWypozyczony = wypozyczenia.stream()
                .anyMatch(w -> w.getPojazd().equals(pojazd) && w.getDataZwrotu() == null);
            if (czyWypozyczony) {
                System.err.println("Nie można usunąć pojazdu, który jest aktualnie wypożyczony.");
                return;
            }
            wypozyczenia.removeIf(w -> w.getPojazd().equals(pojazd)); // Usuń też historyczne wypożyczenia tego pojazdu
            pojazdy.remove(pojazd);
        }
    }

    public List<Pojazd> getPojazdy() {
        return new ArrayList<>(pojazdy);
    }

    /**
     * Zwraca listę pojazdów, które są ogólnie oznaczone jako dostępne (Pojazd.isCzyDostepny()).
     * Ta metoda NIE sprawdza konfliktów z istniejącymi wypożyczeniami.
     * Do sprawdzenia dostępności w konkretnym okresie użyj {@link #czyPojazdBedzieDostepny(Pojazd, Date, Date)}.
     * @return Lista pojazdów oznaczonych jako dostępne.
     */
    public List<Pojazd> getPojazdyOgolnieDostepne() {
        return pojazdy.stream()
                .filter(Pojazd::isCzyDostepny)
                .collect(Collectors.toList());
    }
    
    /**
     * Pomocnicza metoda do usuwania informacji o godzinie, minucie, sekundzie z daty.
     * @param date Data do przetworzenia.
     * @return Data z wyzerowanymi godzinami, minutami, sekundami i milisekundami.
     */
    private Date truncateTime(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Sprawdza, czy dany pojazd będzie dostępny w podanym okresie.
     * Pojazd jest niedostępny, jeśli istnieje inne wypożyczenie tego pojazdu,
     * którego okres (od dataWypozyczenia do dataZwrotu lub planowanaDataZwrotu)
     * nachodzi na podany okres [dataOd, dataDo].
     *
     * @param pojazd Sprawdzany pojazd.
     * @param dataOd Data rozpoczęcia sprawdzanego okresu.
     * @param dataDo Data zakończenia sprawdzanego okresu.
     * @return true, jeśli pojazd jest dostępny w danym okresie, false w przeciwnym razie.
     */
    public boolean czyPojazdBedzieDostepny(Pojazd pojazd, Date dataOd, Date dataDo) {
        if (pojazd == null || dataOd == null || dataDo == null || dataOd.after(dataDo)) {
            return false; // Nieprawidłowe dane wejściowe
        }
        if (!pojazd.isCzyDostepny()){ // Sprawdzenie ogólnej flagi dostępności
            return false;
        }

        Date sprawdzanyStart = truncateTime(dataOd);
        Date sprawdzanyKoniec = truncateTime(dataDo);

        for (Wypozyczenie w : wypozyczenia) {
            if (w.getPojazd().equals(pojazd)) {
                Date wypozyczenieStart = truncateTime(w.getDataWypozyczenia());
                // Używamy rzeczywistej daty zwrotu jeśli jest, inaczej planowanej
                Date wypozyczenieKoniec = w.getDataZwrotu() != null ? truncateTime(w.getDataZwrotu()) : truncateTime(w.getPlanowanaDataZwrotu());

                if (wypozyczenieStart == null || wypozyczenieKoniec == null) {
                    continue; // Pomiń wypożyczenia z niekompletnymi danymi dat
                }
                
                // Sprawdzenie nachodzenia się okresów:
                // (StartA <= KoniecB) and (KoniecA >= StartB)
                boolean nachodzi = (sprawdzanyStart.compareTo(wypozyczenieKoniec) <= 0) && 
                                 (sprawdzanyKoniec.compareTo(wypozyczenieStart) >= 0);

                if (nachodzi) {
                    return false; // Znaleziono konflikt
                }
            }
        }
        return true; // Brak konfliktów
    }


    // Metody zarządzania wypożyczeniami

    /**
     * Tworzy nowe wypożyczenie.
     * Zmienia status pojazdu na niedostępny (jeśli to ma sens - teraz dostępność jest bardziej dynamiczna).
     * @param klient Klient wypożyczający.
     * @param pojazd Wypożyczany pojazd.
     * @param dataWypozyczenia Data rozpoczęcia wypożyczenia.
     * @param planowanaDataZwrotu Planowana data zwrotu pojazdu.
     * @return true, jeśli wypożyczenie zostało pomyślnie utworzone, false w przeciwnym razie.
     */
    public boolean wypozyczPojazd(Klient klient, Pojazd pojazd, Date dataWypozyczenia, Date planowanaDataZwrotu) {
        if (klient == null || pojazd == null || dataWypozyczenia == null || planowanaDataZwrotu == null) {
            System.err.println("Błąd: Niekompletne dane do wypożyczenia.");
            return false;
        }
        if (dataWypozyczenia.after(planowanaDataZwrotu)) {
            System.err.println("Błąd: Data wypożyczenia nie może być późniejsza niż planowana data zwrotu.");
            return false;
        }

        // Sprawdzenie dostępności pojazdu w wybranym okresie
        if (!czyPojazdBedzieDostepny(pojazd, dataWypozyczenia, planowanaDataZwrotu)) {
            System.err.println("Pojazd " + pojazd.getNumerRejestracyjny() + " nie jest dostępny w wybranym okresie.");
            return false;
        }

        Wypozyczenie wypozyczenie = new Wypozyczenie(klient, pojazd, dataWypozyczenia, planowanaDataZwrotu);
        wypozyczenia.add(wypozyczenie);
        // Flaga `czyDostepny` w klasie Pojazd może teraz służyć jako ogólny wskaźnik
        // (np. czy pojazd nie jest w serwisie), a faktyczna dostępność jest sprawdzana dynamicznie.
        // Można rozważyć usunięcie `pojazd.setCzyDostepny(false);` jeśli `czyPojazdBedzieDostepny` jest głównym mechanizmem.
        // Na razie zostawiamy dla spójności z poprzednią logiką, ale to do przemyślenia.
        // pojazd.setCzyDostepny(false); // To może być mylące, bo pojazd będzie dostępny po planowanej dacie zwrotu
        return true;
    }

    /**
     * Kończy wypożyczenie (rejestruje zwrot pojazdu).
     * Oblicza koszt. Status dostępności pojazdu jest zarządzany przez metodę {@link #czyPojazdBedzieDostepny}.
     * @param wypozyczenie Obiekt Wypozyczenie do zakończenia.
     * @param dataZwrotu Rzeczywista data zwrotu pojazdu.
     * @return true, jeśli zwrot został pomyślnie zarejestrowany, false w przeciwnym razie.
     */
    public boolean zwrocPojazd(Wypozyczenie wypozyczenie, Date dataZwrotu) {
        if (wypozyczenie != null && dataZwrotu != null && wypozyczenie.getDataZwrotu() == null) {
            if (dataZwrotu.before(wypozyczenie.getDataWypozyczenia())) {
                 System.err.println("Data zwrotu nie może być wcześniejsza niż data wypożyczenia.");
                return false;
            }
            wypozyczenie.setDataZwrotu(dataZwrotu);
            wypozyczenie.obliczKoszt();
            // pojazd.setCzyDostepny(true); // Usunięte - dostępność jest dynamiczna
            return true;
        }
        return false;
    }

    public List<Wypozyczenie> getWypozyczenia() {
        return new ArrayList<>(wypozyczenia);
    }

    public List<Wypozyczenie> getAktualneWypozyczenia() {
        return wypozyczenia.stream()
                .filter(w -> w.getDataZwrotu() == null)
                .collect(Collectors.toList());
    }

    public List<Wypozyczenie> getHistoryczneWypozyczenia() {
        return wypozyczenia.stream()
                .filter(w -> w.getDataZwrotu() != null)
                .collect(Collectors.toList());
    }

    // Metody zapisu i odczytu danych (bez zmian)
    public void zapiszDane() {
        zapiszDoPliku(PLIK_KLIENCI, klienci);
        zapiszDoPliku(PLIK_POJAZDY, pojazdy);
        zapiszDoPliku(PLIK_WYPOZYCZENIA, wypozyczenia);
        System.out.println("Dane zostały zapisane.");
    }

    @SuppressWarnings("unchecked")
    public void wczytajDane() {
        Object daneKlientow = wczytajZPliku(PLIK_KLIENCI);
        if (daneKlientow instanceof List) {
            this.klienci = (List<Klient>) daneKlientow;
        } else {
            this.klienci = new ArrayList<>();
        }

        Object danePojazdow = wczytajZPliku(PLIK_POJAZDY);
        if (danePojazdow instanceof List) {
            this.pojazdy = (List<Pojazd>) danePojazdow;
        } else {
            this.pojazdy = new ArrayList<>();
        }

        Object daneWypozyczen = wczytajZPliku(PLIK_WYPOZYCZENIA);
        if (daneWypozyczen instanceof List) {
            this.wypozyczenia = (List<Wypozyczenie>) daneWypozyczen;
        } else {
            this.wypozyczenia = new ArrayList<>();
        }
        System.out.println("Dane zostały wczytane.");
    }

    private void zapiszDoPliku(String nazwaPliku, List<?> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nazwaPliku))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku " + nazwaPliku + ": " + e.getMessage());
        }
    }

    private Object wczytajZPliku(String nazwaPliku) {
        File plik = new File(nazwaPliku);
        if (!plik.exists()) {
            System.out.println("Plik " + nazwaPliku + " nie istnieje. Zostanie utworzony przy pierwszym zapisie.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nazwaPliku))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd podczas wczytywania z pliku " + nazwaPliku + ": " + e.getMessage());
            return null;
        }
    }
}
