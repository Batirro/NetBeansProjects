package serwiswypozyczalnimaszyn.gui;

import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import javax.swing.*;
import java.awt.*;

public class OknoGlowne extends JFrame {

    private ZarzadzanieWypozyczalnia zarzadzanie;
    private JTabbedPane zakladkiPane;
    private PanelPojazdy panelPojazdy;
    private PanelKlienci panelKlienci;
    private PanelWypozyczenia panelWypozyczenia;

    public OknoGlowne(ZarzadzanieWypozyczalnia zarzadzanie) {
        this.zarzadzanie = zarzadzanie;
        setTitle("Serwis Wypożyczalni Maszyn Budowlanych");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700); // Zwiększony rozmiar dla dodatkowych kolumn
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        zakladkiPane = new JTabbedPane();

        panelPojazdy = new PanelPojazdy(zarzadzanie, this);
        panelKlienci = new PanelKlienci(zarzadzanie, this);
        panelWypozyczenia = new PanelWypozyczenia(zarzadzanie, this);

        zakladkiPane.addTab("Pojazdy", null, panelPojazdy, "Zarządzanie flotą pojazdów");
        zakladkiPane.addTab("Klienci", null, panelKlienci, "Zarządzanie bazą klientów");
        zakladkiPane.addTab("Wypożyczenia", null, panelWypozyczenia, "Zarządzanie wypożyczeniami");

        add(zakladkiPane);
    }

    public void odswiezWszystkiePanele() {
        if (panelPojazdy != null) {
            panelPojazdy.odswiezTabelePojazdow();
        }
        if (panelKlienci != null) {
            panelKlienci.odswiezTabeleKlientow();
        }
        if (panelWypozyczenia != null) {
            panelWypozyczenia.odswiezTabeleWypozyczen();
        }
    }
}