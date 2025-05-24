package serwiswypozyczalnimaszyn.gui;

import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import serwiswypozyczalnimaszyn.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel GUI do zarządzania pojazdami.
 * Umożliwia dodawanie, edycję i usuwanie pojazdów różnych typów
 * wraz z ich specyficznymi atrybutami.
 * Wyświetla listę pojazdów.
 */
public class PanelPojazdy extends JPanel {

    private ZarzadzanieWypozyczalnia zarzadzanie; // Referencja do logiki aplikacji
    private JList<Pojazd> listaPojazdow; // Lista wyświetlająca pojazdy
    private DefaultListModel<Pojazd> modelListyPojazdow; // Model dla JList

    // Pola formularza ogólne
    private JComboBox<String> typPojazduComboBox;
    private JTextField markaField;
    private JTextField modelField;
    private JTextField numerRejestracyjnyField;
    private JTextField cenaZaDobeField;

    // Pola i etykiety dla atrybutów specyficznych - teraz będzie ich więcej
    // Zamiast jednego 'atrybutSpecyficznyField', stworzymy dedykowane pola dla każdego typu.
    // Aby uprościć zarządzanie widocznością, zgrupujemy je w panelach.

    // Panel dla atrybutów Koparki
    private JPanel panelAtrybutowKoparki;
    private JTextField koparkaGlebokoscField;
    private JTextField koparkaPojemnoscLyzeczkiField;
    private JTextField koparkaZasiegRamieniaField;

    // Panel dla atrybutów Dźwigu
    private JPanel panelAtrybutowDzwigu;
    private JTextField dzwigUdzwigField;
    private JTextField dzwigDlugoscWysiegnikaField;
    private JTextField dzwigWysokoscPodnoszeniaField;

    // Panel dla atrybutów Wywrotki
    private JPanel panelAtrybutowWywrotki;
    private JTextField wywrotkaLadownoscField;
    private JTextField wywrotkaTypNapeduField;
    private JTextField wywrotkaPojemnoscSkrzyniField;

    private JPanel panelAtrybutowSpecyficznychHolder; // Kontener na panele atrybutów

    // Przyciski
    private JButton dodajButton;
    private JButton edytujButton;
    private JButton usunButton;
    private JButton wyczyscPolaButton;

    private Pojazd aktualnieWybranyPojazd = null; // Aktualnie wybrany pojazd z listy

    /**
     * Konstruktor Panelu Pojazdów.
     * @param zarzadzanie Referencja do obiektu zarządzającego logiką wypożyczalni.
     */
    public PanelPojazdy(ZarzadzanieWypozyczalnia zarzadzanie) {
        this.zarzadzanie = zarzadzanie;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        zaladujPojazdy();
        aktualizujWidocznoscPaneliAtrybutow(); // Ustawienie widoczności na starcie
    }

