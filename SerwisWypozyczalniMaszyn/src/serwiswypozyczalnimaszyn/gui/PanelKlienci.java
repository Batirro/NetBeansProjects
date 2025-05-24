package serwiswypozyczalnimaszyn.gui;

import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import serwiswypozyczalnimaszyn.model.Klient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel GUI do zarządzania klientami (firmami).
 * Umożliwia dodawanie, edycję i usuwanie klientów-firm.
 * Wyświetla listę klientów-firm.
 * Poprawiono walidację NIP, aby akceptowała myślniki.
 */
public class PanelKlienci extends JPanel {

    private ZarzadzanieWypozyczalnia zarzadzanie; // Referencja do głównej logiki aplikacji
    private JList<Klient> listaKlientow; // Lista wyświetlająca klientów
    private DefaultListModel<Klient> modelListyKlientow; // Model dla JList

    // Pola tekstowe do wprowadzania danych klienta (firmy)
    private JTextField nazwaFirmyField;
    private JTextField nipField;
    private JTextField telefonField;
    private JTextField emailField;

    // Przyciski akcji
    private JButton dodajButton;
    private JButton edytujButton;
    private JButton usunButton;
    private JButton wyczyscPolaButton;

    private Klient aktualnieWybranyKlient = null; // Przechowuje aktualnie wybranego klienta z listy

    /**
     * Konstruktor Panelu Klientów (Firm).
     * @param zarzadzanie Referencja do obiektu zarządzającego logiką wypożyczalni.
     */
    public PanelKlienci(ZarzadzanieWypozyczalnia zarzadzanie) {
        this.zarzadzanie = zarzadzanie;
        setLayout(new BorderLayout(10, 10)); // Ustawienie głównego layoutu panelu
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Dodanie marginesów

        initComponents(); // Inicjalizacja komponentów GUI
        zaladujKlientow(); // Załadowanie listy klientów
    }

