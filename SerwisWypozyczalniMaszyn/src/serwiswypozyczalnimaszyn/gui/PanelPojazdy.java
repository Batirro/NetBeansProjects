package serwiswypozyczalnimaszyn.gui;

import serwiswypozyczalnimaszyn.logika.ZarzadzanieWypozyczalnia;
import serwiswypozyczalnimaszyn.model.Koparka;
import serwiswypozyczalnimaszyn.model.Pojazd;
import serwiswypozyczalnimaszyn.model.Wywrotka;
import serwiswypozyczalnimaszyn.model.Dzwig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class PanelPojazdy extends JPanel {

    private ZarzadzanieWypozyczalnia zarzadzanie;
    private JFrame parentFrame;
    private JTable tabelaPojazdow;
    private DefaultTableModel modelTabeli;

    public PanelPojazdy(ZarzadzanieWypozyczalnia zarzadzanie, JFrame parentFrame) {
        this.zarzadzanie = zarzadzanie;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        initComponents();
        odswiezTabelePojazdow();
    }

    private void initComponents() {
        String[] nazwyKolumn = {"ID", "Model", "Rok", "Dostępny", "Typ Pojazdu", "Stawka/dzień (PLN)"};
        modelTabeli = new DefaultTableModel(nazwyKolumn, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaPojazdow = new JTable(modelTabeli);
        tabelaPojazdow.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelaPojazdow.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabelaPojazdow.getColumnModel().getColumn(2).setPreferredWidth(50);
        tabelaPojazdow.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelaPojazdow.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaPojazdow.getColumnModel().getColumn(5).setPreferredWidth(120);

        add(new JScrollPane(tabelaPojazdow), BorderLayout.CENTER);

        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDodaj = new JButton("Dodaj Pojazd");
        btnDodaj.addActionListener(e -> dodajPojazdDialog());
        panelPrzyciskow.add(btnDodaj);

        JButton btnUsun = new JButton("Usuń Zaznaczony");
        btnUsun.addActionListener(e -> usunZaznaczonyPojazd());
        panelPrzyciskow.add(btnUsun);

        JButton btnPokazCechy = new JButton("Pokaż Cechy Zaznaczonego");
        btnPokazCechy.addActionListener(e -> pokazCechyZaznaczonegoPojazdu());
        panelPrzyciskow.add(btnPokazCechy);

        JButton btnOdswiez = new JButton("Odśwież");
        btnOdswiez.addActionListener(e -> odswiezTabelePojazdow());
        panelPrzyciskow.add(btnOdswiez);

        add(panelPrzyciskow, BorderLayout.SOUTH);
    }

    public void odswiezTabelePojazdow() {
        modelTabeli.setRowCount(0);
        List<Pojazd> pojazdy = zarzadzanie.pobierzWszystkiePojazdy();
        for (Pojazd p : pojazdy) {
            String typPojazdu = "";
            if (p instanceof Koparka) typPojazdu = "Koparka";
            else if (p instanceof Wywrotka) typPojazdu = "Wywrotka";
            else if (p instanceof Dzwig) typPojazdu = "Dźwig";
            modelTabeli.addRow(new Object[]{
                    p.getId(),
                    p.getNazwaModelu(),
                    p.getRokProdukcji(),
                    p.isDostepny() ? "Tak" : "Nie",
                    typPojazdu,
                    String.format("%.2f", p.getDziennaStawka())
            });
        }
    }

    private void pokazCechyZaznaczonegoPojazdu() {
        int wybranyWiersz = tabelaPojazdow.getSelectedRow();
        if (wybranyWiersz == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Proszę zaznaczyć pojazd.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int idPojazdu = (int) modelTabeli.getValueAt(wybranyWiersz, 0);
        Optional<Pojazd> pojazOpt = zarzadzanie.znajdzPojazd(idPojazdu);
        if (pojazOpt.isPresent()) {
            Pojazd pojazd = pojazOpt.get();
            StringBuilder cechy = new StringBuilder();
            String typ = "";

            cechy.append("Model: ").append(pojazd.getNazwaModelu()).append("\n");
            cechy.append("Rok produkcji: ").append(pojazd.getRokProdukcji()).append("\n");
            cechy.append("Stawka dzienna: ").append(String.format("%.2f", pojazd.getDziennaStawka())).append(" PLN\n");
            cechy.append("-----------------------------\nCechy specyficzne:\n");

            if (pojazd instanceof Koparka k) {
                typ = "Koparka";
                cechy.append("  Głębokość kopania: ").append(k.getGlebokoscKopania()).append(" m\n");
                cechy.append("  Typ podwozia: ").append(k.getTypPodwozia());
            } else if (pojazd instanceof Wywrotka w) {
                typ = "Wywrotka";
                cechy.append("  Ładowność: ").append(w.getLadownosc()).append(" t\n");
                cechy.append("  Liczba osi: ").append(w.getLiczbaOsi());
            } else if (pojazd instanceof Dzwig d) {
                typ = "Dźwig";
                cechy.append("  Maksymalny udźwig: ").append(d.getMaksymalnyUdzwig()).append(" t\n");
                cechy.append("  Maks. wys. podnoszenia: ").append(d.getMaksymalnaWysokoscPodnoszenia()).append(" m");
            } else {
                typ = "Pojazd Ogólny";
                cechy.append("  Brak zdefiniowanych cech specyficznych dla tego typu.");
            }
            JTextArea textArea = new JTextArea(cechy.toString());
            textArea.setEditable(false);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPaneDialog = new JScrollPane(textArea);
            scrollPaneDialog.setPreferredSize(new Dimension(400, 200));
            JOptionPane.showMessageDialog(parentFrame, scrollPaneDialog, "Szczegóły Pojazdu: " + typ, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Nie znaleziono pojazdu o ID: " + idPojazdu, "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dodajPojazdDialog() {
        String[] typyPojazdow = {"Koparka", "Wywrotka", "Dźwig"};
        String wybranyTyp = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Wybierz typ pojazdu:",
                "Dodaj Nowy Pojazd - Wybór Typu",
                JOptionPane.PLAIN_MESSAGE,
                null,
                typyPojazdow,
                typyPojazdow[0]);

        if (wybranyTyp == null) {
            return;
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField modelField = new JTextField(20);
        JTextField rokField = new JTextField(5);
        JTextField stawkaField = new JTextField(7);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(modelField, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Rok produkcji:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(rokField, gbc);

        JTextField spec1Field = new JTextField(10);
        JTextField spec2Field = new JTextField(10);
        String spec1Label = "", spec2Label = "";

        if ("Koparka".equals(wybranyTyp)) {
            spec1Label = "Głęb. kopania (m):"; spec2Label = "Typ podwozia:";
        } else if ("Wywrotka".equals(wybranyTyp)) {
            spec1Label = "Ładowność (t):"; spec2Label = "Liczba osi:";
        } else if ("Dźwig".equals(wybranyTyp)) {
            spec1Label = "Maks. udźwig (t):"; spec2Label = "Maks. wys. podn. (m):";
        }

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel(spec1Label), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(spec1Field, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel(spec2Label), gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(spec2Field, gbc);

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Stawka dzienna (PLN):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(stawkaField, gbc);

        int result = JOptionPane.showConfirmDialog(parentFrame, formPanel, "Dodaj " + wybranyTyp, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String model = modelField.getText().trim();
                String rokStr = rokField.getText().trim();
                String spec1Str = spec1Field.getText().trim();
                String spec2Str = spec2Field.getText().trim();
                String stawkaStr = stawkaField.getText().trim();

                if (model.isEmpty() || rokStr.isEmpty() || spec1Str.isEmpty() || spec2Str.isEmpty() || stawkaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Wszystkie pola muszą być wypełnione!", "Błąd Walidacji", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int rok = Integer.parseInt(rokStr);
                if (rok < 1900 || rok > java.time.LocalDate.now().getYear() + 5) {
                    JOptionPane.showMessageDialog(parentFrame, "Niepoprawny rok produkcji.", "Błąd Walidacji", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double dziennaStawka = Double.parseDouble(stawkaStr.replace(',', '.'));
                if (dziennaStawka <= 0) {
                    JOptionPane.showMessageDialog(parentFrame, "Stawka dzienna musi być wartością dodatnią.", "Błąd Walidacji", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pojazd nowyPojazd = null;
                int noweId = zarzadzanie.pobierzNastepneIdPojazdu();

                if ("Koparka".equals(wybranyTyp)) {
                    double glebokosc = Double.parseDouble(spec1Str.replace(',', '.'));
                    if (glebokosc <= 0) throw new NumberFormatException("Głębokość kopania musi być dodatnia.");
                    nowyPojazd = new Koparka(noweId, model, rok, glebokosc, spec2Str, dziennaStawka);
                } else if ("Wywrotka".equals(wybranyTyp)) {
                    double ladownosc = Double.parseDouble(spec1Str.replace(',', '.'));
                    if (ladownosc <= 0) throw new NumberFormatException("Ładowność musi być dodatnia.");
                    int osie = Integer.parseInt(spec2Str);
                    if (osie <= 0) throw new NumberFormatException("Liczba osi musi być dodatnia.");
                    nowyPojazd = new Wywrotka(noweId, model, rok, ladownosc, osie, dziennaStawka);
                } else if ("Dźwig".equals(wybranyTyp)) {
                    double udzwig = Double.parseDouble(spec1Str.replace(',', '.'));
                    if (udzwig <= 0) throw new NumberFormatException("Udźwig musi być dodatni.");
                    double wysokosc = Double.parseDouble(spec2Str.replace(',', '.'));
                    if (wysokosc <= 0) throw new NumberFormatException("Wysokość podnoszenia musi być dodatnia.");
                    nowyPojazd = new Dzwig(noweId, model, rok, udzwig, wysokosc, dziennaStawka);
                }

                if (nowyPojazd != null) {
                    zarzadzanie.dodajPojazd(nowyPojazd);
                    odswiezTabelePojazdow();
                    JOptionPane.showMessageDialog(parentFrame, wybranyTyp + " \"" + model + "\" dodany pomyślnie!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Błąd formatu danych: " + ex.getMessage() + "\nUpewnij się, że wprowadzasz poprawne liczby.", "Błąd Walidacji", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Wystąpił nieoczekiwany błąd: " + ex.getMessage(), "Błąd Krytyczny", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void usunZaznaczonyPojazd() {
        int wybranyWiersz = tabelaPojazdow.getSelectedRow();
        if (wybranyWiersz == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Proszę zaznaczyć pojazd do usunięcia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int idPojazdu = (int) modelTabeli.getValueAt(wybranyWiersz, 0);
        String modelPojazdu = (String) modelTabeli.getValueAt(wybranyWiersz, 1);

        Optional<Pojazd> pojazdOpt = zarzadzanie.znajdzPojazd(idPojazdu);
        if (pojazdOpt.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Błąd: Wybrany pojazd nie istnieje już w systemie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            odswiezTabelePojazdow();
            return;
        }

        int potwierdzenie = JOptionPane.showConfirmDialog(
                parentFrame,
                "Czy na pewno chcesz usunąć pojazd: " + modelPojazdu + " (ID: " + idPojazdu + ")?",
                "Potwierdź Usunięcie",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (potwierdzenie == JOptionPane.YES_OPTION) {
            if (zarzadzanie.usunPojazd(idPojazdu)) {
                JOptionPane.showMessageDialog(parentFrame, "Pojazd \"" + modelPojazdu + "\" usunięty pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Nie udało się usunąć pojazdu \"" + modelPojazdu + "\".\nMożliwe przyczyny: pojazd jest aktualnie wypożyczony.", "Błąd Usuwania", JOptionPane.ERROR_MESSAGE);
            }
            odswiezTabelePojazdow();
            if (parentFrame instanceof OknoGlowne) {
                ((OknoGlowne)parentFrame).odswiezWszystkiePanele();
            }
        }
    }
}