package serwiswypozyczalnimaszyn.gui;

import com.toedter.calendar.JDateChooser;
import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import serwiswypozyczalnimaszyn.model.Klient;
import serwiswypozyczalnimaszyn.model.Pojazd;
import serwiswypozyczalnimaszyn.model.Wypozyczenie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PanelWypozyczenia extends JPanel {
    private ZarzadzanieWypozyczalnia zarzadzanie;
    private JFrame parentFrame;
    private JTable tabelaWypozyczen;
    private DefaultTableModel modelTabeli;
    private static final DateTimeFormatter DATE_FORMATTER_GUI = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public PanelWypozyczenia(ZarzadzanieWypozyczalnia zarzadzanie, JFrame parentFrame) {
        this.zarzadzanie = zarzadzanie;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        initComponents();
        odswiezTabeleWypozyczen();
    }

    private void initComponents() {
        String[] nazwyKolumn = {"ID", "Klient", "Pojazd", "Stawka/d", "Od", "Plan. Do", "Fakt. Do", "Status", "Koszt (PLN)"};
        modelTabeli = new DefaultTableModel(nazwyKolumn, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaWypozyczen = new JTable(modelTabeli);
        tabelaWypozyczen.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelaWypozyczen.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabelaWypozyczen.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelaWypozyczen.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelaWypozyczen.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaWypozyczen.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabelaWypozyczen.getColumnModel().getColumn(6).setPreferredWidth(80);
        tabelaWypozyczen.getColumnModel().getColumn(7).setPreferredWidth(70);
        tabelaWypozyczen.getColumnModel().getColumn(8).setPreferredWidth(80);

        add(new JScrollPane(tabelaWypozyczen), BorderLayout.CENTER);

        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNoweWypozyczenie = new JButton("Zarejestruj Wypożyczenie");
        btnNoweWypozyczenie.addActionListener(e -> zarejestrujNoweWypozyczenieDialog());
        panelPrzyciskow.add(btnNoweWypozyczenie);

        JButton btnZakonczWypozyczenie = new JButton("Zakończ Zaznaczone");
        btnZakonczWypozyczenie.addActionListener(e -> zakonczZaznaczoneWypozyczenieDialog());
        panelPrzyciskow.add(btnZakonczWypozyczenie);

        JButton btnOdswiez = new JButton("Odśwież");
        btnOdswiez.addActionListener(e -> odswiezTabeleWypozyczen());
        panelPrzyciskow.add(btnOdswiez);

        add(panelPrzyciskow, BorderLayout.SOUTH);
    }

    public void odswiezTabeleWypozyczen() {
        modelTabeli.setRowCount(0);
        List<Wypozyczenie> wypozyczenia = zarzadzanie.pobierzWszystkieWypozyczenia();
        for (Wypozyczenie w : wypozyczenia) {
            String klientInfo = w.getKlient().getNazwaFirmy() + " (ID:" + w.getKlient().getId() + ")";
            String pojazdInfo = w.getPojazd().getNazwaModelu() + " (ID:" + w.getPojazd().getId() + ")";
            String planDataDo = w.getPlanowanaDataZakonczenia() != null ? w.getPlanowanaDataZakonczenia().format(DATE_FORMATTER_GUI) : "N/A";
            String faktDataDo = w.getFaktycznaDataZakonczenia() != null ? w.getFaktycznaDataZakonczenia().format(DATE_FORMATTER_GUI) : "N/A";
            String status = w.isAktywne() ? "Aktywne" : "Zakończone";
            String kosztWyswietlany = w.isAktywne() ? "N/A" : String.format("%.2f", w.getKosztCalkowity());
            String stawkaPojazdu = String.format("%.2f", w.getPojazd().getDziennaStawka());

            modelTabeli.addRow(new Object[]{
                    w.getId(), klientInfo, pojazdInfo, stawkaPojazdu,
                    w.getDataRozpoczecia().format(DATE_FORMATTER_GUI),
                    planDataDo, faktDataDo, status, kosztWyswietlany
            });
        }
    }

    private void zarejestrujNoweWypozyczenieDialog() {
        List<Klient> klienci = zarzadzanie.pobierzWszystkichKlientow();
        if (klienci.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Brak klientów w systemie. Dodaj najpierw klienta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] opcjeKlientow = klienci.stream().map(k -> k.getId() + ": " + k.getNazwaFirmy()).toArray(String[]::new);
        String wybranyKlientStr = (String) JOptionPane.showInputDialog(parentFrame, "Wybierz klienta:", "Klient", JOptionPane.PLAIN_MESSAGE, null, opcjeKlientow, opcjeKlientow[0]);
        if (wybranyKlientStr == null) return;
        int idKlienta = Integer.parseInt(wybranyKlientStr.split(":")[0]);

        List<Pojazd> dostepnePojazdy = zarzadzanie.pobierzDostepnePojazdy();
        if (dostepnePojazdy.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Brak dostępnych pojazdów do wypożyczenia.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] opcjePojazdow = dostepnePojazdy.stream().map(p -> p.getId() + ": " + p.getNazwaModelu() + " (Stawka: " + String.format("%.2f", p.getDziennaStawka()) + " PLN/dzień)").toArray(String[]::new);
        String wybranyPojazdStr = (String) JOptionPane.showInputDialog(parentFrame, "Wybierz pojazd:", "Pojazd", JOptionPane.PLAIN_MESSAGE, null, opcjePojazdow, opcjePojazdow[0]);
        if (wybranyPojazdStr == null) return;
        int idPojazdu = Integer.parseInt(wybranyPojazdStr.split(":")[0]);

        JDateChooser dateChooserStart = new JDateChooser(new Date());
        dateChooserStart.setDateFormatString("dd-MM-yyyy");
        JDateChooser dateChooserPlanEnd = new JDateChooser();
        dateChooserPlanEnd.setDateFormatString("dd-MM-yyyy");
        if (dateChooserStart.getDate() != null) {
            LocalDate defaultPlanEnd = dateChooserStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(7);
            dateChooserPlanEnd.setDate(Date.from(defaultPlanEnd.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        JPanel panelDat = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panelDat.add(new JLabel("Data rozpoczęcia:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelDat.add(dateChooserStart, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelDat.add(new JLabel("Planowana data zakończenia:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelDat.add(dateChooserPlanEnd, gbc);

        int result = JOptionPane.showConfirmDialog(parentFrame, panelDat, "Ustal Daty Wypożyczenia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Date utilDateStart = dateChooserStart.getDate();
            Date utilDatePlanEnd = dateChooserPlanEnd.getDate();

            if (utilDateStart == null || utilDatePlanEnd == null) {
                JOptionPane.showMessageDialog(parentFrame, "Obie daty muszą być wybrane.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate dataRozpoczecia = utilDateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate planowanaDataZakonczenia = utilDatePlanEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (planowanaDataZakonczenia.isBefore(dataRozpoczecia)) {
                JOptionPane.showMessageDialog(parentFrame, "Planowana data zakończenia nie może być wcześniejsza niż data rozpoczęcia.", "Błąd Walidacji", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Optional<Wypozyczenie> noweWypozyczenie = zarzadzanie.zarejestrujWypozyczenie(idKlienta, idPojazdu, dataRozpoczecia, planowanaDataZakonczenia);
            if (noweWypozyczenie.isPresent()) {
                odswiezTabeleWypozyczen();
                if (parentFrame instanceof OknoGlowne) ((OknoGlowne)parentFrame).odswiezWszystkiePanele();
                JOptionPane.showMessageDialog(parentFrame, "Wypożyczenie zarejestrowane.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Nie udało się zarejestrować wypożyczenia. Sprawdź konsolę.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void zakonczZaznaczoneWypozyczenieDialog() {
        int wybranyWiersz = tabelaWypozyczen.getSelectedRow();
        if (wybranyWiersz == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Proszę zaznaczyć aktywne wypożyczenie do zakończenia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int idWypozyczenia = (int) modelTabeli.getValueAt(wybranyWiersz, 0);
        String statusWypozyczenia = (String) modelTabeli.getValueAt(wybranyWiersz, 7);
        if (!"Aktywne".equals(statusWypozyczenia)) {
            JOptionPane.showMessageDialog(parentFrame, "Wybrane wypożyczenie nie jest aktywne.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDateChooser dateChooserFaktEnd = new JDateChooser(new Date());
        dateChooserFaktEnd.setDateFormatString("dd-MM-yyyy");

        JPanel panelDatyKoniec = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDatyKoniec.add(new JLabel("Faktyczna data zakończenia:"));
        panelDatyKoniec.add(dateChooserFaktEnd);

        int result = JOptionPane.showConfirmDialog(parentFrame, panelDatyKoniec, "Ustal Faktyczną Datę Zakończenia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Date utilDateFaktEnd = dateChooserFaktEnd.getDate();
            if (utilDateFaktEnd == null) {
                JOptionPane.showMessageDialog(parentFrame, "Proszę wybrać faktyczną datę zakończenia.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate faktycznaDataZakonczenia = utilDateFaktEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Optional<Wypozyczenie> wypOpt = zarzadzanie.znajdzWypozyczenie(idWypozyczenia);
            if (wypOpt.isPresent()) {
                if (faktycznaDataZakonczenia.isBefore(wypOpt.get().getDataRozpoczecia())) {
                    JOptionPane.showMessageDialog(parentFrame, "Faktyczna data zakończenia nie może być wcześniejsza niż data rozpoczęcia!", "Błąd Walidacji", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Błąd: Nie znaleziono wypożyczenia do zakończenia (ID: "+idWypozyczenia+").", "Błąd Krytyczny", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (zarzadzanie.zakonczWypozyczenie(idWypozyczenia, faktycznaDataZakonczenia)) {
                odswiezTabeleWypozyczen();
                if (parentFrame instanceof OknoGlowne) ((OknoGlowne)parentFrame).odswiezWszystkiePanele();

                Optional<Wypozyczenie> zakonczoneWypOpt = zarzadzanie.znajdzWypozyczenie(idWypozyczenia);
                if(zakonczoneWypOpt.isPresent()){
                    JOptionPane.showMessageDialog(parentFrame,
                            "Wypożyczenie (ID: " + idWypozyczenia + ") zakończone pomyślnie.\nObliczony koszt: " +
                                    String.format("%.2f", zakonczoneWypOpt.get().getKosztCalkowity()) + " PLN",
                            "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Wypożyczenie zakończone, ale nie udało się pobrać szczegółów kosztu.", "Informacja", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Nie udało się zakończyć wypożyczenia (ID: "+idWypozyczenia+"). Sprawdź konsolę.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}