    /**
     * Inicjalizuje i rozmieszcza komponenty GUI na panelu.
     */
    private void initComponents() {
        // Panel z listą klientów
        JPanel panelListy = new JPanel(new BorderLayout());
        panelListy.setBorder(BorderFactory.createTitledBorder("Lista Klientów (Firm)"));
        modelListyKlientow = new DefaultListModel<>();
        listaKlientow = new JList<>(modelListyKlientow);
        listaKlientow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Tylko jeden element może być wybrany
        listaKlientow.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Reakcja tylko po zakończeniu zmiany selekcji
                    aktualnieWybranyKlient = listaKlientow.getSelectedValue();
                    wyswietlDaneKlienta(aktualnieWybranyKlient);
                    edytujButton.setEnabled(aktualnieWybranyKlient != null);
                    usunButton.setEnabled(aktualnieWybranyKlient != null);
                }
            }
        });
        panelListy.add(new JScrollPane(listaKlientow), BorderLayout.CENTER);

        // Panel z formularzem danych klienta
        JPanel panelFormularza = new JPanel(new GridBagLayout());
        panelFormularza.setBorder(BorderFactory.createTitledBorder("Dane Klienta (Firmy)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Marginesy między komponentami
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etykiety i pola tekstowe
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormularza.add(new JLabel("Nazwa firmy:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; // Pole tekstowe rozciąga się
        nazwaFirmyField = new JTextField(20);
        panelFormularza.add(nazwaFirmyField, gbc);
        gbc.weightx = 0; // Reset rozciągania

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormularza.add(new JLabel("NIP:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        nipField = new JTextField(20);
        panelFormularza.add(nipField, gbc);
        gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormularza.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        telefonField = new JTextField(20);
        panelFormularza.add(telefonField, gbc);
        gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormularza.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        emailField = new JTextField(20);
        panelFormularza.add(emailField, gbc);
        gbc.weightx = 0;

        // Panel z przyciskami
        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dodajButton = new JButton("Dodaj");
        edytujButton = new JButton("Edytuj");
        usunButton = new JButton("Usuń");
        wyczyscPolaButton = new JButton("Wyczyść Pola");

        edytujButton.setEnabled(false); // Domyślnie nieaktywny
        usunButton.setEnabled(false);   // Domyślnie nieaktywny

        panelPrzyciskow.add(dodajButton);
        panelPrzyciskow.add(edytujButton);
        panelPrzyciskow.add(usunButton);
        panelPrzyciskow.add(wyczyscPolaButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        panelFormularza.add(panelPrzyciskow, gbc);


        // Dodanie paneli do głównego panelu
        add(panelListy, BorderLayout.CENTER);
        add(panelFormularza, BorderLayout.SOUTH);

        // Dodanie ActionListenerów do przycisków
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajKlienta();
            }
        });

        edytujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edytujKlienta();
            }
        });

        usunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usunKlienta();
            }
        });

        wyczyscPolaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wyczyscPola();
                listaKlientow.clearSelection(); // Odznacz element na liście
                aktualnieWybranyKlient = null;
                edytujButton.setEnabled(false);
                usunButton.setEnabled(false);
            }
        });
    }

    /**
     * Ładuje listę klientów z logiki biznesowej do modelu listy GUI.
     */
    private void zaladujKlientow() {
        modelListyKlientow.clear();
        for (Klient k : zarzadzanie.getKlienci()) {
            modelListyKlientow.addElement(k);
        }
    }

    /**
     * Wyświetla dane wybranego klienta (firmy) w polach formularza.
     * @param klient Klient, którego dane mają być wyświetlone. Jeśli null, pola są czyszczone.
     */
    private void wyswietlDaneKlienta(Klient klient) {
        if (klient != null) {
            nazwaFirmyField.setText(klient.getNazwaFirmy());
            nipField.setText(klient.getNip()); // Wyświetlamy NIP w oryginalnym formacie, w jakim został zapisany
            telefonField.setText(klient.getNumerTelefonu());
            emailField.setText(klient.getEmail());
        } else {
            wyczyscPola();
        }
    }

    /**
     * Czyści wszystkie pola formularza.
     */
    private void wyczyscPola() {
        nazwaFirmyField.setText("");
        nipField.setText("");
        telefonField.setText("");
        emailField.setText("");
        nazwaFirmyField.requestFocus(); // Ustawienie fokusu na pierwszym polu
    }
    
    /**
     * Waliduje numer NIP.
     * Usuwa znaki niebędące cyframi i sprawdza, czy pozostało 10 cyfr.
     * @param nipInput Wprowadzony NIP jako String.
     * @return true jeśli NIP jest poprawny, false w przeciwnym razie.
     */
    private boolean walidujNip(String nipInput) {
        if (nipInput == null || nipInput.trim().isEmpty()) {
            return false;
        }
        String nipCzysty = nipInput.replaceAll("\\D", ""); // Usuwa wszystkie znaki niebędące cyframi
        return nipCzysty.length() == 10;
    }


    /**
     * Obsługuje logikę dodawania nowego klienta (firmy).
     * Pobiera dane z formularza, waliduje je i dodaje klienta poprzez ZarzadzanieWypozyczalnia.
     */
    private void dodajKlienta() {
        String nazwaFirmy = nazwaFirmyField.getText().trim();
        String nipInput = nipField.getText().trim(); // NIP wprowadzony przez użytkownika (może zawierać myślniki)
        String telefon = telefonField.getText().trim();
        String email = emailField.getText().trim();

        if (nazwaFirmy.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nazwa firmy jest wymagana.", "Błąd walidacji", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!walidujNip(nipInput)) {
             JOptionPane.showMessageDialog(this, "NIP musi składać się z 10 cyfr (myślniki są dozwolone).", "Błąd walidacji NIP", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Prosta walidacja email (można rozbudować o regex)
        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Niepoprawny format adresu email.", "Błąd walidacji", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Zapisujemy NIP w oryginalnym formacie wprowadzonym przez użytkownika, jeśli przeszedł walidację
        Klient nowyKlient = new Klient(nazwaFirmy, nipInput, telefon, email);
        zarzadzanie.dodajKlienta(nowyKlient);
        zaladujKlientow(); // Odświeżenie listy
        wyczyscPola();
        JOptionPane.showMessageDialog(this, "Klient (firma) dodany pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obsługuje logikę edycji wybranego klienta (firmy).
     * Pobiera dane z formularza, waliduje je i aktualizuje dane klienta.
     */
    private void edytujKlienta() {
        if (aktualnieWybranyKlient == null) {
            JOptionPane.showMessageDialog(this, "Najpierw wybierz klienta z listy.", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nazwaFirmy = nazwaFirmyField.getText().trim();
        String nipInput = nipField.getText().trim();
        String telefon = telefonField.getText().trim();
        String email = emailField.getText().trim();

        if (nazwaFirmy.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nazwa firmy jest wymagana.", "Błąd walidacji", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!walidujNip(nipInput)) {
             JOptionPane.showMessageDialog(this, "NIP musi składać się z 10 cyfr (myślniki są dozwolone).", "Błąd walidacji NIP", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Niepoprawny format adresu email.", "Błąd walidacji", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aktualizacja danych wybranego klienta
        aktualnieWybranyKlient.setNazwaFirmy(nazwaFirmy);
        aktualnieWybranyKlient.setNip(nipInput); // Aktualizujemy NIP na ten wprowadzony (po walidacji)
        aktualnieWybranyKlient.setNumerTelefonu(telefon);
        aktualnieWybranyKlient.setEmail(email);

        zaladujKlientow(); // Odświeżenie listy (ważne, jeśli toString() zależy od tych pól)
        listaKlientow.setSelectedValue(aktualnieWybranyKlient, true); // Ponowne zaznaczenie edytowanego klienta
        JOptionPane.showMessageDialog(this, "Dane klienta (firmy) zaktualizowane.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obsługuje logikę usuwania wybranego klienta (firmy).
     * Prosi o potwierdzenie przed usunięciem.
     */
    private void usunKlienta() {
        if (aktualnieWybranyKlient == null) {
            JOptionPane.showMessageDialog(this, "Najpierw wybierz klienta z listy.", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Sprawdzenie, czy klient ma aktywne wypożyczenia
        boolean maAktywneWypozyczenia = zarzadzanie.getAktualneWypozyczenia().stream()
                .anyMatch(w -> w.getKlient().equals(aktualnieWybranyKlient));

        if (maAktywneWypozyczenia) {
            JOptionPane.showMessageDialog(this, "Nie można usunąć klienta (firmy), który ma aktywne wypożyczenia.", "Błąd usuwania", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int potwierdzenie = JOptionPane.showConfirmDialog(this,
                "Czy na pewno chcesz usunąć klienta: " + aktualnieWybranyKlient.getNazwaFirmy() + " (NIP: " + aktualnieWybranyKlient.getNip() + ")?",
                "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (potwierdzenie == JOptionPane.YES_OPTION) {
            zarzadzanie.usunKlienta(aktualnieWybranyKlient);
            zaladujKlientow();
            wyczyscPola();
            aktualnieWybranyKlient = null;
            edytujButton.setEnabled(false);
            usunButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Klient (firma) usunięty.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Metoda publiczna do odświeżania listy klientów z zewnątrz, np. po wczytaniu danych.
     */
    public void odswiezListeKlientow() {
        zaladujKlientow();
    }
}
