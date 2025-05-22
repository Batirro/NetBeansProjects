package serwiswypozyczalnimaszyn.gui;

import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import serwiswypozyczalnimaszyn.model.Klient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class PanelKlienci extends JPanel {

    private ZarzadzanieWypozyczalnia zarzadzanie;
    private JFrame parentFrame;
    private JTable tabelaKlientow;
    private DefaultTableModel modelTabeli;

    public PanelKlienci(ZarzadzanieWypozyczalnia zarzadzanie, JFrame parentFrame) {
        this.zarzadzanie = zarzadzanie;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        initComponents();
        odswiezTabeleKlientow();
    }

    private void initComponents() {
        String[] nazwyKolumn = {"ID", "Nazwa Firmy", "NIP"};
        modelTabeli = new DefaultTableModel(nazwyKolumn, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaKlientow = new JTable(modelTabeli);
        add(new JScrollPane(tabelaKlientow), BorderLayout.CENTER);

        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDodaj = new JButton("Dodaj Klienta");
        btnDodaj.addActionListener(e -> dodajKlientaDialog());
        panelPrzyciskow.add(btnDodaj);

        JButton btnEdytuj = new JButton("Edytuj Zaznaczonego");
        btnEdytuj.addActionListener(e -> edytujZaznaczonegoKlientaDialog());
        panelPrzyciskow.add(btnEdytuj);

        JButton btnUsun = new JButton("Usuń Zaznaczonego");
        btnUsun.addActionListener(e -> usunZaznaczonegoKlienta());
        panelPrzyciskow.add(btnUsun);

        JButton btnOdswiez = new JButton("Odśwież");
        btnOdswiez.addActionListener(e -> odswiezTabeleKlientow());
        panelPrzyciskow.add(btnOdswiez);

        add(panelPrzyciskow, BorderLayout.SOUTH);
    }

    public void odswiezTabeleKlientow() {
        modelTabeli.setRowCount(0);
        List<Klient> klienci = zarzadzanie.pobierzWszystkichKlientow();
        for (Klient k : klienci) {
            modelTabeli.addRow(new Object[]{k.getId(), k.getNazwaFirmy(), k.getNumerNIP()});
        }
    }

    private void dodajKlientaDialog() {
        JTextField fieldNazwa = new JTextField();
        JTextField fieldNip = new JTextField();

        final JComponent[] inputs = new JComponent[]{
                new JLabel("Nazwa Firmy:"),
                fieldNazwa,
                new JLabel("Numer NIP:"),
                fieldNip
        };
        int result = JOptionPane.showConfirmDialog(parentFrame, inputs, "Dodaj Nowego Klienta", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String nazwa = fieldNazwa.getText();
            String nip = fieldNip.getText();
            if (nazwa.trim().isEmpty() || nip.trim().isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Nazwa firmy i NIP nie mogą być puste.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Sprawdzenie czy NIP już istnieje
            if (zarzadzanie.znajdzKlientaPoNip(nip).isPresent()) {
                JOptionPane.showMessageDialog(parentFrame, "Klient o podanym NIP już istnieje!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int noweId = zarzadzanie.pobierzNastepneIdKlienta();
            Klient nowyKlient = new Klient(noweId, nazwa, nip);
            zarzadzanie.dodajKlienta(nowyKlient);
            odswiezTabeleKlientow();
            JOptionPane.showMessageDialog(parentFrame, "Klient dodany pomyślnie!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void edytujZaznaczonegoKlientaDialog() {
        int wybranyWiersz = tabelaKlientow.getSelectedRow();
        if (wybranyWiersz == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Proszę zaznaczyć klienta do edycji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int idKlienta = (int) modelTabeli.getValueAt(wybranyWiersz, 0);
        Optional<Klient> klientOpt = zarzadzanie.znajdzKlienta(idKlienta);

        if (klientOpt.isPresent()) {
            Klient klient = klientOpt.get();
            JTextField fieldNazwa = new JTextField(klient.getNazwaFirmy());
            JTextField fieldNip = new JTextField(klient.getNumerNIP());

            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Nazwa Firmy:"),
                    fieldNazwa,
                    new JLabel("Numer NIP:"),
                    fieldNip
            };
            int result = JOptionPane.showConfirmDialog(parentFrame, inputs, "Edytuj Klienta (ID: " + idKlienta + ")", JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String nowaNazwa = fieldNazwa.getText();
                String nowyNip = fieldNip.getText();
                if (nowaNazwa.trim().isEmpty() || nowyNip.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Nazwa firmy i NIP nie mogą być puste.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Sprawdzenie czy nowy NIP nie koliduje z innym klientem
                Optional<Klient> innyKlientZNipem = zarzadzanie.znajdzKlientaPoNip(nowyNip);
                if (innyKlientZNipem.isPresent() && innyKlientZNipem.get().getId() != idKlienta) {
                    JOptionPane.showMessageDialog(parentFrame, "Inny klient o podanym NIP już istnieje!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (zarzadzanie.edytujKlienta(idKlienta, nowaNazwa, nowyNip)) {
                    odswiezTabeleKlientow();
                    JOptionPane.showMessageDialog(parentFrame, "Dane klienta zaktualizowane.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Komunikat z logiki powinien być na konsoli lub lepiej przechwycony
                    JOptionPane.showMessageDialog(parentFrame, "Nie udało się zaktualizować danych klienta.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Nie można znaleźć klienta do edycji.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void usunZaznaczonegoKlienta() {
        int wybranyWiersz = tabelaKlientow.getSelectedRow();
        if (wybranyWiersz == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Proszę zaznaczyć klienta do usunięcia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int idKlienta = (int) modelTabeli.getValueAt(wybranyWiersz, 0);
        String nazwaFirmy = (String) modelTabeli.getValueAt(wybranyWiersz, 1);

        int potwierdzenie = JOptionPane.showConfirmDialog(
                parentFrame,
                "Czy na pewno chcesz usunąć klienta: " + nazwaFirmy + " (ID: " + idKlienta + ")?",
                "Potwierdź usunięcie",
                JOptionPane.YES_NO_OPTION);

        if (potwierdzenie == JOptionPane.YES_OPTION) {
            if (zarzadzanie.usunKlienta(idKlienta)) {
                JOptionPane.showMessageDialog(parentFrame, "Klient usunięty pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Błąd (np. klient ma aktywne wypożyczenia) - komunikat z logiki na konsoli
                JOptionPane.showMessageDialog(parentFrame, "Nie udało się usunąć klienta. Możliwe, że ma aktywne wypożyczenia.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            odswiezTabeleKlientow();
        }
    }
}