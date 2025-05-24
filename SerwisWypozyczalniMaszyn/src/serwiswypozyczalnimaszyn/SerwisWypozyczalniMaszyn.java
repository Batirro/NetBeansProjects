package serwiswypozyczalnimaszyn;

import serwiswypozyczalnimaszyn.gui.OknoGlowne;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Główna klasa aplikacji "Serwis Wypożyczalni Maszyn".
 * Odpowiada za uruchomienie interfejsu użytkownika.
 */
public class SerwisWypozyczalniMaszyn {

    /**
     * Główna metoda (punkt wejścia) aplikacji.
     * Ustawia wygląd aplikacji (Look and Feel) na systemowy,
     * a następnie tworzy i wyświetla główne okno aplikacji w wątku zdarzeń Swing.
     *
     * @param args Argumenty przekazywane z wiersza poleceń (nieużywane w tej aplikacji).
     */
    public static void main(String[] args) {
        // Ustawienie preferowanego wyglądu (Look and Feel)
        // Próba ustawienia systemowego L&F dla lepszej integracji z systemem operacyjnym.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // W przypadku błędu, Swing użyje domyślnego L&F (Metal).
            System.err.println("Nie udało się ustawić systemowego Look and Feel: " + e.getMessage());
            System.err.println("Aplikacja użyje domyślnego wyglądu Swing.");
        }

        // Uruchomienie interfejsu użytkownika w wątku zdarzeń Swing (Event Dispatch Thread - EDT)
        // Jest to standardowa praktyka zapewniająca bezpieczeństwo wątkowe operacji na komponentach GUI.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Utworzenie instancji głównego okna aplikacji
                OknoGlowne okno = new OknoGlowne();
                // Ustawienie okna jako widoczne
                okno.setVisible(true);
            }
        });
    }
}
