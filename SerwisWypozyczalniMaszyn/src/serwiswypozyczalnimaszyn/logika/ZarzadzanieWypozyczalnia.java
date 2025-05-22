package serwiswypozyczalnimaszyn.logika;

import serwiswypozyczalnimaszyn.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

public class ZarzadzanieWypozyczalnia {
    private List<Pojazd> listaPojazdow;
    private List<Klient> listaKlientow;
    private List<Wypozyczenie> listaWypozyczen;

    private int nastepneIdPojazdu = 1;
    private int nastepneIdKlienta = 1;
    private int nastepneIdWypozyczenia = 1;

    public ZarzadzanieWypozyczalnia() {
        this.listaPojazdow = new ArrayList<>();
        this.listaKlientow = new ArrayList<>();
        this.listaWypozyczen = new ArrayList<>();
    }

    // --- METODY POMOCNICZE DO GENEROWANIA ID ---
    public int pobierzNastepneIdPojazdu() { return nastepneIdPojazdu++; }
    public int pobierzNastepneIdKlienta() { return nastepneIdKlienta++; }
    private int generujIdWypozyczenia() { return nastepneIdWypozyczenia++; }

    // --- ZARZĄDZANIE POJAZDAMI ---
    public void dodajPojazd(Pojazd pojazd) {
        if (pojazd != null) {
            if (znajdzPojazd(pojazd.getId()).isEmpty()) {
                this.listaPojazdow.add(pojazd);
                System.out.println("INFO: Dodano pojazd: " + pojazd.getNazwaModelu());
            } else {
                System.err.println("BŁĄD: Pojazd o ID " + pojazd.getId() + " już istnieje!");
            }
        }
    }

    public boolean usunPojazd(int idPojazdu) {
        Optional<Pojazd> pojazdDoUsunieciaOpt = znajdzPojazd(idPojazdu);
        if (pojazdDoUsunieciaOpt.isPresent()) {
            Pojazd pojazd = pojazdDoUsunieciaOpt.get();
            boolean jestWypozyczonyAktywnie = listaWypozyczen.stream()
                    .anyMatch(w -> w.getPojazd().getId() == idPojazdu && w.isAktywne());
            if (jestWypozyczonyAktywnie) {
                System.err.println("BŁĄD: Nie można usunąć pojazdu (ID: " + idPojazdu + "), jest aktualnie wypożyczony.");
                return false;
            }
            listaPojazdow.remove(pojazd);
            System.out.println("INFO: Usunięto pojazd: " + pojazd.getNazwaModelu());
            return true;
        }
        System.err.println("BŁĄD: Nie znaleziono pojazdu o ID: " + idPojazdu + " do usunięcia.");
        return false;
    }

    public Optional<Pojazd> znajdzPojazd(int idPojazdu) {
        return listaPojazdow.stream().filter(p -> p.getId() == idPojazdu).findFirst();
    }
    public List<Pojazd> pobierzWszystkiePojazdy() { return new ArrayList<>(this.listaPojazdow); }
    public List<Pojazd> pobierzDostepnePojazdy() {
        return this.listaPojazdow.stream().filter(Pojazd::isDostepny).collect(Collectors.toList());
    }

    // --- ZARZĄDZANIE KLIENTAMI ---
    public void dodajKlienta(Klient klient) {
        if (klient != null) {
            if (znajdzKlienta(klient.getId()).isEmpty() && znajdzKlientaPoNip(klient.getNumerNIP()).isEmpty()) {
                this.listaKlientow.add(klient);
                System.out.println("INFO: Dodano klienta: " + klient.getNazwaFirmy());
            } else {
                System.err.println("BŁĄD: Klient o ID " + klient.getId() + " lub NIP " + klient.getNumerNIP() + " już istnieje!");
            }
        }
    }

    public boolean usunKlienta(int idKlienta) {
        Optional<Klient> klientOpt = znajdzKlienta(idKlienta);
        if (klientOpt.isPresent()) {
            boolean maAktywne = listaWypozyczen.stream().anyMatch(w -> w.getKlient().getId() == idKlienta && w.isAktywne());
            if (maAktywne) {
                System.err.println("BŁĄD: Nie można usunąć klienta (ID: " + idKlienta + "), ma aktywne wypożyczenia.");
                return false;
            }
            this.listaKlientow.remove(klientOpt.get());
            System.out.println("INFO: Usunięto klienta: " + klientOpt.get().getNazwaFirmy());
            return true;
        }
        System.err.println("BŁĄD: Nie znaleziono klienta o ID: " + idKlienta + " do usunięcia.");
        return false;
    }

    public boolean edytujKlienta(int idKlienta, String nowaNazwa, String nowyNip) {
        Optional<Klient> klientOpt = znajdzKlienta(idKlienta);
        if (klientOpt.isPresent()) {
            Klient klient = klientOpt.get();
            Optional<Klient> klientZNipem = znajdzKlientaPoNip(nowyNip);
            if (klientZNipem.isPresent() && klientZNipem.get().getId() != idKlienta) {
                System.err.println("BŁĄD: Inny klient o NIP " + nowyNip + " już istnieje!");
                return false;
            }
            klient.setNazwaFirmy(nowaNazwa);
            klient.setNumerNIP(nowyNip);
            System.out.println("INFO: Zaktualizowano dane klienta: " + klient.getNazwaFirmy());
            return true;
        }
        System.err.println("BŁĄD: Nie znaleziono klienta o ID: " + idKlienta + " do edycji.");
        return false;
    }

