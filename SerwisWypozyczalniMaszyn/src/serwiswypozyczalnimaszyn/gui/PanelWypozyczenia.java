package serwiswypozyczalnimaszyn.gui;

import com.toedter.calendar.JDateChooser;
import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import serwiswypozyczalnimaszyn.model.Klient;
import serwiswypozyczalnimaszyn.model.Pojazd;
import serwiswypozyczalnimaszyn.model.Wypozyczenie;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Panel GUI do zarządzania wypożyczeniami.
 * Umożliwia tworzenie nowych wypożyczeń dla klientów-firm, rejestrowanie zwrotów
 * oraz przeglądanie aktualnych i historycznych wypożyczeń.
 * Dodano pole planowanej daty zwrotu oraz wskaźnik dostępności pojazdu.
 */
public class PanelWypozyczenia extends JPanel {

    private ZarzadzanieWypozyczalnia zarzadzanie;

    private JComboBox<Klient> klientComboBox;
    private JComboBox<Pojazd> pojazdComboBox;
    private JDateChooser dataWypozyczeniaChooser;
    private JDateChooser planowanaDataZwrotuChooser; // Nowe pole
    private JLabel dostepnoscPojazduLabel; // Nowe pole
    private JButton wypozyczButton;

    private JList<Wypozyczenie> listaAktualnychWypozyczen;
    private DefaultListModel<Wypozyczenie> modelAktualnychWypozyczen;
    private JDateChooser dataZwrotuChooser; // Dla rzeczywistego zwrotu
    private JButton zwrocButton;
    private JLabel kosztLabel;