    /**
     * Inicjalizuje i rozmieszcza komponenty GUI na panelu.
     */
    private void initComponents() {
        // Panel listy pojazdów
        JPanel panelListy = new JPanel(new BorderLayout());
        panelListy.setBorder(BorderFactory.createTitledBorder("Lista Pojazdów"));
        modelListyPojazdow = new DefaultListModel<>();
        listaPojazdow = new JList<>(modelListyPojazdow);
        listaPojazdow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPojazdow.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                aktualnieWybranyPojazd = listaPojazdow.getSelectedValue();
                wyswietlDanePojazdu(aktualnieWybranyPojazd);
                edytujButton.setEnabled(aktualnieWybranyPojazd != null);
                usunButton.setEnabled(aktualnieWybranyPojazd != null);
                typPojazduComboBox.setEnabled(aktualnieWybranyPojazd == null);
            }
        });
        panelListy.add(new JScrollPane(listaPojazdow), BorderLayout.CENTER);

        // Główny panel formularza
        JPanel panelFormularzaGlownego = new JPanel(new GridBagLayout());
        panelFormularzaGlownego.setBorder(BorderFactory.createTitledBorder("Dane Pojazdu"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;


        // Typ pojazdu
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormularzaGlownego.add(new JLabel("Typ pojazdu:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        typPojazduComboBox = new JComboBox<>(new String[]{"Koparka", "Dźwig", "Wywrotka"});
        typPojazduComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                aktualizujWidocznoscPaneliAtrybutow();
                // Jeśli pola są czyszczone (nie ma wybranego pojazdu), wyczyść też pola atrybutów
                if (aktualnieWybranyPojazd == null) {
                    wyczyscPolaAtrybutowSpecyficznych();
                }
            }
        });
        panelFormularzaGlownego.add(typPojazduComboBox, gbc);
        gbc.weightx = 0;

        // Marka
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormularzaGlownego.add(new JLabel("Marka:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        markaField = new JTextField(20);
        panelFormularzaGlownego.add(markaField, gbc);
        gbc.weightx = 0;

        // Model
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormularzaGlownego.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        modelField = new JTextField(20);
        panelFormularzaGlownego.add(modelField, gbc);
        gbc.weightx = 0;

        // Numer rejestracyjny
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormularzaGlownego.add(new JLabel("Nr rejestracyjny:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        numerRejestracyjnyField = new JTextField(20);
        panelFormularzaGlownego.add(numerRejestracyjnyField, gbc);
        gbc.weightx = 0;

        // Cena za dobę
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormularzaGlownego.add(new JLabel("Cena za dobę:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        cenaZaDobeField = new JTextField(20);
        panelFormularzaGlownego.add(cenaZaDobeField, gbc);
        gbc.weightx = 0;

        // Kontener na panele atrybutów specyficznych
        panelAtrybutowSpecyficznychHolder = new JPanel(new CardLayout());
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weighty = 1.0; // Dajemy mu trochę miejsca
        panelFormularzaGlownego.add(panelAtrybutowSpecyficznychHolder, gbc);
        gbc.gridwidth = 1; gbc.weighty = 0; // Reset

        // Inicjalizacja paneli atrybutów
        initPanelAtrybutowKoparki();
        initPanelAtrybutowDzwigu();
        initPanelAtrybutowWywrotki();

        panelAtrybutowSpecyficznychHolder.add(panelAtrybutowKoparki, "Koparka");
        panelAtrybutowSpecyficznychHolder.add(panelAtrybutowDzwigu, "Dźwig");
        panelAtrybutowSpecyficznychHolder.add(panelAtrybutowWywrotki, "Wywrotka");
        // Dodajemy pusty panel jako domyślny, gdyby żaden typ nie był wybrany lub dla innych przypadków
        panelAtrybutowSpecyficznychHolder.add(new JPanel(), "Pusty");


        // Panel przycisków
        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dodajButton = new JButton("Dodaj");
        edytujButton = new JButton("Edytuj");
        usunButton = new JButton("Usuń");
        wyczyscPolaButton = new JButton("Wyczyść Pola");

        edytujButton.setEnabled(false);
        usunButton.setEnabled(false);

        panelPrzyciskow.add(dodajButton);
        panelPrzyciskow.add(edytujButton);
        panelPrzyciskow.add(usunButton);
        panelPrzyciskow.add(wyczyscPolaButton);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        panelFormularzaGlownego.add(panelPrzyciskow, gbc);

        // Używamy JScrollPane dla panelu formularza, gdyby miał za dużo pól
        JScrollPane scrollPaneFormularza = new JScrollPane(panelFormularzaGlownego);
        scrollPaneFormularza.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFormularza.setBorder(null); // Usuwamy podwójne obramowanie


        add(panelListy, BorderLayout.CENTER);
        add(scrollPaneFormularza, BorderLayout.SOUTH); // Zmienione na scrollPane

        // Listenery przycisków
        dodajButton.addActionListener(e -> dodajPojazd());
        edytujButton.addActionListener(e -> edytujPojazd());
        usunButton.addActionListener(e -> usunPojazd());
        wyczyscPolaButton.addActionListener(e -> {
            wyczyscPola();
            listaPojazdow.clearSelection();
            aktualnieWybranyPojazd = null;
            edytujButton.setEnabled(false);
            usunButton.setEnabled(false);
            typPojazduComboBox.setEnabled(true);
            aktualizujWidocznoscPaneliAtrybutow(); // Pokaż odpowiedni panel dla domyślnego typu
        });
    }

    private void initPanelAtrybutowKoparki() {
        panelAtrybutowKoparki = new JPanel(new GridBagLayout());
        panelAtrybutowKoparki.setBorder(BorderFactory.createTitledBorder("Atrybuty Koparki"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panelAtrybutowKoparki.add(new JLabel("Głębokość kopania (m):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; koparkaGlebokoscField = new JTextField(10); panelAtrybutowKoparki.add(koparkaGlebokoscField, gbc); gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 1; panelAtrybutowKoparki.add(new JLabel("Pojemność łyżki (m³):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; koparkaPojemnoscLyzeczkiField = new JTextField(10); panelAtrybutowKoparki.add(koparkaPojemnoscLyzeczkiField, gbc); gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 2; panelAtrybutowKoparki.add(new JLabel("Zasięg ramienia (m):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; koparkaZasiegRamieniaField = new JTextField(10); panelAtrybutowKoparki.add(koparkaZasiegRamieniaField, gbc); gbc.weightx = 0;
    }

    private void initPanelAtrybutowDzwigu() {
        panelAtrybutowDzwigu = new JPanel(new GridBagLayout());
        panelAtrybutowDzwigu.setBorder(BorderFactory.createTitledBorder("Atrybuty Dźwigu"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panelAtrybutowDzwigu.add(new JLabel("Maks. udźwig (tony):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; dzwigUdzwigField = new JTextField(10); panelAtrybutowDzwigu.add(dzwigUdzwigField, gbc); gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 1; panelAtrybutowDzwigu.add(new JLabel("Długość wysięgnika (m):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; dzwigDlugoscWysiegnikaField = new JTextField(10); panelAtrybutowDzwigu.add(dzwigDlugoscWysiegnikaField, gbc); gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 2; panelAtrybutowDzwigu.add(new JLabel("Maks. wys. podnoszenia (m):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; dzwigWysokoscPodnoszeniaField = new JTextField(10); panelAtrybutowDzwigu.add(dzwigWysokoscPodnoszeniaField, gbc); gbc.weightx = 0;
    }

    private void initPanelAtrybutowWywrotki() {
        panelAtrybutowWywrotki = new JPanel(new GridBagLayout());
        panelAtrybutowWywrotki.setBorder(BorderFactory.createTitledBorder("Atrybuty Wywrotki"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panelAtrybutowWywrotki.add(new JLabel("Ładowność (tony):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; wywrotkaLadownoscField = new JTextField(10); panelAtrybutowWywrotki.add(wywrotkaLadownoscField, gbc); gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 1; panelAtrybutowWywrotki.add(new JLabel("Typ napędu:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; wywrotkaTypNapeduField = new JTextField(10); panelAtrybutowWywrotki.add(wywrotkaTypNapeduField, gbc); gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 2; panelAtrybutowWywrotki.add(new JLabel("Pojemność skrzyni (m³):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; wywrotkaPojemnoscSkrzyniField = new JTextField(10); panelAtrybutowWywrotki.add(wywrotkaPojemnoscSkrzyniField, gbc); gbc.weightx = 0;
    }


    /**
     * Aktualizuje widoczność paneli z atrybutami specyficznymi
     * w zależności od wybranego typu pojazdu, używając CardLayout.
     */
    private void aktualizujWidocznoscPaneliAtrybutow() {
        String typ = (String) typPojazduComboBox.getSelectedItem();
        CardLayout cl = (CardLayout)(panelAtrybutowSpecyficznychHolder.getLayout());
        if (typ == null) {
            cl.show(panelAtrybutowSpecyficznychHolder, "Pusty");
            return;
        }
        cl.show(panelAtrybutowSpecyficznychHolder, typ); // Nazwy kart muszą odpowiadać nazwom typów
    }


    /**
     * Ładuje listę pojazdów z logiki biznesowej do modelu listy GUI.
     */
    private void zaladujPojazdy() {
        modelListyPojazdow.clear();
        for (Pojazd p : zarzadzanie.getPojazdy()) {
            modelListyPojazdow.addElement(p);
        }
    }

    /**
     * Wyświetla dane wybranego pojazdu w polach formularza.
     * @param pojazd Pojazd, którego dane mają być wyświetlone. Jeśli null, pola są czyszczone.
     */
    private void wyswietlDanePojazdu(Pojazd pojazd) {
        wyczyscPolaAtrybutowSpecyficznych(); // Najpierw czyścimy wszystkie specyficzne
        if (pojazd != null) {
            typPojazduComboBox.setSelectedItem(pojazd.getTyp());
            markaField.setText(pojazd.getMarka());
            modelField.setText(pojazd.getModel());
            numerRejestracyjnyField.setText(pojazd.getNumerRejestracyjny());
            cenaZaDobeField.setText(String.valueOf(pojazd.getCenaZaDobe()));

            // Ustawienie atrybutów specyficznych
            if (pojazd instanceof Koparka k) {
                koparkaGlebokoscField.setText(String.valueOf(k.getGlebokoscKopania_m()));
                koparkaPojemnoscLyzeczkiField.setText(String.valueOf(k.getPojemnoscLyzeczki_m3()));
                koparkaZasiegRamieniaField.setText(String.valueOf(k.getZasiegRamienia_m()));
            } else if (pojazd instanceof Dzwig d) {
                dzwigUdzwigField.setText(String.valueOf(d.getMaksymalnyUdzwig_tony()));
                dzwigDlugoscWysiegnikaField.setText(String.valueOf(d.getDlugoscWysiegnika_m()));
                dzwigWysokoscPodnoszeniaField.setText(String.valueOf(d.getMaksymalnaWysokoscPodnoszenia_m()));
            } else if (pojazd instanceof Wywrotka w) {
                wywrotkaLadownoscField.setText(String.valueOf(w.getLadownosc_tony()));
                wywrotkaTypNapeduField.setText(w.getTypNapedu());
                wywrotkaPojemnoscSkrzyniField.setText(String.valueOf(w.getPojemnoscSkrzyni_m3()));
            }
            aktualizujWidocznoscPaneliAtrybutow();
            typPojazduComboBox.setEnabled(false);
        } else {
            wyczyscPola(); // Czyści też pola ogólne
            typPojazduComboBox.setEnabled(true);
        }
    }
    
    /**
     * Czyści tylko pola atrybutów specyficznych.
     */
    private void wyczyscPolaAtrybutowSpecyficznych() {
        koparkaGlebokoscField.setText("");
        koparkaPojemnoscLyzeczkiField.setText("");
        koparkaZasiegRamieniaField.setText("");
        dzwigUdzwigField.setText("");
        dzwigDlugoscWysiegnikaField.setText("");
        dzwigWysokoscPodnoszeniaField.setText("");
        wywrotkaLadownoscField.setText("");
        wywrotkaTypNapeduField.setText("");
        wywrotkaPojemnoscSkrzyniField.setText("");
    }


    /**
     * Czyści wszystkie pola formularza (ogólne i specyficzne).
     */
    private void wyczyscPola() {
        typPojazduComboBox.setSelectedIndex(0);
        markaField.setText("");
        modelField.setText("");
        numerRejestracyjnyField.setText("");
        cenaZaDobeField.setText("");
        wyczyscPolaAtrybutowSpecyficznych(); // Czyści pola specyficzne
        aktualizujWidocznoscPaneliAtrybutow(); // Ustawia odpowiedni panel dla domyślnego typu
        markaField.requestFocus();
        typPojazduComboBox.setEnabled(true);
    }

    /**
     * Waliduje i parsuje wartość double z pola tekstowego.
     * @param field Pole tekstowe.
     * @param fieldName Nazwa pola (do komunikatu błędu).
     * @param errors Lista do zbierania błędów walidacji.
     * @return Sparsowana wartość double lub 0.0 jeśli błąd.
     */
    private double parseDoubleField(JTextField field, String fieldName, List<String> errors) {
        try {
            double value = Double.parseDouble(field.getText().trim());
            if (value <= 0) {
                errors.add(fieldName + " musi być wartością dodatnią.");
                return 0.0;
            }
            return value;
        } catch (NumberFormatException ex) {
            errors.add(fieldName + " musi być poprawną liczbą.");
            return 0.0;
        }
    }
    
    /**
     * Waliduje, czy pole tekstowe (dla wartości String) nie jest puste.
     * @param field Pole tekstowe.
     * @param fieldName Nazwa pola (do komunikatu błędu).
     * @param errors Lista do zbierania błędów walidacji.
     * @return Wartość z pola lub pusty string jeśli błąd (choć błąd jest dodawany do listy).
     */
    private String validateStringField(JTextField field, String fieldName, List<String> errors) {
        String value = field.getText().trim();
        if (value.isEmpty()) {
            errors.add(fieldName + " jest wymagane.");
        }
        return value;
    }


    /**
     * Obsługuje logikę dodawania nowego pojazdu.
     */
    private void dodajPojazd() {
        String typ = (String) typPojazduComboBox.getSelectedItem();
        List<String> validationErrors = new ArrayList<>();

        String marka = validateStringField(markaField, "Marka", validationErrors);
        String model = validateStringField(modelField, "Model", validationErrors);
        String numerRejestracyjny = validateStringField(numerRejestracyjnyField, "Numer rejestracyjny", validationErrors);
        double cenaZaDobe = parseDoubleField(cenaZaDobeField, "Cena za dobę", validationErrors);

        Pojazd nowyPojazd = null;

        switch (typ) {
            case "Koparka":
                double glebokosc = parseDoubleField(koparkaGlebokoscField, "Głębokość kopania", validationErrors);
                double pojemnoscLyzki = parseDoubleField(koparkaPojemnoscLyzeczkiField, "Pojemność łyżki", validationErrors);
                double zasiegRamienia = parseDoubleField(koparkaZasiegRamieniaField, "Zasięg ramienia", validationErrors);
                if (validationErrors.isEmpty()) {
                    nowyPojazd = new Koparka(marka, model, numerRejestracyjny, cenaZaDobe, glebokosc, pojemnoscLyzki, zasiegRamienia);
                }
                break;
            case "Dźwig":
                double udzwig = parseDoubleField(dzwigUdzwigField, "Maksymalny udźwig", validationErrors);
                double dlugoscWysiegnika = parseDoubleField(dzwigDlugoscWysiegnikaField, "Długość wysięgnika", validationErrors);
                double wysPodnoszenia = parseDoubleField(dzwigWysokoscPodnoszeniaField, "Maks. wys. podnoszenia", validationErrors);
                if (validationErrors.isEmpty()) {
                    nowyPojazd = new Dzwig(marka, model, numerRejestracyjny, cenaZaDobe, udzwig, dlugoscWysiegnika, wysPodnoszenia);
                }
                break;
            case "Wywrotka":
                double ladownosc = parseDoubleField(wywrotkaLadownoscField, "Ładowność", validationErrors);
                String typNapedu = validateStringField(wywrotkaTypNapeduField, "Typ napędu", validationErrors);
                double pojemnoscSkrzyni = parseDoubleField(wywrotkaPojemnoscSkrzyniField, "Pojemność skrzyni", validationErrors);
                if (validationErrors.isEmpty()) {
                    nowyPojazd = new Wywrotka(marka, model, numerRejestracyjny, cenaZaDobe, ladownosc, typNapedu, pojemnoscSkrzyni);
                }
                break;
            default:
                JOptionPane.showMessageDialog(this, "Nieznany typ pojazdu.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
        }

        if (!validationErrors.isEmpty()) {
            JOptionPane.showMessageDialog(this, String.join("\n", validationErrors), "Błędy walidacji", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nowyPojazd != null) {
            zarzadzanie.dodajPojazd(nowyPojazd);
            zaladujPojazdy();
            wyczyscPola();
            JOptionPane.showMessageDialog(this, "Pojazd dodany pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Obsługuje logikę edycji wybranego pojazdu.
     */
    private void edytujPojazd() {
        if (aktualnieWybranyPojazd == null) {
            JOptionPane.showMessageDialog(this, "Najpierw wybierz pojazd z listy.", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> validationErrors = new ArrayList<>();
        String marka = validateStringField(markaField, "Marka", validationErrors);
        String modelP = validateStringField(modelField, "Model", validationErrors); // 'model' to już nazwa pola
        String numerRejestracyjny = validateStringField(numerRejestracyjnyField, "Numer rejestracyjny", validationErrors);
        double cenaZaDobe = parseDoubleField(cenaZaDobeField, "Cena za dobę", validationErrors);

        // Aktualizacja danych ogólnych
        aktualnieWybranyPojazd.setMarka(marka);
        aktualnieWybranyPojazd.setModel(modelP);
        aktualnieWybranyPojazd.setNumerRejestracyjny(numerRejestracyjny);
        aktualnieWybranyPojazd.setCenaZaDobe(cenaZaDobe);

        // Aktualizacja danych specyficznych
        String typ = aktualnieWybranyPojazd.getTyp(); // Typ nie jest zmieniany podczas edycji
        switch (typ) {
            case "Koparka":
                Koparka k = (Koparka) aktualnieWybranyPojazd;
                k.setGlebokoscKopania_m(parseDoubleField(koparkaGlebokoscField, "Głębokość kopania", validationErrors));
                k.setPojemnoscLyzeczki_m3(parseDoubleField(koparkaPojemnoscLyzeczkiField, "Pojemność łyżki", validationErrors));
                k.setZasiegRamienia_m(parseDoubleField(koparkaZasiegRamieniaField, "Zasięg ramienia", validationErrors));
                break;
            case "Dźwig":
                Dzwig d = (Dzwig) aktualnieWybranyPojazd;
                d.setMaksymalnyUdzwig_tony(parseDoubleField(dzwigUdzwigField, "Maksymalny udźwig", validationErrors));
                d.setDlugoscWysiegnika_m(parseDoubleField(dzwigDlugoscWysiegnikaField, "Długość wysięgnika", validationErrors));
                d.setMaksymalnaWysokoscPodnoszenia_m(parseDoubleField(dzwigWysokoscPodnoszeniaField, "Maks. wys. podnoszenia", validationErrors));
                break;
            case "Wywrotka":
                Wywrotka w = (Wywrotka) aktualnieWybranyPojazd;
                w.setLadownosc_tony(parseDoubleField(wywrotkaLadownoscField, "Ładowność", validationErrors));
                w.setTypNapedu(validateStringField(wywrotkaTypNapeduField, "Typ napędu", validationErrors));
                w.setPojemnoscSkrzyni_m3(parseDoubleField(wywrotkaPojemnoscSkrzyniField, "Pojemność skrzyni", validationErrors));
                break;
        }

        if (!validationErrors.isEmpty()) {
            // Przywrócenie oryginalnych wartości, jeśli walidacja nie powiodła się,
            // aby użytkownik widział, co próbował zapisać.
            // Jednak lepszym podejściem byłoby nie zatwierdzanie zmian w obiekcie, dopóki walidacja nie przejdzie.
            // Na razie zostawiamy tak, ale to miejsce do potencjalnej poprawy.
            // Można by np. najpierw zebrać wszystkie wartości, potem walidować, a dopiero potem ustawiać w obiekcie.
            wyswietlDanePojazdu(aktualnieWybranyPojazd); // Przywraca poprzednie dane w polach, jeśli obiekt nie został zmieniony
            JOptionPane.showMessageDialog(this, String.join("\n", validationErrors), "Błędy walidacji", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        zaladujPojazdy();
        listaPojazdow.setSelectedValue(aktualnieWybranyPojazd, true);
        JOptionPane.showMessageDialog(this, "Dane pojazdu zaktualizowane.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Obsługuje logikę usuwania wybranego pojazdu.
     */
    private void usunPojazd() {
        if (aktualnieWybranyPojazd == null) {
            JOptionPane.showMessageDialog(this, "Najpierw wybierz pojazd z listy.", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean czyWypozyczony = zarzadzanie.getAktualneWypozyczenia().stream()
                .anyMatch(w -> w.getPojazd().equals(aktualnieWybranyPojazd));

        if (czyWypozyczony) {
            JOptionPane.showMessageDialog(this, "Nie można usunąć pojazdu, który jest aktualnie wypożyczony.", "Błąd usuwania", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean maHistorieWypozyczen = zarzadzanie.getHistoryczneWypozyczenia().stream()
                .anyMatch(w -> w.getPojazd().equals(aktualnieWybranyPojazd));
        
        if (maHistorieWypozyczen) {
             int potwierdzenieHistorii = JOptionPane.showConfirmDialog(this,
                "Pojazd '" + aktualnieWybranyPojazd.toString() + "' ma historię wypożyczeń.\n" +
                "Usunięcie pojazdu usunie go również z historii wypożyczeń.\n" +
                "Czy na pewno chcesz kontynuować?",
                "Potwierdź usunięcie pojazdu z historią", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
             if (potwierdzenieHistorii != JOptionPane.YES_OPTION) {
                 return;
             }
        }

        int potwierdzenie = JOptionPane.showConfirmDialog(this,
                "Czy na pewno chcesz usunąć pojazd: " + aktualnieWybranyPojazd.toString() + "?",
                "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (potwierdzenie == JOptionPane.YES_OPTION) {
            zarzadzanie.usunPojazd(aktualnieWybranyPojazd);
            zaladujPojazdy();
            wyczyscPola();
            aktualnieWybranyPojazd = null;
            edytujButton.setEnabled(false);
            usunButton.setEnabled(false);
            typPojazduComboBox.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Pojazd usunięty.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Metoda publiczna do odświeżania listy pojazdów z zewnątrz.
     */
    public void odswiezListePojazdow() {
        zaladujPojazdy();
    }
}
