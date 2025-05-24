package serwiswypozyczalnimaszyn.gui;

import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Główne okno aplikacji serwisu wypożyczalni maszyn.
 * Zawiera zakładki do zarządzania klientami, pojazdami i wypożyczeniami.
 * Odpowiada za inicjalizację interfejsu użytkownika oraz obsługę zapisu danych przy zamykaniu.
 */
public class OknoGlowne extends JFrame {

    private ZarzadzanieWypozyczalnia zarzadzanie; // Obiekt zarządzający logiką
    private JTabbedPane tabbedPane; // Panel z zakładkami
    private PanelKlienci panelKlienci; // Panel do zarządzania klientami
    private PanelPojazdy panelPojazdy; // Panel do zarządzania pojazdami
    private PanelWypozyczenia panelWypozyczenia; // Panel do zarządzania wypożyczeniami

    /**
     * Konstruktor głównego okna aplikacji.
     * Inicjalizuje obiekt zarządzania, konfiguruje okno i jego komponenty.
     */
    public OknoGlowne() {
        zarzadzanie = new ZarzadzanieWypozyczalnia(); // Inicjalizacja logiki biznesowej (wczytuje dane)

        setTitle("Serwis Wypożyczalni Maszyn Budowlanych");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Zmienione, aby obsłużyć zapis przed zamknięciem
        setSize(1000, 700); // Ustawienie rozmiaru okna
        setLocationRelativeTo(null); // Wyśrodkowanie okna na ekranie
        
        // Obsługa zamknięcia okna - zapis danych
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int potwierdzenie = JOptionPane.showConfirmDialog(
                        OknoGlowne.this,
                        "Czy chcesz zapisać zmiany przed zamknięciem aplikacji?",
                        "Zapisz i zamknij",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (potwierdzenie == JOptionPane.YES_OPTION) {
                    zarzadzanie.zapiszDane();
                    dispose(); // Zamyka okno
                    System.exit(0); // Kończy aplikację
                } else if (potwierdzenie == JOptionPane.NO_OPTION) {
                    dispose();
                    System.exit(0);
                }
                // Jeśli CANCEL, nic nie rób (okno pozostaje otwarte)
            }
        });


        initComponents(); // Inicjalizacja komponentów GUI
        // Po inicjalizacji komponentów, jeśli dane zostały wczytane,
        // panele powinny zostać odświeżone, aby wyświetlić te dane.
        // To jest robione w konstruktorach paneli (np. zaladujKlientow()),
        // ale jeśli dane są wczytywane asynchronicznie lub po inicjalizacji GUI,
        // potrzebne byłoby dodatkowe odświeżenie.
        // W tym przypadku ZarzadzanieWypozyczalnia wczytuje dane w swoim konstruktorze,
        // a panele są tworzone później, więc powinny mieć dostęp do wczytanych danych.
    }

    /**
     * Inicjalizuje komponenty interfejsu użytkownika, takie jak menu i panel z zakładkami.
     */
    private void initComponents() {
        // Pasek menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuPlik = new JMenu("Plik");

        JMenuItem menuItemZapisz = new JMenuItem("Zapisz dane");
        menuItemZapisz.addActionListener(e -> {
            zarzadzanie.zapiszDane();
            JOptionPane.showMessageDialog(this, "Dane zostały zapisane.", "Zapisano", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPlik.add(menuItemZapisz);
        
        JMenuItem menuItemOdswiez = new JMenuItem("Odśwież widoki");
        menuItemOdswiez.addActionListener(e -> odswiezWszystkiePanele());
        menuPlik.add(menuItemOdswiez);

        menuPlik.addSeparator();

        JMenuItem menuItemZamknij = new JMenuItem("Zamknij");
        menuItemZamknij.addActionListener(e -> {
            // Symulacja zdarzenia zamknięcia okna, aby wywołać logikę zapisu
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        menuPlik.add(menuItemZamknij);
        menuBar.add(menuPlik);
        setJMenuBar(menuBar);


        // Panel z zakładkami
        tabbedPane = new JTabbedPane();

        panelKlienci = new PanelKlienci(zarzadzanie);
        tabbedPane.addTab("Klienci", panelKlienci);

        panelPojazdy = new PanelPojazdy(zarzadzanie);
        tabbedPane.addTab("Pojazdy", panelPojazdy);

        panelWypozyczenia = new PanelWypozyczenia(zarzadzanie);
        tabbedPane.addTab("Wypożyczenia", panelWypozyczenia);
        
        // Listener zmiany zakładek, aby odświeżać dane w panelu wypożyczeń
        // (szczególnie ComboBoxy z klientami i pojazdami)
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == panelWypozyczenia) {
                panelWypozyczenia.odswiezWszystko();
            } else if (tabbedPane.getSelectedComponent() == panelKlienci) {
                panelKlienci.odswiezListeKlientow();
            } else if (tabbedPane.getSelectedComponent() == panelPojazdy) {
                panelPojazdy.odswiezListePojazdow();
            }
        });


        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
    * Odświeża dane we wszystkich panelach.
    * Przydatne po operacjach, które mogą wpłynąć na wiele widoków, np. wczytanie danych.
    */
    private void odswiezWszystkiePanele() {
        if (panelKlienci != null) {
            panelKlienci.odswiezListeKlientow();
        }
        if (panelPojazdy != null) {
            panelPojazdy.odswiezListePojazdow();
        }
        if (panelWypozyczenia != null) {
            panelWypozyczenia.odswiezWszystko();
        }
        JOptionPane.showMessageDialog(this, "Widoki zostały odświeżone.", "Odświeżono", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Główna metoda uruchamiająca aplikację.
     * Tworzy i wyświetla główne okno w wątku zdarzeń Swing.
     * @param args Argumenty wiersza poleceń (nieużywane).
     */
    public static void main(String[] args) {
        // Ustawienie wyglądu (Look and Feel) na systemowy dla lepszej integracji
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Nie udało się ustawić systemowego Look and Feel: " + e.getMessage());
        }

        // Uruchomienie GUI w wątku dyspozytora zdarzeń Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OknoGlowne().setVisible(true);
            }
        });
    }
}