    private JList<Wypozyczenie> listaHistorycznychWypozyczen;
    private DefaultListModel<Wypozyczenie> modelHistorycznychWypozyczen;
    private JTextArea szczegolyHistorycznegoWypozyczeniaArea;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public PanelWypozyczenia(ZarzadzanieWypozyczalnia zarzadzanie) {
        this.zarzadzanie = zarzadzanie;
        setLayout(new GridLayout(1, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        dodajListeneryDostepnosci();
        zaladujDaneDoComboBoxow();
        zaladujAktualneWypozyczenia();
        zaladujHistoryczneWypozyczenia();
        sprawdzDostepnoscPojazdu(); // Sprawdź przy inicjalizacji
    }

    private void initComponents() {
        JPanel panelLewy = new JPanel(new BorderLayout(10,10));
        JPanel panelTworzenia = new JPanel(new GridBagLayout());
        panelTworzenia.setBorder(BorderFactory.createTitledBorder("Nowe Wypożyczenie"));
        GridBagConstraints gbcTworzenie = new GridBagConstraints();
        gbcTworzenie.insets = new Insets(5, 5, 5, 5);
        gbcTworzenie.fill = GridBagConstraints.HORIZONTAL;
        gbcTworzenie.anchor = GridBagConstraints.WEST;

        // Klient
        gbcTworzenie.gridx = 0; gbcTworzenie.gridy = 0; panelTworzenia.add(new JLabel("Klient (Firma):"), gbcTworzenie);
        gbcTworzenie.gridx = 1; gbcTworzenie.gridy = 0; gbcTworzenie.weightx = 1.0;
        klientComboBox = new JComboBox<>();
        panelTworzenia.add(klientComboBox, gbcTworzenie);
        gbcTworzenie.weightx = 0;

        // Pojazd
        gbcTworzenie.gridx = 0; gbcTworzenie.gridy = 1; panelTworzenia.add(new JLabel("Pojazd:"), gbcTworzenie);
        gbcTworzenie.gridx = 1; gbcTworzenie.gridy = 1; gbcTworzenie.weightx = 1.0;
        pojazdComboBox = new JComboBox<>();
        panelTworzenia.add(pojazdComboBox, gbcTworzenie);
        gbcTworzenie.weightx = 0;

        // Data wypożyczenia
        gbcTworzenie.gridx = 0; gbcTworzenie.gridy = 2; panelTworzenia.add(new JLabel("Data wypożyczenia:"), gbcTworzenie);
        gbcTworzenie.gridx = 1; gbcTworzenie.gridy = 2;
        dataWypozyczeniaChooser = new JDateChooser();
        dataWypozyczeniaChooser.setDate(new Date());
        dataWypozyczeniaChooser.setDateFormatString("dd-MM-yyyy");
        panelTworzenia.add(dataWypozyczeniaChooser, gbcTworzenie);

        // Planowana data zwrotu
        gbcTworzenie.gridx = 0; gbcTworzenie.gridy = 3; panelTworzenia.add(new JLabel("Planowana data zwrotu:"), gbcTworzenie);
        gbcTworzenie.gridx = 1; gbcTworzenie.gridy = 3;
        planowanaDataZwrotuChooser = new JDateChooser();
        // Ustawienie domyślnej planowanej daty zwrotu np. tydzień od dzisiaj
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        planowanaDataZwrotuChooser.setDate(cal.getTime());
        planowanaDataZwrotuChooser.setDateFormatString("dd-MM-yyyy");
        panelTworzenia.add(planowanaDataZwrotuChooser, gbcTworzenie);
        
        // Etykieta dostępności pojazdu
        gbcTworzenie.gridx = 0; gbcTworzenie.gridy = 4; panelTworzenia.add(new JLabel("Dostępność:"), gbcTworzenie);
        gbcTworzenie.gridx = 1; gbcTworzenie.gridy = 4;
        dostepnoscPojazduLabel = new JLabel("Sprawdź...");
        dostepnoscPojazduLabel.setFont(dostepnoscPojazduLabel.getFont().deriveFont(Font.BOLD));
        panelTworzenia.add(dostepnoscPojazduLabel, gbcTworzenie);

        // Przycisk Wypożycz
        gbcTworzenie.gridx = 0; gbcTworzenie.gridy = 5; gbcTworzenie.gridwidth = 2;
        gbcTworzenie.fill = GridBagConstraints.NONE; gbcTworzenie.anchor = GridBagConstraints.CENTER;
        wypozyczButton = new JButton("Wypożycz");
        panelTworzenia.add(wypozyczButton, gbcTworzenie);

        panelLewy.add(panelTworzenia, BorderLayout.NORTH);

        // Sekcja Zwrotów (bez zmian w strukturze, tylko listenery)
        JPanel panelZwrotow = new JPanel(new BorderLayout(5,5));
        panelZwrotow.setBorder(BorderFactory.createTitledBorder("Aktualne Wypożyczenia (do zwrotu)"));
        modelAktualnychWypozyczen = new DefaultListModel<>();
        listaAktualnychWypozyczen = new JList<>(modelAktualnychWypozyczen);
        listaAktualnychWypozyczen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelZwrotow.add(new JScrollPane(listaAktualnychWypozyczen), BorderLayout.CENTER);
        JPanel panelKontrolekZwrotu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelKontrolekZwrotu.add(new JLabel("Data zwrotu:"));
        dataZwrotuChooser = new JDateChooser(); // Rzeczywista data zwrotu
        dataZwrotuChooser.setDate(new Date());
        dataZwrotuChooser.setDateFormatString("dd-MM-yyyy");
        panelKontrolekZwrotu.add(dataZwrotuChooser);
        zwrocButton = new JButton("Zwróć Pojazd");
        zwrocButton.setEnabled(false);
        panelKontrolekZwrotu.add(zwrocButton);
        kosztLabel = new JLabel("Koszt: -");
        panelKontrolekZwrotu.add(kosztLabel);
        panelZwrotow.add(panelKontrolekZwrotu, BorderLayout.SOUTH);
        panelLewy.add(panelZwrotow, BorderLayout.CENTER);
        add(panelLewy);

        // Panel Prawy: Historia Wypożyczeń (bez zmian)
        JPanel panelPrawy = new JPanel(new BorderLayout(10,10));
        panelPrawy.setBorder(BorderFactory.createTitledBorder("Historia Wypożyczeń"));
        modelHistorycznychWypozyczen = new DefaultListModel<>();
        listaHistorycznychWypozyczen = new JList<>(modelHistorycznychWypozyczen);
        listaHistorycznychWypozyczen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelPrawy.add(new JScrollPane(listaHistorycznychWypozyczen), BorderLayout.CENTER);
        szczegolyHistorycznegoWypozyczeniaArea = new JTextArea(5, 30);
        szczegolyHistorycznegoWypozyczeniaArea.setBorder(BorderFactory.createTitledBorder("Szczegóły"));
        szczegolyHistorycznegoWypozyczeniaArea.setEditable(false);
        szczegolyHistorycznegoWypozyczeniaArea.setLineWrap(true);
        szczegolyHistorycznegoWypozyczeniaArea.setWrapStyleWord(true);
        panelPrawy.add(new JScrollPane(szczegolyHistorycznegoWypozyczeniaArea), BorderLayout.SOUTH);
        add(panelPrawy);

        // Listenery przycisków
        wypozyczButton.addActionListener(e -> utworzWypozyczenie());
        listaAktualnychWypozyczen.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Wypozyczenie wybrane = listaAktualnychWypozyczen.getSelectedValue();
                zwrocButton.setEnabled(wybrane != null);
                obliczSzacowanyKosztPrzyZwrocie(wybrane);
            }
        });
        dataZwrotuChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
             Wypozyczenie wybrane = listaAktualnychWypozyczen.getSelectedValue();
             obliczSzacowanyKosztPrzyZwrocie(wybrane);
        });
        zwrocButton.addActionListener(e -> zwrocPojazd());
        listaHistorycznychWypozyczen.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                wyswietlSzczegolyHistorycznego(listaHistorycznychWypozyczen.getSelectedValue());
            }
        });
    }
    
    private void obliczSzacowanyKosztPrzyZwrocie(Wypozyczenie wybraneWypozyczenie) {
        if (wybraneWypozyczenie != null) {
            Date dataWyp = wybraneWypozyczenie.getDataWypozyczenia();
            Date dataZwr = dataZwrotuChooser.getDate(); // Rzeczywista data zwrotu
            Pojazd pojazd = wybraneWypozyczenie.getPojazd();

            if (dataZwr != null && dataWyp != null && pojazd != null && !dataZwr.before(dataWyp)) {
                long diffInMillis = dataZwr.getTime() - dataWyp.getTime();
                long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                 if (diffInMillis % (1000 * 60 * 60 * 24) > 0 || diffInDays == 0) {
                    diffInDays++;
                }
                double koszt = diffInDays * pojazd.getCenaZaDobe();
                kosztLabel.setText(String.format("Koszt: %.2f zł", koszt));
            } else {
                kosztLabel.setText("Koszt: -");
            }
        } else {
            kosztLabel.setText("Koszt: -");
        }
    }


    private void dodajListeneryDostepnosci() {
        PropertyChangeListener listener = evt -> sprawdzDostepnoscPojazdu();
        dataWypozyczeniaChooser.getDateEditor().addPropertyChangeListener("date", listener);
        planowanaDataZwrotuChooser.getDateEditor().addPropertyChangeListener("date", listener);
        pojazdComboBox.addActionListener(e -> sprawdzDostepnoscPojazdu());
    }

    private void sprawdzDostepnoscPojazdu() {
        Pojazd wybranyPojazd = (Pojazd) pojazdComboBox.getSelectedItem();
        Date dataOd = dataWypozyczeniaChooser.getDate();
        Date dataDo = planowanaDataZwrotuChooser.getDate();

        if (wybranyPojazd == null || dataOd == null || dataDo == null) {
            dostepnoscPojazduLabel.setText("Wybierz pojazd i daty");
            dostepnoscPojazduLabel.setForeground(Color.ORANGE);
            wypozyczButton.setEnabled(false);
            return;
        }

        if (dataOd.after(dataDo)) {
            dostepnoscPojazduLabel.setText("Błędny zakres dat!");
            dostepnoscPojazduLabel.setForeground(Color.RED);
            wypozyczButton.setEnabled(false);
            return;
        }
        
        // Sprawdzenie czy data wypożyczenia nie jest z przeszłości
        Date dzisiaj = truncateTime(new Date());
        Date wybranaDataWyp = truncateTime(dataOd);

        if (wybranaDataWyp.before(dzisiaj)) {
            dostepnoscPojazduLabel.setText("Data wyp. z przeszłości!");
            dostepnoscPojazduLabel.setForeground(Color.RED);
            wypozyczButton.setEnabled(false);
            return;
        }


        boolean dostepny = zarzadzanie.czyPojazdBedzieDostepny(wybranyPojazd, dataOd, dataDo);
        if (dostepny) {
            dostepnoscPojazduLabel.setText("DOSTĘPNY");
            dostepnoscPojazduLabel.setForeground(new Color(0, 128, 0)); // Ciemnozielony
            wypozyczButton.setEnabled(true);
        } else {
            dostepnoscPojazduLabel.setText("NIEDOSTĘPNY");
            dostepnoscPojazduLabel.setForeground(Color.RED);
            wypozyczButton.setEnabled(false);
        }
    }


    public void zaladujDaneDoComboBoxow() {
        klientComboBox.removeAllItems();
        List<Klient> klienci = zarzadzanie.getKlienci();
        for (Klient k : klienci) {
            klientComboBox.addItem(k);
        }

        pojazdComboBox.removeAllItems();
        // Ładujemy pojazdy, które są ogólnie dostępne (np. nie w serwisie)
        // Szczegółowa dostępność w okresie będzie sprawdzana dynamicznie
        List<Pojazd> pojazdyDostepneOgolnie = zarzadzanie.getPojazdyOgolnieDostepne();
        for (Pojazd p : pojazdyDostepneOgolnie) {
            pojazdComboBox.addItem(p);
        }
        sprawdzDostepnoscPojazdu(); // Odśwież status dostępności po załadowaniu
    }

    private void zaladujAktualneWypozyczenia() {
        modelAktualnychWypozyczen.clear();
        List<Wypozyczenie> aktualne = zarzadzanie.getAktualneWypozyczenia();
        for (Wypozyczenie w : aktualne) {
            modelAktualnychWypozyczen.addElement(w);
        }
        zwrocButton.setEnabled(false);
        kosztLabel.setText("Koszt: -");
    }

    private void zaladujHistoryczneWypozyczenia() {
        modelHistorycznychWypozyczen.clear();
        List<Wypozyczenie> historyczne = zarzadzanie.getHistoryczneWypozyczenia();
        for (Wypozyczenie w : historyczne) {
            modelHistorycznychWypozyczen.addElement(w);
        }
        szczegolyHistorycznegoWypozyczeniaArea.setText("");
    }
    
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

    private void utworzWypozyczenie() {
        Klient klient = (Klient) klientComboBox.getSelectedItem();
        Pojazd pojazd = (Pojazd) pojazdComboBox.getSelectedItem();
        Date dataWypozyczenia = dataWypozyczeniaChooser.getDate();
        Date planDataZwrotu = planowanaDataZwrotuChooser.getDate();

        if (klient == null || pojazd == null || dataWypozyczenia == null || planDataZwrotu == null) {
            JOptionPane.showMessageDialog(this, "Wszystkie pola (klient, pojazd, daty) muszą być wypełnione.", "Błąd danych", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dataWypozyczenia.after(planDataZwrotu)) {
            JOptionPane.showMessageDialog(this, "Data wypożyczenia nie może być późniejsza niż planowana data zwrotu.", "Błąd dat", JOptionPane.ERROR_MESSAGE);
            sprawdzDostepnoscPojazdu(); // Odśwież etykietę dostępności
            return;
        }
         Date dzisiaj = truncateTime(new Date());
        Date wybranaDataWyp = truncateTime(dataWypozyczenia);

        if (wybranaDataWyp.before(dzisiaj)) {
            JOptionPane.showMessageDialog(this, "Data wypożyczenia nie może być z przeszłości.", "Błąd daty", JOptionPane.ERROR_MESSAGE);
            sprawdzDostepnoscPojazdu();
            return;
        }

        // Ponowne sprawdzenie dostępności bezpośrednio przed próbą wypożyczenia
        if (!zarzadzanie.czyPojazdBedzieDostepny(pojazd, dataWypozyczenia, planDataZwrotu)) {
             JOptionPane.showMessageDialog(this, "Pojazd stał się niedostępny w wybranym okresie. Proszę wybrać inny termin lub pojazd.", "Błąd dostępności", JOptionPane.ERROR_MESSAGE);
             sprawdzDostepnoscPojazdu(); // Odśwież etykietę
             return;
        }

        boolean sukces = zarzadzanie.wypozyczPojazd(klient, pojazd, dataWypozyczenia, planDataZwrotu);
        if (sukces) {
            JOptionPane.showMessageDialog(this, "Pojazd wypożyczony pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            zaladujDaneDoComboBoxow(); // Odświeży też listę pojazdów i sprawdzi dostępność
            zaladujAktualneWypozyczenia();
            // Reset dat do domyślnych
            dataWypozyczeniaChooser.setDate(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 7);
            planowanaDataZwrotuChooser.setDate(cal.getTime());
            sprawdzDostepnoscPojazdu(); // Ponowne sprawdzenie dla nowych dat
        } else {
            JOptionPane.showMessageDialog(this, "Nie udało się wypożyczyć pojazdu. Sprawdź konsolę błędów lub dostępność.", "Błąd operacji", JOptionPane.ERROR_MESSAGE);
            sprawdzDostepnoscPojazdu(); // Odśwież etykietę
        }
    }

    private void zwrocPojazd() {
        Wypozyczenie wybraneWypozyczenie = listaAktualnychWypozyczen.getSelectedValue();
        Date dataZwrotuRzeczywista = dataZwrotuChooser.getDate(); // Rzeczywista data zwrotu

        if (wybraneWypozyczenie == null) {
            JOptionPane.showMessageDialog(this, "Wybierz wypożyczenie z listy aktualnych.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dataZwrotuRzeczywista == null) {
            JOptionPane.showMessageDialog(this, "Wybierz rzeczywistą datę zwrotu.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dataZwrotuRzeczywista.before(wybraneWypozyczenie.getDataWypozyczenia())) {
            JOptionPane.showMessageDialog(this, "Data zwrotu nie może być wcześniejsza niż data wypożyczenia.", "Błąd daty", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sukces = zarzadzanie.zwrocPojazd(wybraneWypozyczenie, dataZwrotuRzeczywista);
        if (sukces) {
            JOptionPane.showMessageDialog(this, String.format("Pojazd zwrócony. Koszt: %.2f zł", wybraneWypozyczenie.getKosztCalkowity()), "Sukces", JOptionPane.INFORMATION_MESSAGE);
            zaladujDaneDoComboBoxow();
            zaladujAktualneWypozyczenia();
            zaladujHistoryczneWypozyczenia();
            kosztLabel.setText("Koszt: -");
            dataZwrotuChooser.setDate(new Date());
        } else {
            JOptionPane.showMessageDialog(this, "Nie udało się zarejestrować zwrotu.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void wyswietlSzczegolyHistorycznego(Wypozyczenie wypozyczenie) {
        if (wypozyczenie != null) {
            Klient klient = wypozyczenie.getKlient();
            Pojazd pojazd = wypozyczenie.getPojazd();
            StringBuilder sb = new StringBuilder();

            if (klient != null) {
                sb.append("Klient (Firma): ").append(klient.getNazwaFirmy()).append("\n");
                sb.append("NIP: ").append(klient.getNip()).append("\n\n");
            } else {
                sb.append("Klient: BRAK DANYCH\n\n");
            }

            if (pojazd != null) {
                sb.append("Pojazd: ").append(pojazd.toString()).append("\n"); // Używa toString() z konkretnej klasy pojazdu
                sb.append("Typ pojazdu: ").append(pojazd.getTyp()).append("\n");
            } else {
                 sb.append("Pojazd: BRAK DANYCH\n");
            }
            
            sb.append("Data wypożyczenia: ").append(dateFormat.format(wypozyczenie.getDataWypozyczenia())).append("\n");
            if (wypozyczenie.getPlanowanaDataZwrotu() != null) {
                 sb.append("Planowana data zwrotu: ").append(dateFormat.format(wypozyczenie.getPlanowanaDataZwrotu())).append("\n");
            }
            if (wypozyczenie.getDataZwrotu() != null) {
                sb.append("Rzeczywista data zwrotu: ").append(dateFormat.format(wypozyczenie.getDataZwrotu())).append("\n");
            }
            sb.append(String.format("Koszt całkowity: %.2f zł", wypozyczenie.getKosztCalkowity()));
            szczegolyHistorycznegoWypozyczeniaArea.setText(sb.toString());
        } else {
            szczegolyHistorycznegoWypozyczeniaArea.setText("");
        }
    }

    public void odswiezWszystko() {
        zaladujDaneDoComboBoxow(); // To wywoła też sprawdzDostepnoscPojazdu()
        zaladujAktualneWypozyczenia();
        zaladujHistoryczneWypozyczenia();
    }
}