    public Optional<Klient> znajdzKlienta(int idKlienta) {
        return listaKlientow.stream().filter(k -> k.getId() == idKlienta).findFirst();
    }
    public Optional<Klient> znajdzKlientaPoNip(String numerNIP) {
        if (numerNIP == null || numerNIP.trim().isEmpty()) return Optional.empty();
        return listaKlientow.stream().filter(k -> numerNIP.equals(k.getNumerNIP())).findFirst();
    }
    public List<Klient> pobierzWszystkichKlientow() { return new ArrayList<>(this.listaKlientow); }

    // --- ZARZĄDZANIE WYPOŻYCZENIAMI ---
    public Optional<Wypozyczenie> zarejestrujWypozyczenie(int idKlienta, int idPojazdu, LocalDate dataRozpoczecia, LocalDate planowanaDataZakonczenia) {
        Optional<Klient> klientOpt = znajdzKlienta(idKlienta);
        Optional<Pojazd> pojazOpt = znajdzPojazd(idPojazdu);

        if (klientOpt.isEmpty()) { System.err.println("BŁĄD: Nie znaleziono klienta o ID: " + idKlienta); return Optional.empty(); }
        if (pojazOpt.isEmpty()) { System.err.println("BŁĄD: Nie znaleziono pojazdu o ID: " + idPojazdu); return Optional.empty(); }
        if (planowanaDataZakonczenia.isBefore(dataRozpoczecia)) {
            System.err.println("BŁĄD: Planowana data zakończenia nie może być wcześniejsza niż data rozpoczęcia.");
            return Optional.empty();
        }
        Pojazd pojazd = pojazOpt.get();
        if (!pojazd.isDostepny()) {
            System.err.println("BŁĄD: Pojazd " + pojazd.getNazwaModelu() + " (ID: " + idPojazdu + ") jest niedostępny.");
            return Optional.empty();
        }

        int idWypozyczenia = generujIdWypozyczenia();
        Wypozyczenie noweWypozyczenie = new Wypozyczenie(idWypozyczenia, klientOpt.get(), pojazd, dataRozpoczecia, planowanaDataZakonczenia);
        this.listaWypozyczen.add(noweWypozyczenie);
        pojazd.setDostepny(false);
        System.out.println("INFO: Zarejestrowano wypożyczenie ID: " + idWypozyczenia + " dla " + klientOpt.get().getNazwaFirmy() +
                ", pojazd " + pojazd.getNazwaModelu() + ". Plan. zwrot: " + planowanaDataZakonczenia.format(DateTimeFormatter.ISO_DATE));
        return Optional.of(noweWypozyczenie);
    }

    public boolean zakonczWypozyczenie(int idWypozyczenia, LocalDate faktycznaDataZwrotu) {
        Optional<Wypozyczenie> wypozyczenieOpt = znajdzWypozyczenie(idWypozyczenia);
        if (wypozyczenieOpt.isEmpty()) { System.err.println("BŁĄD: Nie znaleziono wypożyczenia o ID: " + idWypozyczenia); return false; }
        Wypozyczenie wypozyczenie = wypozyczenieOpt.get();
        if (!wypozyczenie.isAktywne()) { System.err.println("BŁĄD: Wypożyczenie ID: " + idWypozyczenia + " już zakończone."); return false; }
        if (faktycznaDataZwrotu.isBefore(wypozyczenie.getDataRozpoczecia())) {
            System.err.println("BŁĄD: Faktyczna data zwrotu nie może być wcześniejsza niż data rozpoczęcia.");
            return false;
        }

        wypozyczenie.zakonczWypozyczenie(faktycznaDataZwrotu);
        Pojazd pojazd = wypozyczenie.getPojazd();
        if (pojazd != null) {
            pojazd.setDostepny(true);
            System.out.println("INFO: Zakończono wypożyczenie ID: " + idWypozyczenia +
                    ". Pojazd " + pojazd.getNazwaModelu() + " dostępny. " +
                    "Koszt: " + String.format("%.2f", wypozyczenie.getKosztCalkowity()) + " PLN");
        } else { System.err.println("KRYTYCZNY BŁĄD: Zakończono wypożyczenie ID: " + idWypozyczenia + " bez powiązanego pojazdu!"); }
        return true;
    }

    public Optional<Wypozyczenie> znajdzWypozyczenie(int idWypozyczenia) {
        return this.listaWypozyczen.stream().filter(w -> w.getId() == idWypozyczenia).findFirst();
    }
    public List<Wypozyczenie> pobierzWszystkieWypozyczenia() {
        return new ArrayList<>(this.listaWypozyczen);
    }
    public List<Wypozyczenie> pobierzAktywneWypozyczenia() {
        return this.listaWypozyczen.stream().filter(Wypozyczenie::isAktywne).collect(Collectors.toList());
    }
    public List<Wypozyczenie> pobierzHistorieWypozyczenKlienta(int idKlienta) {
        Optional<Klient> kOpt = znajdzKlienta(idKlienta);
        if(kOpt.isEmpty()) {
            System.out.println("INFO: Brak klienta o ID " + idKlienta + " do pobrania historii.");
            return new ArrayList<>();
        }
        return this.listaWypozyczen.stream().filter(w->w.getKlient().getId() == idKlienta).collect(Collectors.toList());
    }
}