package serwiswypozyczalnimaszyn;

import serwiswypozyczalnimaszyn.gui.OknoGlowne;
import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import serwiswypozyczalnimaszyn.model.*;
import java.time.LocalDate;
import javax.swing.SwingUtilities;
import java.util.Optional;

public class SerwisWypozyczalniMaszyn {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // Lambda dla zwięzłości
            ZarzadzanieWypozyczalnia zarzadzanie = new ZarzadzanieWypozyczalnia();
            dodajDaneTestowe(zarzadzanie);

            OknoGlowne okno = new OknoGlowne(zarzadzanie);
            okno.setVisible(true);
        });
    }

    private static void dodajDaneTestowe(ZarzadzanieWypozyczalnia zarzadzanie) {
        Pojazd k1 = new Koparka(zarzadzanie.pobierzNastepneIdPojazdu(), "CAT 320D", 2018, 6.5, "gąsienicowe", 350.00);
        Pojazd w1 = new Wywrotka(zarzadzanie.pobierzNastepneIdPojazdu(), "MAN TGS", 2020, 26.0, 4, 450.00);
        Pojazd d1 = new Dzwig(zarzadzanie.pobierzNastepneIdPojazdu(), "Liebherr LTM 1050", 2019, 50.0, 40.0, 1200.00);
        Pojazd k2 = new Koparka(zarzadzanie.pobierzNastepneIdPojazdu(), "JCB 220X", 2021, 7.0, "gąsienicowe", 400.00);


        zarzadzanie.dodajPojazd(k1);
        zarzadzanie.dodajPojazd(w1);
        zarzadzanie.dodajPojazd(d1);
        zarzadzanie.dodajPojazd(k2);


        Klient c1 = new Klient(zarzadzanie.pobierzNastepneIdKlienta(), "BudMax GUI S.A.", "111-222-33-44");
        Klient c2 = new Klient(zarzadzanie.pobierzNastepneIdKlienta(), "TransKop GUI Sp. z o.o.", "555-666-77-88");

        zarzadzanie.dodajKlienta(c1);
        zarzadzanie.dodajKlienta(c2);

        LocalDate dataStart1 = LocalDate.now().minusDays(10);
        LocalDate planDataKoniec1 = dataStart1.plusDays(7);
        Optional<Wypozyczenie> wyp1Opt = zarzadzanie.zarejestrujWypozyczenie(c1.getId(), k1.getId(), dataStart1, planDataKoniec1);
        // Zakończmy to wypożyczenie z małym opóźnieniem
        if (wyp1Opt.isPresent()) {
            zarzadzanie.zakonczWypozyczenie(wyp1Opt.get().getId(), planDataKoniec1.plusDays(1));
        }


        LocalDate dataStart2 = LocalDate.now().minusDays(3);
        LocalDate planDataKoniec2 = dataStart2.plusDays(5);
        zarzadzanie.zarejestrujWypozyczenie(c2.getId(), w1.getId(), dataStart2, planDataKoniec2); // To pozostanie aktywne

        System.out.println("\n--- Dodano dane testowe dla GUI. ---\n");
    }
}