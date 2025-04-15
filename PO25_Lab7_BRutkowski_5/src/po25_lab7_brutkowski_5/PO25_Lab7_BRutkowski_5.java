package po25_lab7_brutkowski_5;

import java.io.FileWriter; // Import do zapisu do pliku
import java.io.IOException; // Import do obsługi błędów I/O
import java.io.PrintWriter; // Import dla wygodnego zapisu tekstu
import java.util.ArrayList;
import java.util.List;

public class PO25_Lab7_BRutkowski_5 {

    public static void main(String[] args) {

        System.out.println("--- Zadanie 5.0: Obliczenia Wynagrodzeń ---");

        List<Pracownik> pracownicy = new ArrayList<>();
        pracownicy.add(new Ksiegowy("Adam", "Wiśniewski", 6000));
        pracownicy.add(new PracownikIT("Agata", "Wróbel", 7500, 3));
        pracownicy.add(new PracownikIT("Paweł", "Kowalski", 5000, 1));
        pracownicy.add(new Programista("Ada", "Nowak", 8000, 6));

        for (Pracownik p : pracownicy) {
            System.out.println(p.wyswietlSzczegolyWynagrodzenia());
            System.out.println("--------------------");
        }

        System.out.println("\n--- Zadanie 5.1: Obliczenia Zysku Transportowego ---");

        SrodekTransportu samolot = new Samolot();
        SrodekTransportu pociag = new Pociag();

        // --- Obliczenia dla Pytania 1 ---
        double dystans1 = 200;
        double wynagrodzenie1 = 10;
        double koszt1 = pociag.obliczKoszt(dystans1);
        double zysk1 = KalkulatorZysku.obliczZysk(dystans1, wynagrodzenie1, pociag);
        System.out.printf("Pytanie 1: Zysk dla (dystans=%.1f, wynagrodzenie=%.1f, transport=%s) wynosi: %.2f\n",
                dystans1, wynagrodzenie1, pociag, zysk1);

        // --- Obliczenia dla Pytania 2 ---
        double dystans2a = 50;
        double wynagrodzenie2a = 50000;
        double koszt2a = samolot.obliczKoszt(dystans2a);
        double zysk2a = KalkulatorZysku.obliczZysk(dystans2a, wynagrodzenie2a, samolot);
        System.out.printf("  Zlecenie A (dystans=%.1f, wynagrodzenie=%.1f): Koszt=%.2f, Zysk = %.2f\n", dystans2a, wynagrodzenie2a, koszt2a, zysk2a);

        double dystans2b = 30;
        double wynagrodzenie2b = 40000;
        double koszt2b = samolot.obliczKoszt(dystans2b);
        double zysk2b = KalkulatorZysku.obliczZysk(dystans2b, wynagrodzenie2b, samolot);
        System.out.printf("  Zlecenie B (dystans=%.1f, wynagrodzenie=%.1f): Koszt=%.2f, Zysk = %.2f\n", dystans2b, wynagrodzenie2b, koszt2b, zysk2b);

        System.out.print("Pytanie 2: ");
        if (zysk2a > zysk2b) {
            System.out.println("Większy zysk da zlecenie A (dystans 50, wynagrodzenie 50k).");
        } else if (zysk2b > zysk2a) {
            System.out.println("Większy zysk da zlecenie B (dystans 30, wynagrodzenie 40k).");
        } else {
            System.out.println("Oba zlecenia dają taki sam zysk.");
        }

        // --- Obliczenia dla Pytania 3 ---
        double dystans3 = 350;
        double wynagrodzenie3 = 45000;
        double koszt3_samolot = samolot.obliczKoszt(dystans3);
        double zysk3_samolot = KalkulatorZysku.obliczZysk(dystans3, wynagrodzenie3, samolot);
        double koszt3_pociag = pociag.obliczKoszt(dystans3);
        double zysk3_pociag = KalkulatorZysku.obliczZysk(dystans3, wynagrodzenie3, pociag);

        System.out.printf("Pytanie 3: Zlecenie (dystans=%.1f, wynagrodzenie=%.1f)\n", dystans3, wynagrodzenie3);
        System.out.printf("  Zysk używając %s: Koszt=%.2f, Zysk=%.2f\n", samolot, koszt3_samolot, zysk3_samolot);
        System.out.printf("  Zysk używając %s: Koszt=%.2f, Zysk=%.2f\n", pociag, koszt3_pociag, zysk3_pociag);

        System.out.print("Pytanie 3: ");
        if (zysk3_samolot > zysk3_pociag) {
            System.out.println("Większy zysk da Samolot.");
        } else if (zysk3_pociag > zysk3_samolot) {
            System.out.println("Większy zysk da Pociąg.");
        } else {
            System.out.println("Oba środki transportu dają taki sam zysk.");
        }

        try {
            zapiszOdpowiedziDoPliku(
                dystans1, wynagrodzenie1, koszt1, zysk1,
                dystans2a, wynagrodzenie2a, koszt2a, zysk2a,
                dystans2b, wynagrodzenie2b, koszt2b, zysk2b,
                dystans3, wynagrodzenie3, koszt3_samolot, zysk3_samolot,
                koszt3_pociag, zysk3_pociag
            );
            System.out.println("\nWyniki zapisano do pliku odpowiedzi.txt");
        } catch (IOException e) {
            System.err.println("\nBłąd podczas zapisu do pliku odpowiedzi.txt: " + e.getMessage());
        }
    }
    private static void zapiszOdpowiedziDoPliku(
            double dystans1, double wynagrodzenie1, double koszt1, double zysk1,
            double dystans2a, double wynagrodzenie2a, double koszt2a, double zysk2a,
            double dystans2b, double wynagrodzenie2b, double koszt2b, double zysk2b,
            double dystans3, double wynagrodzenie3, double koszt3_samolot, double zysk3_samolot,
            double koszt3_pociag, double zysk3_pociag) throws IOException {

        // Try-with-resources zapewnia automatyczne zamknięcie PrintWriter i FileWriter
        try (PrintWriter writer = new PrintWriter(new FileWriter("odpowiedzi.txt"))) {

            writer.println("Odpowiedzi do Zadania 5.1");
            writer.println("========================");
            writer.println();

            writer.println("Pytanie 1: Jaki jest zysk za zrealizowanie zlecenia z dystansem 200 i wynagrodzeniem 10 z wykorzystaniem pociągu?");
            writer.printf("   - Dystans: %.1f\n", dystans1);
            writer.printf("   - Wynagrodzenie: %.2f (Założenie: podana wartość 10, może powinno być 10k?)\n", wynagrodzenie1);
            writer.printf("   - Koszt pociągu (20 * S): %.2f\n", koszt1);
            writer.printf("   - Zysk (Wynagrodzenie - Koszt): %.2f\n", zysk1);
            writer.printf("   Odpowiedź: Zysk wynosi %.2f %s.\n", zysk1, (zysk1 < 0 ? "(strata)" : ""));
            writer.println();


            writer.println("Pytanie 2: Firma ma do wyboru dwa zlecenia na transport samolotem:");
            writer.println("   - Zlecenie A: dystans 50, wynagrodzenie 50 (Założenie: 50k)");
            writer.printf("     - Dystans: %.1f\n", dystans2a);
            writer.printf("     - Wynagrodzenie: %.2f\n", wynagrodzenie2a);
            writer.printf("     - Koszt samolotu (100 * S^2): %.2f\n", koszt2a);
            writer.printf("     - Zysk A: %.2f %s\n", zysk2a, (zysk2a < 0 ? "(strata)" : ""));
            writer.println("   - Zlecenie B: dystans 30, wynagrodzenie 40 (Założenie: 40k)");
            writer.printf("     - Dystans: %.1f\n", dystans2b);
            writer.printf("     - Wynagrodzenie: %.2f\n", wynagrodzenie2b);
            writer.printf("     - Koszt samolotu (100 * S^2): %.2f\n", koszt2b);
            writer.printf("     - Zysk B: %.2f %s\n", zysk2b, (zysk2b < 0 ? "(strata)" : ""));
            writer.println();
            writer.print("   Które z tych zleceń da większy zysk (mniejszą stratę)?\n   Odpowiedź: ");
            if (zysk2a > zysk2b) {
                writer.printf("Zlecenie A (Zysk: %.2f) da większy zysk niż Zlecenie B (Zysk: %.2f).\n", zysk2a, zysk2b);
            } else if (zysk2b > zysk2a) {
                 writer.printf("Zlecenie B (Zysk: %.2f) da większy zysk niż Zlecenie A (Zysk: %.2f).\n", zysk2b, zysk2a);
            } else {
                writer.printf("Oba zlecenia dają taki sam zysk (%.2f).\n", zysk2a);
            }
            if(zysk2a < 0 || zysk2b < 0) {
                 writer.println("   (Uwaga: Przy przyjętych założeniach wynagrodzeń (50k, 40k) oba zlecenia samolotowe generują stratę.)");
            }
            writer.println();


            writer.println("Pytanie 3: Który środek transportu da większe zyski dla zlecenia z dystansem 350 i wynagrodzeniem 45 (Założenie: 45k)?");
            writer.printf("   - Dystans: %.1f\n", dystans3);
            writer.printf("   - Wynagrodzenie: %.2f\n", wynagrodzenie3);
            writer.println();
            writer.println("   - Opcja Samolot:");
            writer.printf("     - Koszt (100 * S^2): %.2f\n", koszt3_samolot);
            writer.printf("     - Zysk: %.2f %s\n", zysk3_samolot, (zysk3_samolot < 0 ? "(strata)" : ""));
            writer.println("   - Opcja Pociąg:");
            writer.printf("     - Koszt (20 * S): %.2f\n", koszt3_pociag);
            writer.printf("     - Zysk: %.2f %s\n", zysk3_pociag, (zysk3_pociag < 0 ? "(strata)" : ""));
            writer.println();
            writer.print("   Odpowiedź: ");
             if (zysk3_samolot > zysk3_pociag) {
                writer.printf("Większy zysk da Samolot (Zysk: %.2f vs %.2f dla pociągu).\n", zysk3_samolot, zysk3_pociag);
            } else if (zysk3_pociag > zysk3_samolot) {
                 writer.printf("Większy zysk da Pociąg (Zysk: %.2f vs %.2f dla samolotu).\n", zysk3_pociag, zysk3_samolot);
            } else {
                writer.printf("Oba środki transportu dają taki sam zysk (%.2f).\n", zysk3_samolot);
            }
        }
    }
}